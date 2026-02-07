# 🔧 WIDGET-FIX.md — Widget Debugging & Expanded Widget Selection

> **PRIORITY: CRITICAL** — Widgets are broken. Fix before adding any new features.

---

## Current Bug: "Can't show content" on MIUI/HyperOS

### Screenshots show:
- Widget appears in MIUI widget picker ✅ (icon renders correctly)
- Widget is registered as 2x1 ✅
- Widget shows "Can't show content" when placed on home screen ❌

### Root Cause Checklist — Debug in this order:

#### 1. Missing or broken `initialLayout` (MOST LIKELY)
Glance widgets REQUIRE a traditional XML layout as fallback while Compose content loads. If this is missing or references missing resources, MIUI shows "Can't show content."

**Fix:**
```xml
<!-- res/layout/widget_loading.xml -->
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@color/widget_background">

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center"
        android:text="Loading..."
        android:textColor="@color/widget_text"
        android:textSize="14sp" />
</FrameLayout>
```

```xml
<!-- res/values/widget_colors.xml -->
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <color name="widget_background">#FF1A1A1A</color>
    <color name="widget_text">#FFFFFFFF</color>
</resources>
```

**Verify:** `android:initialLayout="@layout/widget_loading"` exists in `res/xml/clockjacked_widget_info.xml`.

#### 2. `provideGlance()` crashing silently
If the Glance composable throws ANY exception, MIUI shows "Can't show content" with no logcat output visible.

**Fix — wrap everything in try-catch and provide fallback:**
```kotlin
class ClockJackedWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            try {
                // Your actual widget content
                WidgetContent()
            } catch (e: Exception) {
                // Fallback — at least show SOMETHING
                Text(
                    text = "ClockJacked ⚡",
                    style = TextStyle(
                        color = ColorProvider(Color.White),
                        fontSize = 16.sp
                    )
                )
            }
        }
    }
}
```

**Common crash causes inside provideGlance:**
- Accessing DataStore that hasn't been initialized yet → use `try-catch` or provide defaults
- Using `LocalContext` incorrectly in Glance (it's different from regular Compose)
- Referencing resources that don't exist
- Null pointer on clock list when no clocks saved yet

#### 3. DataStore not initialized for widget
If the widget tries to read saved clocks from DataStore before the app has ever been opened, it crashes.

**Fix — always provide defaults:**
```kotlin
override suspend fun provideGlance(context: Context, id: GlanceId) {
    // Load clocks with fallback to defaults
    val clocks = try {
        loadSavedClocks(context)
    } catch (e: Exception) {
        DEFAULT_CLOCKS  // Always have a fallback
    }

    provideContent {
        WidgetContent(clocks = clocks.ifEmpty { DEFAULT_CLOCKS })
    }
}
```

#### 4. MIUI Background Restrictions
Xiaomi/MIUI aggressively kills background processes. The widget update worker may be getting killed.

**Fixes — apply ALL of these:**

```kotlin
// In Application class or MainActivity.onCreate()
fun requestMIUIPermissions(context: Context) {
    // Request autostart permission (MIUI-specific)
    try {
        val intent = Intent()
        intent.component = ComponentName(
            "com.miui.securitycenter",
            "com.miui.permcenter.autostart.AutoStartManagementActivity"
        )
        context.startActivity(intent)
    } catch (e: Exception) {
        // Not MIUI, ignore
    }
}
```

```kotlin
// WorkManager — use KEEP policy and add constraints
fun scheduleWidgetUpdates(context: Context) {
    val constraints = Constraints.Builder()
        .setRequiresBatteryNotLow(false)  // Update even on low battery
        .build()

    val request = PeriodicWorkRequestBuilder<WidgetUpdateWorker>(
        15, TimeUnit.MINUTES
    )
        .setConstraints(constraints)
        .addTag("clockjacked_widget")
        .build()

    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        "clock_widget_update",
        ExistingPeriodicWorkPolicy.KEEP,
        request
    )
}
```

**Also add to AndroidManifest.xml:**
```xml
<uses-permission android:name="android.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS" />
<uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />

<!-- Boot receiver to restart widget updates after reboot -->
<receiver
    android:name=".widget.BootReceiver"
    android:exported="true">
    <intent-filter>
        <action android:name="android.intent.action.BOOT_COMPLETED" />
    </intent-filter>
</receiver>
```

```kotlin
class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            WidgetUpdateWorker.schedule(context)
            // Also force an immediate widget update
            CoroutineScope(Dispatchers.IO).launch {
                ClockJackedWidget().updateAll(context)
            }
        }
    }
}
```

#### 5. Widget Info XML issues
Verify the widget info XML is complete and correct:

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
    android:updatePeriodMillis="1800000"
    android:widgetCategory="home_screen"
    android:description="@string/widget_description"
    android:previewLayout="@layout/widget_loading" />
```

**Note:** Remove `android:configure` attribute temporarily to rule out config activity issues. Add it back after basic widget works.

### Debugging Steps for Claude Code

```bash
# 1. Check logcat for widget errors
adb logcat | grep -i "glance\|widget\|clockjacked\|AppWidget"

# 2. Force widget update manually
adb shell am broadcast -a android.appwidget.action.APPWIDGET_UPDATE \
    --es appWidgetIds "$(adb shell appwidget list | grep clockjacked)"

# 3. Check if WorkManager is running
adb shell dumpsys jobscheduler | grep clockjacked

# 4. Check MIUI battery optimization status
adb shell dumpsys deviceidle whitelist | grep clockjacked
```

### Fix Verification
After applying fixes:
1. Uninstall app completely
2. Clean build and reinstall
3. Open app first (let DataStore initialize)
4. Add widget from picker
5. Should show clock(s) immediately
6. Wait 15+ minutes — verify it updates
7. Reboot phone — verify widget survives

---

## Expanded Widget Selection

Once the basic widget works, offer these widget styles:

### Widget 1: "Quick Glance" (2x1) — Single Clock
```
┌──────────────────────────┐
│  🇮🇩 Bali        ☀️  🟢 │
│     3:47 PM              │
│     +8h · Fri, Feb 7     │
└──────────────────────────┘
```
- Shows 1 clock with call status dot
- City name, flag, day/night icon
- Large time, offset, date
- Most compact option

### Widget 2: "Duo" (4x1) — Two Clocks Side by Side
```
┌─────────────────────────────────────────────┐
│  🇮🇩 Bali           │  🇬🇧 Liverpool         │
│  ☀️ 3:47 PM    🟢   │  🌙 7:47 AM    🟡      │
│  +8h · Fri           │  +0h · Fri              │
└─────────────────────────────────────────────┘
```
- Two clocks, equal space
- Includes traffic light dots
- Great for "me + home" comparison

### Widget 3: "The Crew" (4x2) — Up to 4 Clocks Grid
```
┌─────────────────────────────────────────────┐
│  🇮🇩 Bali             │  🇺🇸 Salt Lake City   │
│  ☀️ 3:47 PM     🟢    │  🌙 1:47 AM     🔴   │
│  Peak hours 🔥         │  Deep sleep 😴        │
├─────────────────────────┼─────────────────────│
│  🇬🇧 Liverpool         │  🇺🇸 Honolulu         │
│  ☀️ 7:47 AM     🟡    │  🌙 9:47 PM     🟡   │
│  Morning coffee ☕      │  Evening chill 🛋️     │
└─────────────────────────────────────────────┘
```
- 2x2 grid with vibe labels
- Traffic light dots
- The flagship widget

### Widget 4: "Timeline" (5x1) — Horizontal Time Strip
```
┌───────────────────────────────────────────────────────┐
│  🏠 Bali 3:47PM  →  LPL 7:47AM  →  SLC 1:47AM  →  HNL 9:47PM │
└───────────────────────────────────────────────────────┘
```
- All clocks in a single horizontal row
- Compact, information-dense
- Color-coded text (green/amber/red based on call status)
- Home base marked with 🏠

### Widget 5: "Next Call" (3x1) — Smart Recommendation
```
┌────────────────────────────────────┐
│  📞 Best time to call Campbell:    │
│     NOW — it's 10:32 AM in Liverpool │
│  🟢 Good for 10 more hours        │
└────────────────────────────────────┘
```
- AI-style smart widget
- Shows the next "crew" member who's in a good calling window
- Cycles through crew members or shows the one closest to leaving green zone
- Most unique widget — no other clock app does this

---

## Widget Configuration Screen

When user long-presses a widget → "Configure", show a clean selection screen:

```
┌─────────────────────────────┐
│  Configure Widget           │
│                             │
│  Style:                     │
│  ○ Quick Glance (2x1)      │
│  ○ Duo (4x1)               │
│  ● The Crew (4x2)          │
│  ○ Timeline (5x1)          │
│  ○ Next Call (3x1)         │
│                             │
│  Select clocks:             │
│  ☑ 🇮🇩 Bali (Home Base)    │
│  ☑ 🇬🇧 Liverpool            │
│  ☑ 🇺🇸 Salt Lake City       │
│  ☐ 🇺🇸 Honolulu             │
│                             │
│  Show vibe labels: [✓]     │
│  Show call status: [✓]     │
│                             │
│  [ Save Widget ]            │
└─────────────────────────────┘
```

### Config Persistence
Each widget instance stores its own config:
```kotlin
// Use Glance state per widget ID
data class WidgetConfig(
    val style: WidgetStyle,
    val selectedClockIds: List<String>,
    val showVibeLabels: Boolean = true,
    val showCallStatus: Boolean = true
)

enum class WidgetStyle {
    QUICK_GLANCE,  // 2x1
    DUO,           // 4x1
    CREW,          // 4x2
    TIMELINE,      // 5x1
    NEXT_CALL      // 3x1
}
```

---

## Implementation Priority

1. **FIX the current "Can't show content" bug first** — follow debug checklist above
2. **Get Widget 1 (Quick Glance) working reliably** — simplest, proves the pipeline
3. **Add Widget 3 (The Crew)** — the flagship
4. **Add Widget 2 (Duo)** — quick win after Crew works
5. **Add Widget 5 (Next Call)** — the showstopper
6. **Add Widget 4 (Timeline)** — nice to have

---

## MIUI/HyperOS Widget Testing Checklist

- [ ] Widget shows content immediately after adding (no "Can't show content")
- [ ] Widget updates time within 15 minutes
- [ ] Widget survives app being killed from recents
- [ ] Widget survives phone reboot
- [ ] Widget survives MIUI "battery saver" clearing background apps
- [ ] Widget looks correct in MIUI dark mode
- [ ] Widget looks correct in MIUI light mode
- [ ] Widget resize works (drag edges on home screen)
- [ ] Multiple widget instances work simultaneously
- [ ] Removing a clock from app doesn't crash widget
- [ ] Widget tap opens app correctly

---

*Fix the foundation first, then build the empire.* ⚡
