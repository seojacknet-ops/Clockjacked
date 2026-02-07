# ✅ TODO.md — ClockJacked Build Tracker

> **Update this file as tasks are completed. Claude Code: check off items as you finish them.**

Last Updated: 2026-02-07
Status: ✅ COMPLETE

---

## Phase 1: Foundation 🏗️
**Target: 1-2 hours | Agent: ARCHITECT**

- [x] Initialize Android project (com.clockjacked.app)
- [x] Configure build.gradle.kts with all dependencies
  - [x] Jetpack Compose BOM
  - [x] Material 3
  - [x] Navigation Compose
  - [x] DataStore Preferences
  - [x] Jetpack Glance
  - [x] Kotlin Coroutines
- [x] Set up project package structure (see CLAUDE.md)
- [x] Create ClockEntry data model
- [x] Create Color.kt — dark-first AMOLED palette
- [x] Create Type.kt — typography scale
- [x] Create Theme.kt — Material 3 theme with dark/light
- [x] Create TimeFormatter.kt utility
- [x] Create TimeDiffCalculator.kt utility
- [x] Verify: Project compiles clean with no errors
- [x] Verify: Empty app launches with correct dark theme

**Phase 1 Complete:** [x]

---

## Phase 2: Core Clock Display ⚙️
**Target: 2-3 hours | Agents: ENGINEER + DESIGNER**

- [x] Build DayNightIndicator.kt composable (sun/moon)
- [x] Build ClockCard.kt composable
  - [x] City name with flag emoji
  - [x] Current time (large, bold)
  - [x] Current date
  - [x] Time difference from local (e.g., "+5h", "-8h")
  - [x] Day/night visual indicator
  - [x] Subtle card styling (elevation, rounded corners)
- [x] Build ClockViewModel.kt
  - [x] StateFlow for clock list
  - [x] 1-second tick coroutine for live updates
  - [x] Add/remove/reorder clock functions
- [x] Build DashboardScreen.kt
  - [x] LazyColumn of ClockCards
  - [x] Top app bar with "ClockJacked" title
  - [x] FAB or button to navigate to Add Clock
- [x] Pre-load 4 default clocks on first launch
  - [x] Salt Lake City (America/Denver)
  - [x] Honolulu (Pacific/Honolulu)
  - [x] Liverpool (Europe/London)
  - [x] Bali (Asia/Makassar)
- [x] Verify: All 4 clocks display on screen
- [x] Verify: Times update every second
- [x] Verify: Time differences are accurate
- [x] Verify: Day/night indicator matches actual conditions

**Phase 2 Complete:** [x]

---

## Phase 3: Add, Remove & Persist 🔧
**Target: 2-3 hours | Agent: ENGINEER**

- [x] Build TimezoneDatabase.kt
  - [x] 300+ cities mapped to IANA timezone IDs (422 cities)
  - [x] Include all world capitals
  - [x] Include major cities per timezone
  - [x] Include common travel destinations
  - [x] Each entry: city name, country, timezone ID, flag emoji
- [x] Build PreferencesManager.kt (DataStore wrapper)
  - [x] Save clock list as JSON string (kotlinx.serialization)
  - [x] Load clock list on app start
  - [x] Handle first-launch default loading
  - [x] Handle data corruption gracefully
- [x] Build ClockRepository.kt
  - [x] Abstract data operations
  - [x] CRUD operations for clocks
  - [x] Reorder operation
- [x] Build TimezoneSearchBar.kt composable
  - [x] Text input with search icon
  - [x] Real-time filtering as user types
  - [x] Show matching cities in dropdown/list
  - [x] Search by city name, country, or abbreviation
- [x] Build AddClockScreen.kt
  - [x] Search bar at top
  - [x] Results list below
  - [x] Tap to add clock → navigate back to dashboard
  - [x] Show toast/snackbar confirmation
- [x] Implement swipe-to-delete on ClockCard
  - [x] Swipe left to reveal delete action
  - [x] Confirm with undo snackbar
- [x] Implement drag-to-reorder on DashboardScreen
  - [x] Long press to initiate drag
  - [x] Visual feedback during drag
  - [x] Save new order to DataStore
- [x] Wire up Navigation Compose
  - [x] Dashboard → Add Clock
  - [x] Dashboard → About
  - [x] Back navigation
- [x] Verify: Add a new clock → appears on dashboard
- [x] Verify: Remove a clock → disappears from dashboard
- [x] Verify: Reorder clocks → new order saves
- [x] Verify: Kill app → reopen → all clocks preserved
- [x] Verify: Search "Tokyo" → returns correct result
- [x] Verify: Search "UTC" → returns UTC/GMT option

**Phase 3 Complete:** [x]

---

## Phase 4: Home Screen Widget 📱
**Target: 1-2 hours | Agent: WIDGET_SMITH**

- [x] Build ClockJackedWidget.kt (Glance composable)
  - [x] Small size (2x1): 1 clock — time + city name
  - [x] Medium size (4x1): 2 clocks side by side
  - [x] Large size (4x2): 4 clocks in 2x2 grid
- [x] Style widget
  - [x] Dark background matching app theme
  - [x] Rounded corners
  - [x] Semi-transparent background option
  - [x] Readable text sizes for each widget size
- [x] Build WidgetConfigActivity.kt
  - [x] Show list of user's saved clocks
  - [x] Let user pick which clocks to show in widget
  - [x] Save widget configuration
- [x] Set up WidgetUpdateWorker.kt
  - [x] WorkManager periodic task every 60 seconds
  - [x] Efficient — minimal battery impact
  - [x] Handle doze mode properly
- [x] Register widget in AndroidManifest.xml
  - [x] Widget provider metadata
  - [x] Widget preview image
  - [x] Min/max sizes
- [x] Widget tap action → opens app
- [x] Verify: Add widget to home screen
- [x] Verify: Widget shows correct times
- [x] Verify: Widget updates within 60 seconds
- [x] Verify: Widget survives phone reboot
- [x] Verify: Widget works on MIUI/HyperOS launcher
- [x] Verify: Removing clock from app updates widget

**Phase 4 Complete:** [x]

---

## Phase 5: Polish & Ship 🚀
**Target: 1-2 hours | Agents: DESIGNER + TESTER + SHIPPER**

### UI Polish
- [x] Build AboutScreen.kt
  - [x] "ClockJacked ⚡" header
  - [x] Credits: Jack, Jake & Campbell
  - [x] Tagline and story
  - [x] Version number
  - [x] Subtle animation on load
  - [x] Jack's profile photo (circular, accent border)
- [x] Add navigation transitions (slide/fade)
- [x] Add haptic feedback on:
  - [x] Clock delete
  - [x] Clock add
  - [x] Drag reorder
- [x] Add empty state for dashboard (no clocks)
- [x] Verify dark mode looks premium on AMOLED
- [x] Verify light mode is clean and usable
- [x] Add system theme following option

### App Icon
- [x] Design app icon (bold, simple, recognizable)
- [x] Generate adaptive icon for all densities
- [x] Verify icon looks good on MIUI launcher

### Build & Release
- [x] Configure ProGuard/R8 rules
- [x] Build debug APK (~19MB)
- [x] Build release APK (2.3MB — well under 10MB budget)
- [x] Check APK size < 10MB ✅

**Phase 5 Complete:** [x]

---

## Phase 6: Extras & Personalization 🎨
**See FEATURES.md for full specs, implementation code, and design details.**

### 6A — Home Base Clock ✅
- [x] Add `isHomeBase` field to ClockEntry data model
- [x] Add `nickname` field to ClockEntry data model
- [x] Update DataStore serialization for new fields
- [x] Pin Home Base clock to top of dashboard (sortedClocks)
- [x] Visual treatment: accent glow/border + 🏠 badge
- [x] Larger time display for Home Base card (32sp vs 28sp)
- [x] Calculate all time diffs relative to Home Base (not device timezone)
- [x] Long-press menu → "Set as Home Base" option
- [x] Verify: Change Home Base → all diffs recalculate
- [x] Verify: Home Base persists across app restart

### 6B — Traffic Light Call Status ✅
- [x] Implement `getCallStatus()` function (GREEN/AMBER/RED) in CallStatusCalculator.kt
- [x] Add colored dot indicator (12dp) to right side of clock cards
- [x] GREEN dot: subtle pulse animation
- [x] AMBER dot: static
- [x] RED dot: static
- [x] Home Base clock does NOT show traffic light
- [x] Verify: Status changes correctly as time passes
- [x] Verify: All three states render with correct colors

### 6C — Vibe Labels ✅
- [x] Implement `getVibeLabel()` function in VibeLabelCalculator.kt
- [x] Display as secondary text below time on each card
- [x] Use TextSecondary color for labels (50% alpha onSurfaceVariant)
- [x] Verify: Labels change correctly throughout the day

### 6D — Custom Nicknames ✅
- [x] Long-press → "Edit nickname" option in action sheet
- [x] AlertDialog with OutlinedTextField for nickname entry
- [x] Save nickname to DataStore via updateNickname()
- [x] Display nickname as primary label, original city as subtitle
- [x] Empty nickname reverts to original city name (displayName() extension)
- [x] Update share text to use nickname when set
- [x] Verify: Nickname persists across restart

### 6E — Breathing Background Tints ✅
- [x] Implement `getTimeTint()` function in TimeTintCalculator.kt
- [x] Apply tint as background overlay at 7% opacity
- [x] Ensure text contrast ratios remain accessible
- [x] Verify: Tints are subtle and don't overpower text
- [x] Verify: Looks good on AMOLED black background

### 6F — Quick Share ✅
- [x] Long-press clock card → bottom sheet (ClockActionSheet)
- [x] Menu options: Copy time, Share, Set Home Base, Edit nickname, Add/Remove Crew, Delete
- [x] "Copy time" → formatted text to clipboard + snackbar "Copied!"
- [x] "Share" → Android share sheet with formatted message
- [x] ShareFormatter.kt handles formatting with nickname support
- [x] Verify: Copy works, share sheet opens correctly

### 6G — Crew Mode ✅
- [x] Add `isCrew` field to ClockEntry data model
- [x] Allow users to mark clocks as "crew" via action sheet toggle
- [x] Build CrewModeScreen with horizontal 24-hour timeline (Canvas)
- [x] CrewOverlapCalculator: finds overlap using 1440-element boolean array
- [x] Map each crew member's awake hours onto timeline (default 8 AM–10 PM)
- [x] Calculate and highlight overlap "green zone"
- [x] Handle "no overlap" case: "No good overlap today. Someone's losing sleep 😅"
- [x] Accessible via Groups icon in dashboard top bar
- [x] Verify: Works with 2, 3, 4+ crew members

### 6H — Easter Eggs ✅
- [x] EasterEggManager.kt — one-per-session management via shownEggs set
- [x] 15+ clocks → "Slow down, you don't know people in that many time zones. 😄"
- [x] All same timezone → "Everyone's in the same zone! Go outside and see them. 🤝"
- [x] Adding clock at 3 AM → "Adding clocks at 3 AM? You good? 😅"
- [x] Deleting last clock → "Are you sure? It's lonely without any clocks. 🥺"
- [x] About screen: tap crew names for fun subtitles (Jack/Jake/Campbell)
- [x] Each easter egg shows max once per session (no spam)

**Phase 6 Complete:** [x]

---

## Nice-to-Have (Post v1.0) 🎁
_Only after all phases above are complete_

- [ ] Analog clock face toggle
- [ ] Meeting planner view (all zones in a timeline)
- [ ] Time converter ("3pm in Bali = ? in Utah")
- [ ] Custom notification alerts ("It's 9am in Liverpool")
- [ ] Per-clock accent color customization
- [ ] Clock face font options
- [ ] Landscape layout optimization
- [ ] Tablet layout (2-column grid)
- [ ] Export/import clock configurations
- [ ] "Quick add" popular destinations

---

## Progress Summary

| Phase | Status | Completed |
|-------|--------|-----------|
| Phase 1: Foundation | ✅ Complete | 2026-02-06 |
| Phase 2: Core Display | ✅ Complete | 2026-02-06 |
| Phase 3: Add/Remove/Persist | ✅ Complete | 2026-02-06 |
| Phase 4: Widget | ✅ Complete | 2026-02-06 |
| Phase 5: Polish & Ship | ✅ Complete | 2026-02-06 |
| Phase 6: Extras | ✅ Complete | 2026-02-07 |

### Status Key
- ⬜ Not Started
- 🟡 In Progress
- ✅ Complete
- 🔴 Blocked

---

*All phases complete. ClockJacked v1.0 is built and ready to ship.* ⚡
