# ✅ TODO.md — ClockJacked Build Tracker

> **Update this file as tasks are completed. Claude Code: check off items as you finish them.**

Last Updated: 2026-02-06
Status: 🟡 NOT STARTED

---

## Phase 1: Foundation 🏗️
**Target: 1-2 hours | Agent: ARCHITECT**

- [ ] Initialize Android project (com.clockjacked.app)
- [ ] Configure build.gradle.kts with all dependencies
  - [ ] Jetpack Compose BOM
  - [ ] Material 3
  - [ ] Navigation Compose
  - [ ] DataStore Preferences
  - [ ] Jetpack Glance
  - [ ] Kotlin Coroutines
- [ ] Set up project package structure (see CLAUDE.md)
- [ ] Create ClockEntry data model
- [ ] Create Color.kt — dark-first AMOLED palette
- [ ] Create Type.kt — typography scale
- [ ] Create Theme.kt — Material 3 theme with dark/light
- [ ] Create TimeFormatter.kt utility
- [ ] Create TimeDiffCalculator.kt utility
- [ ] Verify: Project compiles clean with no errors
- [ ] Verify: Empty app launches with correct dark theme

**Phase 1 Complete:** [ ] _(check when all above done)_

---

## Phase 2: Core Clock Display ⚙️
**Target: 2-3 hours | Agents: ENGINEER + DESIGNER**

- [ ] Build DayNightIndicator.kt composable (sun/moon)
- [ ] Build ClockCard.kt composable
  - [ ] City name with flag emoji
  - [ ] Current time (large, bold)
  - [ ] Current date
  - [ ] Time difference from local (e.g., "+5h", "-8h")
  - [ ] Day/night visual indicator
  - [ ] Subtle card styling (elevation, rounded corners)
- [ ] Build ClockViewModel.kt
  - [ ] StateFlow for clock list
  - [ ] 1-second tick coroutine for live updates
  - [ ] Add/remove/reorder clock functions
- [ ] Build DashboardScreen.kt
  - [ ] LazyColumn of ClockCards
  - [ ] Top app bar with "ClockJacked" title
  - [ ] FAB or button to navigate to Add Clock
- [ ] Pre-load 4 default clocks on first launch
  - [ ] Salt Lake City (America/Denver)
  - [ ] Honolulu (Pacific/Honolulu)
  - [ ] Liverpool (Europe/London)
  - [ ] Bali (Asia/Makassar)
- [ ] Verify: All 4 clocks display on screen
- [ ] Verify: Times update every second
- [ ] Verify: Time differences are accurate
- [ ] Verify: Day/night indicator matches actual conditions

**Phase 2 Complete:** [ ] _(check when all above done)_

---

## Phase 3: Add, Remove & Persist 🔧
**Target: 2-3 hours | Agent: ENGINEER**

- [ ] Build TimezoneDatabase.kt
  - [ ] 300+ cities mapped to IANA timezone IDs
  - [ ] Include all world capitals
  - [ ] Include major cities per timezone
  - [ ] Include common travel destinations
  - [ ] Each entry: city name, country, timezone ID, flag emoji
- [ ] Build PreferencesManager.kt (DataStore wrapper)
  - [ ] Save clock list as JSON string
  - [ ] Load clock list on app start
  - [ ] Handle first-launch default loading
  - [ ] Handle data corruption gracefully
- [ ] Build ClockRepository.kt
  - [ ] Abstract data operations
  - [ ] CRUD operations for clocks
  - [ ] Reorder operation
- [ ] Build TimezoneSearchBar.kt composable
  - [ ] Text input with search icon
  - [ ] Real-time filtering as user types
  - [ ] Show matching cities in dropdown/list
  - [ ] Search by city name, country, or abbreviation
- [ ] Build AddClockScreen.kt
  - [ ] Search bar at top
  - [ ] Results list below
  - [ ] Tap to add clock → navigate back to dashboard
  - [ ] Show toast/snackbar confirmation
- [ ] Implement swipe-to-delete on ClockCard
  - [ ] Swipe left to reveal delete action
  - [ ] Confirm with undo snackbar
- [ ] Implement drag-to-reorder on DashboardScreen
  - [ ] Long press to initiate drag
  - [ ] Visual feedback during drag
  - [ ] Save new order to DataStore
- [ ] Wire up Navigation Compose
  - [ ] Dashboard → Add Clock
  - [ ] Dashboard → About
  - [ ] Back navigation
- [ ] Verify: Add a new clock → appears on dashboard
- [ ] Verify: Remove a clock → disappears from dashboard
- [ ] Verify: Reorder clocks → new order saves
- [ ] Verify: Kill app → reopen → all clocks preserved
- [ ] Verify: Search "Tokyo" → returns correct result
- [ ] Verify: Search "UTC" → returns UTC/GMT option

**Phase 3 Complete:** [ ] _(check when all above done)_

---

## Phase 4: Home Screen Widget 📱
**Target: 1-2 hours | Agent: WIDGET_SMITH**

- [ ] Build ClockJackedWidget.kt (Glance composable)
  - [ ] Small size (2x1): 1 clock — time + city name
  - [ ] Medium size (4x1): 2 clocks side by side
  - [ ] Large size (4x2): 4 clocks in 2x2 grid
- [ ] Style widget
  - [ ] Dark background matching app theme
  - [ ] Rounded corners
  - [ ] Semi-transparent background option
  - [ ] Readable text sizes for each widget size
- [ ] Build WidgetConfigActivity.kt
  - [ ] Show list of user's saved clocks
  - [ ] Let user pick which clocks to show in widget
  - [ ] Save widget configuration
- [ ] Set up WidgetUpdateWorker.kt
  - [ ] WorkManager periodic task every 60 seconds
  - [ ] Efficient — minimal battery impact
  - [ ] Handle doze mode properly
- [ ] Register widget in AndroidManifest.xml
  - [ ] Widget provider metadata
  - [ ] Widget preview image
  - [ ] Min/max sizes
- [ ] Widget tap action → opens app
- [ ] Verify: Add widget to home screen
- [ ] Verify: Widget shows correct times
- [ ] Verify: Widget updates within 60 seconds
- [ ] Verify: Widget survives phone reboot
- [ ] Verify: Widget works on MIUI/HyperOS launcher
- [ ] Verify: Removing clock from app updates widget

**Phase 4 Complete:** [ ] _(check when all above done)_

---

## Phase 5: Polish & Ship 🚀
**Target: 1-2 hours | Agents: DESIGNER + TESTER + SHIPPER**

### UI Polish
- [ ] Build AboutScreen.kt
  - [ ] "ClockJacked ⚡" header
  - [ ] Credits: Jack, Jake & Campbell
  - [ ] Tagline and story
  - [ ] Version number
  - [ ] Subtle animation on load
- [ ] Add navigation transitions (slide/fade)
- [ ] Add haptic feedback on:
  - [ ] Clock delete
  - [ ] Clock add
  - [ ] Drag reorder
- [ ] Add empty state for dashboard (no clocks)
- [ ] Verify dark mode looks premium on AMOLED
- [ ] Verify light mode is clean and usable
- [ ] Add system theme following option

### App Icon
- [ ] Design app icon (bold, simple, recognizable)
- [ ] Generate adaptive icon for all densities
- [ ] Verify icon looks good on MIUI launcher

### Testing
- [ ] Run full test checklist (see AGENTS.md)
  - [ ] Cold start < 1 second
  - [ ] All timezones accurate
  - [ ] Rotation doesn't lose state
  - [ ] Background/foreground lifecycle
  - [ ] Process death recovery
  - [ ] 20+ clocks performance
  - [ ] Widget edge cases
- [ ] Test on Xiaomi 14 Pro specifically (or closest emulator)

### Build & Release
- [ ] Configure ProGuard/R8 rules
- [ ] Build debug APK
- [ ] Verify debug APK installs and runs
- [ ] Build release APK (signed)
- [ ] Verify release APK installs and runs
- [ ] Check APK size < 10MB
- [ ] Write release notes for v1.0.0

**Phase 5 Complete:** [ ] _(check when all above done)_

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

## Phase 6: Extras & Personalization 🎨
**See FEATURES.md for full specs, implementation code, and design details.**

### 6A — Home Base Clock _(build first)_
- [ ] Add `isHomeBase` field to ClockEntry data model
- [ ] Add `nickname` field to ClockEntry data model
- [ ] Update DataStore serialization for new fields
- [ ] Pin Home Base clock to top of dashboard
- [ ] Visual treatment: accent glow/border + 🏠 badge
- [ ] Larger time display for Home Base card
- [ ] Calculate all time diffs relative to Home Base (not device timezone)
- [ ] Long-press menu → "Set as Home Base" option
- [ ] Animate clock swap when changing Home Base
- [ ] Verify: Change Home Base → all diffs recalculate
- [ ] Verify: Home Base persists across app restart

### 6B — Traffic Light Call Status _(build after 6A)_
- [ ] Implement `getCallStatus()` function (GREEN/AMBER/RED)
- [ ] Add colored dot indicator (12dp) to right side of clock cards
- [ ] GREEN dot: subtle pulse animation
- [ ] AMBER dot: static
- [ ] RED dot: static
- [ ] Add status label text ("Good time to call" / "Getting late" / "Probably sleeping")
- [ ] Home Base clock does NOT show traffic light
- [ ] Optional: subtle card border tint matching status (5% opacity)
- [ ] Verify: Status changes correctly as time passes
- [ ] Verify: All three states render with correct colors

### 6C — Vibe Labels _(build after 6B)_
- [ ] Implement `getVibeLabel()` function with full time-of-day map
- [ ] Display as secondary text below time on each card
- [ ] Add global toggle in settings to show/hide vibe labels
- [ ] Use TextSecondary color for labels
- [ ] Verify: Labels change correctly throughout the day
- [ ] Verify: Toggle on/off works and persists

### 6D — Custom Nicknames _(can build anytime after Phase 5)_
- [ ] Tap city name → inline edit field appears
- [ ] Save nickname to DataStore
- [ ] Display nickname as primary label, original city as subtitle
- [ ] Empty nickname reverts to original city name
- [ ] Update share text to use nickname when set
- [ ] Verify: Nickname persists across restart
- [ ] Verify: Nickname shows in widget too

### 6E — Breathing Background Tints _(can build anytime after Phase 5)_
- [ ] Implement `getTimeTint()` function with time-based color map
- [ ] Apply tint as background overlay at 7% opacity (dark mode)
- [ ] Apply tint at 10-12% opacity for light mode
- [ ] Ensure text contrast ratios remain accessible
- [ ] Smooth transition between tint periods
- [ ] Verify: Tints are subtle and don't overpower text
- [ ] Verify: Looks good on AMOLED black background

### 6F — Quick Share
- [ ] Long-press clock card → context menu / bottom sheet
- [ ] Menu options: Copy time, Share, Set Home Base, Edit nickname, Delete
- [ ] "Copy time" → formatted text to clipboard + snackbar
- [ ] "Share" → Android share sheet with formatted message
- [ ] Format: "🕐 It's 3:47 PM in Bali right now (+8h ahead)"
- [ ] Use nickname in share text when available
- [ ] Verify: Copy works, share sheet opens correctly

### 6G — Crew Mode _(most complex — build last)_
- [ ] Allow users to mark clocks as "crew" members
- [ ] Build horizontal 24-hour timeline view
- [ ] Map each crew member's awake hours onto timeline
- [ ] Calculate and highlight overlap "green zone"
- [ ] Tap green zone → show time in each person's timezone
- [ ] Handle "no overlap" case with fun message
- [ ] Customizable awake hours per crew member (default 8 AM–10 PM)
- [ ] Accessible via tab or button on dashboard
- [ ] Verify: Overlap calculation is correct across date line
- [ ] Verify: Works with 2, 3, 4+ crew members

### 6H — Easter Eggs _(sprinkle throughout)_
- [ ] No search results → "That's not a real place, mate. 🤷"
- [ ] 15+ clocks → "You don't know people in that many time zones. 😄"
- [ ] All same timezone → "Everyone's in the same zone! Go see them. 🤝"
- [ ] Adding clock at 3 AM → "Adding clocks at 3 AM? You good? 😅"
- [ ] Deleting last clock → "Are you sure? It's lonely without any clocks. 🥺"
- [ ] About screen: tap crew names for fun subtitles
- [ ] Each easter egg shows max once per session (no spam)

**Phase 6 Complete:** [ ] _(check when all above done)_

---

## Progress Summary

| Phase | Status | Started | Completed |
|-------|--------|---------|-----------|
| Phase 1: Foundation | ⬜ Not Started | — | — |
| Phase 2: Core Display | ⬜ Not Started | — | — |
| Phase 3: Add/Remove/Persist | ⬜ Not Started | — | — |
| Phase 4: Widget | ⬜ Not Started | — | — |
| Phase 5: Polish & Ship | ⬜ Not Started | — | — |
| Phase 6: Extras | ⬜ Not Started | — | — |

### Status Key
- ⬜ Not Started
- 🟡 In Progress
- ✅ Complete
- 🔴 Blocked

---

*Check items off as you go. Update the Progress Summary table after each phase.* ⚡
