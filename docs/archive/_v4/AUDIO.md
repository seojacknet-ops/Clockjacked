# 🎵 AUDIO.md — ClockJacked Background Music

---

## The Track
**Song:** Bread Gang, Tenzin — "Like a Bird" (DNB mix)
**Vibe:** Drum & bass. Sets the tone that this isn't your nan's clock app.

## Audio Files Included

```
assets/audio/
├── like_a_bird_full.mp3    ← Full track (2.3MB, 3:00, 96kbps) — loops on dashboard
├── like_a_bird_30s.mp3     ← 30-second intro clip (550KB, fade in/out) — lighter option
```

**Recommendation:** Use the 30-second clip by default. It's 550KB vs 2.3MB, has nice fade in/out, and loops seamlessly. The full track is there if the user toggles "Full Track" in settings.

## Behavior

### Auto-Play on App Open
- Music starts playing automatically when the app opens (dashboard screen loads)
- Starts at **30% volume** (not full blast — subtle background vibe)
- Fades in over 2 seconds
- Loops continuously while app is in foreground

### Mute Toggle
- Persistent mute/unmute button on the dashboard top app bar
- Icon: 🔊 (unmuted) / 🔇 (muted)
- Tap to toggle — instant, no delay
- Mute state persists in DataStore (if user mutes, it stays muted next launch)
- Animated icon transition between muted/unmuted states

### Lifecycle Handling
- **App goes to background** → music pauses (don't be that app that plays in the background)
- **App returns to foreground** → music resumes (if not muted)
- **Screen off** → music pauses
- **Screen on + app visible** → music resumes (if not muted)
- **App killed** → music stops, MediaPlayer released

### Volume Control
- Uses `AudioManager.STREAM_MUSIC` — respects system media volume
- App starts playback at 30% of current system volume
- Optional: small volume slider in settings (v2)

## Implementation

### File Placement
```
app/src/main/assets/audio/like_a_bird_30s.mp3
app/src/main/assets/audio/like_a_bird_full.mp3
```

Using `assets/` folder (not `res/raw/`) because:
- No 1MB limit on asset files
- Can stream from assets without loading into memory
- Better for larger audio files

### MusicManager Singleton

```kotlin
object MusicManager {
    private var mediaPlayer: MediaPlayer? = null
    private var isMuted: Boolean = false
    private var isPaused: Boolean = false

    fun init(context: Context, useFullTrack: Boolean = false) {
        if (mediaPlayer != null) return

        val fileName = if (useFullTrack) "audio/like_a_bird_full.mp3"
                        else "audio/like_a_bird_30s.mp3"

        val descriptor = context.assets.openFd(fileName)
        mediaPlayer = MediaPlayer().apply {
            setDataSource(descriptor.fileDescriptor, descriptor.startOffset, descriptor.length)
            descriptor.close()
            isLooping = true
            setVolume(0.3f, 0.3f)  // 30% volume
            prepare()
        }
    }

    fun play() {
        if (!isMuted) {
            mediaPlayer?.let {
                if (!it.isPlaying) {
                    it.start()
                    // Fade in over 2 seconds
                    fadeIn(it, duration = 2000)
                }
            }
        }
    }

    fun pause() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                isPaused = true
                fadeOut(it, duration = 500) {
                    it.pause()
                }
            }
        }
    }

    fun resume() {
        if (!isMuted && isPaused) {
            isPaused = false
            play()
        }
    }

    fun toggleMute(): Boolean {
        isMuted = !isMuted
        mediaPlayer?.let {
            if (isMuted) {
                fadeOut(it, duration = 300) { it.pause() }
            } else {
                it.start()
                fadeIn(it, duration = 500)
            }
        }
        return isMuted
    }

    fun release() {
        mediaPlayer?.release()
        mediaPlayer = null
    }

    private fun fadeIn(player: MediaPlayer, duration: Int) {
        val maxVolume = 0.3f
        val steps = 20
        val stepDuration = duration.toLong() / steps
        var currentStep = 0

        val handler = android.os.Handler(android.os.Looper.getMainLooper())
        val runnable = object : Runnable {
            override fun run() {
                if (currentStep < steps) {
                    val volume = (currentStep.toFloat() / steps) * maxVolume
                    player.setVolume(volume, volume)
                    currentStep++
                    handler.postDelayed(this, stepDuration)
                }
            }
        }
        player.setVolume(0f, 0f)
        handler.post(runnable)
    }

    private fun fadeOut(player: MediaPlayer, duration: Int, onComplete: () -> Unit = {}) {
        val startVolume = 0.3f
        val steps = 20
        val stepDuration = duration.toLong() / steps
        var currentStep = 0

        val handler = android.os.Handler(android.os.Looper.getMainLooper())
        val runnable = object : Runnable {
            override fun run() {
                if (currentStep < steps) {
                    val volume = startVolume * (1f - (currentStep.toFloat() / steps))
                    player.setVolume(volume, volume)
                    currentStep++
                    handler.postDelayed(this, stepDuration)
                } else {
                    onComplete()
                }
            }
        }
        handler.post(runnable)
    }
}
```

### Lifecycle Integration in MainActivity

```kotlin
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize music
        val isMuted = // read from DataStore
        MusicManager.init(this, useFullTrack = false)
        if (!isMuted) MusicManager.play()

        setContent {
            ClockJackedTheme {
                ClockJackedApp(
                    isMuted = isMuted,
                    onToggleMute = {
                        val newMuteState = MusicManager.toggleMute()
                        // save to DataStore
                    }
                )
            }
        }
    }

    override fun onPause() {
        super.onPause()
        MusicManager.pause()
    }

    override fun onResume() {
        super.onResume()
        MusicManager.resume()
    }

    override fun onDestroy() {
        super.onDestroy()
        MusicManager.release()
    }
}
```

### Mute Button in Dashboard TopAppBar

```kotlin
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardTopBar(
    isMuted: Boolean,
    onToggleMute: () -> Unit,
    onNavigateToAdd: () -> Unit,
    onNavigateToAbout: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                "ClockJacked ⚡",
                fontWeight = FontWeight.Black
            )
        },
        actions = {
            // Mute toggle
            IconButton(onClick = onToggleMute) {
                Icon(
                    imageVector = if (isMuted)
                        Icons.Filled.VolumeOff
                    else
                        Icons.Filled.VolumeUp,
                    contentDescription = if (isMuted) "Unmute music" else "Mute music",
                    tint = if (isMuted)
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                    else
                        MaterialTheme.colorScheme.primary
                )
            }
            // Other actions...
        }
    )
}
```

## DataStore Persistence

```kotlin
// In PreferencesManager.kt — add these keys
val MUSIC_MUTED = booleanPreferencesKey("music_muted")
val USE_FULL_TRACK = booleanPreferencesKey("use_full_track")
```

## Settings Screen Options (Future)

```
🎵 Music
├── Background music: [ON/OFF toggle]
├── Track: ○ 30s Loop  ● Full Track
└── Volume: [━━━━━━━○━━━] 30%
```

## APK Size Impact

```
30-second clip:  +550KB  (recommended default)
Full track:      +2.3MB
Both included:   +2.85MB

Total APK budget: 10MB
Estimated core app: ~5MB
With both tracks: ~7.85MB ✅ Under budget
```

## Important Notes

- **DO NOT** play music when the widget updates (only in-app)
- **DO NOT** request `FOREGROUND_SERVICE` permission for music — it's not a music app
- **DO** release MediaPlayer properly to avoid memory leaks
- **DO** handle audio focus (pause if another app starts playing music)
- **DO** respect the user's mute preference across app restarts

---

*"Like a Bird" — because time flies when you're ClockJacked.* ⚡
