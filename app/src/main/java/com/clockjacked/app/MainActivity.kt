package com.clockjacked.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.clockjacked.app.audio.MusicManager
import com.clockjacked.app.ui.navigation.ClockJackedNavGraph
import com.clockjacked.app.ui.theme.ClockJackedTheme
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize music player and restore mute state
        MusicManager.init(applicationContext)
        val app = application as ClockJackedApp
        val isMuted = runBlocking { app.preferencesManager.getMusicMuted().first() }
        MusicManager.setMuted(isMuted)
        if (!isMuted) MusicManager.play()

        setContent {
            ClockJackedTheme {
                ClockJackedNavGraph(
                    application = app
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        MusicManager.resume()
    }

    override fun onPause() {
        super.onPause()
        MusicManager.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        MusicManager.release()
    }
}
