package com.clockjacked.app.audio

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import android.os.Handler
import android.os.Looper

/**
 * Singleton that manages background music playback.
 * Plays "Like a Bird" (DNB) at 30% volume with fade in/out.
 * Respects audio focus so other media apps can take over.
 */
object MusicManager {

    private var mediaPlayer: MediaPlayer? = null
    private var audioManager: AudioManager? = null
    private var focusRequest: AudioFocusRequest? = null
    private var isMuted: Boolean = false
    private var isPaused: Boolean = false
    private var isInitialized: Boolean = false
    private val handler = Handler(Looper.getMainLooper())

    private const val MAX_VOLUME = 0.3f
    private const val FADE_IN_DURATION = 2000L
    private const val FADE_OUT_DURATION = 500L
    private const val FADE_STEPS = 20

    private val audioFocusListener = AudioManager.OnAudioFocusChangeListener { focusChange ->
        when (focusChange) {
            AudioManager.AUDIOFOCUS_LOSS,
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> pause()
            AudioManager.AUDIOFOCUS_GAIN -> resume()
        }
    }

    fun init(context: Context) {
        if (isInitialized) return

        try {
            audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

            val descriptor = context.assets.openFd("audio/like_a_bird_30s.mp3")
            mediaPlayer = MediaPlayer().apply {
                setDataSource(descriptor.fileDescriptor, descriptor.startOffset, descriptor.length)
                descriptor.close()
                isLooping = true
                setVolume(0f, 0f)
                prepare()
            }
            isInitialized = true
        } catch (e: Exception) {
            // Audio file missing or corrupt — silently degrade, music just won't play
            mediaPlayer = null
            isInitialized = false
        }
    }

    /** Set initial mute state from DataStore (no animation) */
    fun setMuted(muted: Boolean) {
        isMuted = muted
    }

    /** Start playback with fade-in if not muted */
    fun play() {
        if (isMuted || !isInitialized) return
        mediaPlayer?.let { player ->
            if (!player.isPlaying) {
                if (requestAudioFocus()) {
                    player.start()
                    fadeIn(player)
                }
            }
        }
    }

    /** Fade out and pause (called on Activity.onPause) */
    fun pause() {
        mediaPlayer?.let { player ->
            if (player.isPlaying) {
                isPaused = true
                fadeOut(player) { player.pause() }
            }
        }
    }

    /** Resume playback if not muted (called on Activity.onResume) */
    fun resume() {
        if (isMuted || !isInitialized) return
        if (isPaused) {
            isPaused = false
            mediaPlayer?.let { player ->
                if (!player.isPlaying) {
                    player.start()
                    fadeIn(player)
                }
            }
        }
    }

    /** Toggle mute on/off. Returns new mute state. */
    fun toggleMute(): Boolean {
        isMuted = !isMuted
        mediaPlayer?.let { player ->
            if (isMuted) {
                fadeOut(player) { player.pause() }
            } else {
                if (!player.isPlaying) {
                    player.start()
                }
                fadeIn(player)
            }
        }
        return isMuted
    }

    /** Release MediaPlayer and audio focus */
    fun release() {
        handler.removeCallbacksAndMessages(null)
        mediaPlayer?.release()
        mediaPlayer = null
        abandonAudioFocus()
        isInitialized = false
        isPaused = false
    }

    private fun requestAudioFocus(): Boolean {
        val am = audioManager ?: return false
        val attrs = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build()

        focusRequest = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
            .setAudioAttributes(attrs)
            .setOnAudioFocusChangeListener(audioFocusListener, handler)
            .build()

        return am.requestAudioFocus(focusRequest!!) == AudioManager.AUDIOFOCUS_REQUEST_GRANTED
    }

    private fun abandonAudioFocus() {
        focusRequest?.let { audioManager?.abandonAudioFocusRequest(it) }
        focusRequest = null
    }

    private fun fadeIn(player: MediaPlayer) {
        val stepDuration = FADE_IN_DURATION / FADE_STEPS
        var step = 0
        player.setVolume(0f, 0f)

        val runnable = object : Runnable {
            override fun run() {
                if (step < FADE_STEPS && player.isPlaying) {
                    val volume = (step.toFloat() / FADE_STEPS) * MAX_VOLUME
                    player.setVolume(volume, volume)
                    step++
                    handler.postDelayed(this, stepDuration)
                } else if (player.isPlaying) {
                    player.setVolume(MAX_VOLUME, MAX_VOLUME)
                }
            }
        }
        handler.post(runnable)
    }

    private fun fadeOut(player: MediaPlayer, onComplete: () -> Unit = {}) {
        val stepDuration = FADE_OUT_DURATION / FADE_STEPS
        var step = 0

        val runnable = object : Runnable {
            override fun run() {
                if (step < FADE_STEPS) {
                    val volume = MAX_VOLUME * (1f - (step.toFloat() / FADE_STEPS))
                    try {
                        player.setVolume(volume, volume)
                    } catch (_: IllegalStateException) {
                        // Player already released
                        return
                    }
                    step++
                    handler.postDelayed(this, stepDuration)
                } else {
                    onComplete()
                }
            }
        }
        handler.post(runnable)
    }
}
