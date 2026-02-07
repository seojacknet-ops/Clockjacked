# 🕐 ClockJacked — World Clock App

## The Pitch
**"Jack, Jake & Campbell jacked the clock game."**

Every world clock app on Android is riddled with ads, paywalls, and bugs. ClockJacked is a clean, fast, beautiful, ad-free world clock app built in under 24 hours. No BS. Just clocks.

---

## Project Overview

**App Name:** ClockJacked
**Tagline:** "Time, unjacked from the trash."
**Platform:** Android (Kotlin + Jetpack Compose)
**Min SDK:** 26 (Android 8.0)
**Target SDK:** 34 (Android 14)
**Architecture:** Single-activity, MVVM with Compose

---

## Core Requirements

### Must-Have (MVP — Ship This)

1. **Home Screen — Clock Dashboard**
   - Display multiple world clocks in a clean vertical list or grid
   - Each clock card shows:
     - City/location name (custom label supported)
     - Current time (updating live every second)
     - Date
     - Time difference from local time (e.g., "+5h", "-8h")
     - Day/night indicator (subtle sun/moon icon or background tint)
   - Default clocks pre-loaded:
     - 🇺🇸 Salt Lake City, Utah (America/Denver)
     - 🇺🇸 Honolulu, Hawaii (Pacific/Honolulu)
     - 🇬🇧 Liverpool, UK (Europe/London)
     - 🇮🇩 Bali, Indonesia (Asia/Makassar)

2. **Add/Remove Clocks**
   - Search by city name or timezone
   - Add unlimited clocks
   - Remove clocks with swipe-to-delete or long-press menu
   - Drag to reorder

3. **Persistent Storage**
   - Save selected clocks locally (Room DB or DataStore)
   - Clocks persist across app restarts

4. **Home Screen Widget**
   - Glance-based widget (Jetpack Glance)
   - Shows 2-4 clocks at a glance on home screen
   - Updates every minute
   - Clean, minimal design matching app aesthetic
   - Configurable: user picks which clocks to show in widget

5. **Theming**
   - Dark mode by default (AMOLED black option)
   - Light mode available
   - Follow system theme option
   - Material You / Dynamic Color support

### Nice-to-Have (If Time Permits)

- Analog clock face option (toggle between digital/analog per clock)
- "Meeting Planner" — see what time it is across all your zones at a glance for scheduling
- Time converter: "When it's 3pm in Bali, what time is it in Utah?"
- Notification reminder: "It's now 9am in Liverpool" (scheduled alerts)
- Clock face customization (font, color accent per clock)

---

## Design Principles

1. **No ads. Ever.** This is the whole point.
2. **No paywall.** Full functionality, free forever.
3. **Fast.** App should launch in under 1 second. No splash screen longer than 300ms.
4. **Clean.** Generous whitespace, clear typography, zero clutter.
5. **Dark-first.** The default theme should be dark/AMOLED. Light mode is secondary.
6. **Personality.** Subtle "ClockJacked" branding. The About screen should credit "Jack, Jake & Campbell" as the crew.

---

## Technical Architecture

### Tech Stack
```
Language:           Kotlin
UI:                 Jetpack Compose + Material 3
Architecture:       MVVM (ViewModel + StateFlow)
Local Storage:      DataStore Preferences (for clock list)
Widget:             Jetpack Glance (AppWidget with Compose)
Time Handling:      java.time.ZonedDateTime / ZoneId
Build:              Gradle (Kotlin DSL)
Min SDK:            26
```

### Project Structure
```
com.clockjacked.app/
├── MainActivity.kt
├── ui/
│   ├── theme/
│   │   ├── Theme.kt
│   │   ├── Color.kt
│   │   └── Type.kt
│   ├── screens/
│   │   ├── ClockDashboard.kt       # Main screen with clock list
│   │   ├── AddClockScreen.kt       # Search & add timezone
│   │   └── AboutScreen.kt          # Credits: Jack, Jake & Campbell
│   └── components/
│       ├── ClockCard.kt             # Individual clock display
│       ├── SearchBar.kt             # Timezone search
│       └── DayNightIndicator.kt     # Sun/moon visual
├── viewmodel/
│   └── ClockViewModel.kt           # Business logic + state
├── data/
│   ├── ClockRepository.kt          # Data layer
│   ├── TimezoneData.kt             # Curated timezone/city list
│   └── PreferencesManager.kt       # DataStore persistence
├── widget/
│   ├── ClockJackedWidget.kt         # Glance widget
│   └── WidgetConfig.kt             # Widget configuration
└── util/
    └── TimeUtils.kt                # Time formatting helpers
```

### Key Timezone Mappings
```kotlin
val DEFAULT_CLOCKS = listOf(
    ClockEntry("Salt Lake City", "America/Denver", "🇺🇸"),
    ClockEntry("Honolulu", "Pacific/Honolulu", "🇺🇸"),
    ClockEntry("Liverpool", "Europe/London", "🇬🇧"),
    ClockEntry("Bali", "Asia/Makassar", "🇮🇩"),
)
```

### Searchable City Database
Include a curated list of ~300-500 major cities mapped to IANA timezone IDs. This should be a local static list (no network dependency). Include:
- All world capitals
- Major cities per timezone
- Common travel destinations
- Full text search by city name, country, or timezone abbreviation

---

## Widget Specification

### Glance Widget Details
- **Sizes:** Support small (2x1), medium (4x1), and large (4x2)
- **Small:** Shows 1 clock (time + city name)
- **Medium:** Shows 2 clocks side by side
- **Large:** Shows 4 clocks in a 2x2 grid
- **Update frequency:** Every 60 seconds via WorkManager
- **Tap action:** Opens app
- **Configuration:** On widget add, let user pick which clocks to display
- **Style:** Match app dark theme, rounded corners, semi-transparent background

---

## About Screen Content

```
ClockJacked ⚡

Built with love by Jack, Jake & Campbell.
Three mates. Multiple time zones. Zero patience for ads.

We got tired of every clock app being stuffed with 
ads, paywalls, and tracking. So we jacked the game 
and built our own.

No ads. No tracking. No BS. Just time.

"Wherever we are in the world — Utah, Hawaii, 
Liverpool, or Bali — we're always on each other's time."

v1.0.0 — Made in 24 hours ⏱️
```

---

## Build & Release Notes

### To Build (Claude Code)
1. Initialize Android project with Gradle Kotlin DSL
2. Set up Jetpack Compose with Material 3
3. Implement core clock display with live updating
4. Add timezone search and persistence
5. Build Glance widget
6. Polish UI, add About screen
7. Generate signed APK for sideloading

### APK Distribution
- Generate debug APK for immediate testing
- Generate release APK (signed) for distribution
- Can distribute via direct APK download (no Play Store needed initially)

### Testing Checklist
- [ ] All 4 default clocks display correctly
- [ ] Times update in real-time
- [ ] Add/remove clocks works
- [ ] Clocks persist after app restart
- [ ] Widget displays and updates
- [ ] Dark/light theme toggle works
- [ ] Search finds cities correctly
- [ ] Day/night indicator is accurate
- [ ] App launches in under 1 second
- [ ] No crashes on rotation or background/foreground

---

## Claude Code Session Strategy

### Phase 1: Foundation (1-2 hours)
- Project scaffolding + Gradle setup
- Theme + Color + Typography
- Basic ClockCard composable
- ClockViewModel with live time updates

### Phase 2: Core Features (2-3 hours)
- Clock dashboard screen
- Add/remove/reorder clocks
- Timezone search with curated city list
- DataStore persistence

### Phase 3: Widget (1-2 hours)
- Glance widget implementation
- Widget configuration activity
- WorkManager for periodic updates

### Phase 4: Polish (1-2 hours)
- About screen
- Animations and transitions
- Edge cases and error handling
- APK generation

---

## Important Notes for Claude Code

- **DO NOT** add any ad SDKs, analytics, or tracking libraries
- **DO NOT** require internet permission (everything works offline)
- **DO** use proper Kotlin coroutines for time updates
- **DO** handle timezone edge cases (DST transitions, etc.)
- **DO** make the app feel native and premium
- **DO** test that the widget actually works on API 26+
- Keep dependencies minimal — this app should be lightweight (<10MB APK)
- Use vector drawables for all icons (no raster assets)
- Follow Material 3 design guidelines strictly

---

*ClockJacked — Because time shouldn't come with a price tag.* ⚡
