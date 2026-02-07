---
name: android-widget
description: Jetpack Glance widget development skill for Android. Use when building home screen widgets, widget configuration activities, WorkManager periodic updates, or widget state management. Covers Glance composables, GlanceAppWidget, GlanceAppWidgetReceiver, widget sizes, WorkManager integration, MIUI/HyperOS widget quirks, and AppWidgetProviderInfo XML. Trigger on any widget, glance, appwidget, or home screen component task.
---

# Jetpack Glance Widget Skill

## Dependencies

```kotlin
// build.gradle.kts
dependencies {
    implementation("androidx.glance:glance-appwidget:1.1.0")
    implementation("androidx.glance:glance-material3:1.1.0")
    implementation("androidx.work:work-runtime-ktx:2.9.1")
}
```

## Widget Architecture (3 files minimum)

### 1. GlanceAppWidget — The UI

```kotlin
class ClockJackedWidget : GlanceAppWidget() {

    override val sizeMode = SizeMode.Responsive(
        setOf(
            DpSize(100.dp, 48.dp),   // Small 2x1
            DpSize(250.dp, 48.dp),   // Medium 4x1
            DpSize(250.dp, 120.dp),  // Large 4x2
        )
    )

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        // Load saved clocks from DataStore here (suspend is OK)
        val clocks = loadWidgetClocks(context, id)

        provideContent {
            val size = LocalSize.current
            GlanceTheme {
                when {
                    size.width < 200.dp -> SmallWidget(clocks.take(1))
                    size.height < 100.dp -> MediumWidget(clocks.take(2))
                    else -> LargeWidget(clocks.take(4))
                }
            }
        }
    }
}
```

### 2. GlanceAppWidgetReceiver — The Entry Point

```kotlin
class ClockJackedWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = ClockJackedWidget()
}
```

### 3. WorkManager — Periodic Updates

```kotlin
class WidgetUpdateWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        // Update all widget instances
        ClockJackedWidget().updateAll(applicationContext)
        return Result.success()
    }

    companion object {
        fun schedule(context: Context) {
            val request = PeriodicWorkRequestBuilder<WidgetUpdateWorker>(
                15, TimeUnit.MINUTES  // Minimum interval for periodic work
            ).build()
            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                "clock_widget_update",
                ExistingPeriodicWorkPolicy.KEEP,
                request
            )
        }
    }
}
```

**Important:** WorkManager minimum periodic interval is 15 minutes. For more frequent updates, use `updatePeriodMillis` in widget XML (minimum 30 minutes on most devices). For a clock widget, combine both approaches and also update on `onEnabled`/`onUpdate` receiver callbacks.

## AndroidManifest.xml Registration

```xml
<!-- Widget receiver -->
<receiver
    android:name=".widget.ClockJackedWidgetReceiver"
    android:exported="true"
    android:label="ClockJacked">
    <intent-filter>
        <action android:name="android.appwidget.action.APPWIDGET_UPDATE" />
    </intent-filter>
    <meta-data
        android:name="android.appwidget.provider"
        android:resource="@xml/clockjacked_widget_info" />
</receiver>

<!-- Widget config activity (optional) -->
<activity
    android:name=".widget.WidgetConfigActivity"
    android:exported="true">
    <intent-filter>
        <action android:name="android.appwidget.action.APPWIDGET_CONFIGURE" />
    </intent-filter>
</activity>

<!-- WorkManager initializer -->
<provider
    android:name="androidx.startup.InitializationProvider"
    android:authorities="${applicationId}.androidx-startup"
    android:exported="false"
    tools:node="merge">
</provider>
```

## Widget Info XML

```xml
<!-- res/xml/clockjacked_widget_info.xml -->
<appwidget-provider xmlns:android="http://schemas.android.com/apk/res/android"
    android:initialLayout="@layout/widget_loading"
    android:minWidth="110dp"
    android:minHeight="40dp"
    android:minResizeWidth="110dp"
    android:minResizeHeight="40dp"
    android:maxResizeWidth="530dp"
    android:maxResizeHeight="400dp"
    android:resizeMode="horizontal|vertical"
    android:targetCellWidth="2"
    android:targetCellHeight="1"
    android:updatePeriodMillis="1800000"
    android:configure="com.clockjacked.app.widget.WidgetConfigActivity"
    android:widgetCategory="home_screen"
    android:previewLayout="@layout/widget_preview" />
```

**Note:** `initialLayout` requires a traditional XML layout (not Compose) — create a minimal loading placeholder.

## Glance Composable Patterns

```kotlin
// Glance uses its OWN composables — NOT regular Compose
// Import from androidx.glance.*, NOT androidx.compose.*
import androidx.glance.layout.*
import androidx.glance.text.Text
import androidx.glance.background
import androidx.glance.appwidget.cornerRadius

@Composable
fun SmallWidget(clocks: List<ClockEntry>) {
    Box(
        modifier = GlanceModifier
            .fillMaxSize()
            .cornerRadius(16.dp)
            .background(ColorProvider(Color(0xFF1A1A1A), Color(0xFFF5F5F5))),
        contentAlignment = Alignment.Center
    ) {
        clocks.firstOrNull()?.let { clock ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = clock.cityName,
                    style = TextStyle(fontSize = 12.sp, color = ColorProvider(Color.White, Color.Black))
                )
                Text(
                    text = formatTime(clock.timezoneId),
                    style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold, color = ColorProvider(Color.White, Color.Black))
                )
            }
        }
    }
}
```

## MIUI/HyperOS Widget Quirks

1. **Widget corners:** MIUI applies its own corner radius — set `cornerRadius` but expect it may be overridden
2. **Background restrictions:** MIUI aggressively kills background processes — WorkManager should use `setExpedited()` where possible
3. **Widget preview:** MIUI uses the preview image, not previewLayout — provide both
4. **Update frequency:** MIUI may throttle `updatePeriodMillis` more aggressively than stock Android
5. **Dark mode:** MIUI may not respect `ColorProvider` day/night — test both modes explicitly

## Widget Click Actions

```kotlin
// Open app on widget tap
Box(
    modifier = GlanceModifier
        .fillMaxSize()
        .clickable(actionStartActivity<MainActivity>())
) { /* content */ }
```

## Widget State Persistence

Use GlanceStateDefinition with DataStore for per-widget configuration:

```kotlin
class ClockJackedWidget : GlanceAppWidget() {
    override val stateDefinition = PreferencesGlanceStateDefinition

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            val prefs = currentState<Preferences>()
            val clockIds = prefs[stringPreferencesKey("selected_clocks")] ?: ""
            // Parse and display
        }
    }
}
```

## Testing Checklist

- [ ] Widget appears in widget picker with correct preview
- [ ] Widget renders in all 3 sizes
- [ ] Widget updates time within update period
- [ ] Widget survives device reboot
- [ ] Widget tap opens app
- [ ] Config activity saves selection correctly
- [ ] Removing clock from app gracefully handles widget
- [ ] Widget looks correct in MIUI dark AND light mode
