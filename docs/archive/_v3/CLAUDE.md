# CLAUDE.md — ClockJacked Project Instructions

> **Read this file first. Then read CLOCKJACKED.md, AGENTS.md, TODO.md, FEATURES.md, ASSETS.md, and WIDGET-FIX.md before writing any code.**

> **⚠️ CRITICAL: Widgets are currently broken on the target device (Xiaomi 14 Pro / HyperOS). Read WIDGET-FIX.md IMMEDIATELY and fix before any other work.**

---

## Project Summary

**ClockJacked** is an Android world clock app built with Kotlin + Jetpack Compose. It displays multiple time zones with a clean, ad-free, dark-first UI and includes a home screen widget. The app must work 100% offline.

---

## Critical Rules — Never Break These

### DO:
- Read ALL .md files in the project root before starting any work
- Follow the phased build strategy in CLOCKJACKED.md
- Adopt the appropriate agent role from AGENTS.md for each task
- Update TODO.md as you complete tasks (check off items)
- Write clean, well-commented Kotlin code
- Use `java.time` for ALL time operations
- Use Jetpack Compose + Material 3 for ALL UI
- Use DataStore Preferences for persistence
- Use Jetpack Glance for the widget
- Keep the APK under 10MB
- Support Android 8.0+ (API 26+)
- Target Android 14 (API 34)
- Test on Xiaomi devices (MIUI/HyperOS considerations)
- Commit logically with clear messages

### DO NOT:
- Add ANY ad SDK, analytics, tracking, or telemetry
- Add internet permission (app is fully offline)
- Use java.util.Date or Calendar (use java.time only)
- Over-engineer — this is a clock app
- Add splash screens longer than 300ms
- Use any deprecated Compose APIs
- Skip error handling or edge cases
- Leave TODO comments in shipped code
- Add dependencies without justification

---

## Tech Stack (Locked In)

```
Language:           Kotlin 1.9+
UI Framework:       Jetpack Compose (BOM latest stable)
Design System:      Material 3 (Material You)
Architecture:       MVVM (ViewModel + StateFlow)
Persistence:        DataStore Preferences
Widget:             Jetpack Glance 1.0+
Async:              Kotlin Coroutines + Flow
Build System:       Gradle Kotlin DSL
Min SDK:            26
Target SDK:         34
Compile SDK:        34
```

---

## Project Structure

```
app/src/main/java/com/clockjacked/app/
├── ClockJackedApp.kt                # Application class (if needed)
├── MainActivity.kt                  # Single activity entry point
├── ui/
│   ├── theme/
│   │   ├── Theme.kt                 # Material 3 theme definition
│   │   ├── Color.kt                 # Color palette (dark-first)
│   │   └── Type.kt                  # Typography scale
│   ├── navigation/
│   │   └── NavGraph.kt              # Navigation compose setup
│   ├── screens/
│   │   ├── DashboardScreen.kt       # Main clock list
│   │   ├── AddClockScreen.kt        # Search & add timezone
│   │   └── AboutScreen.kt           # Credits page
│   └── components/
│       ├── ClockCard.kt             # Individual clock display card
│       ├── TimezoneSearchBar.kt     # Search input with results
│       ├── DayNightIndicator.kt     # Sun/moon icon
│       └── EmptyState.kt            # Shown when no clocks added
├── viewmodel/
│   └── ClockViewModel.kt            # Main ViewModel
├── data/
│   ├── model/
│   │   └── ClockEntry.kt            # Data class for a saved clock
│   ├── repository/
│   │   └── ClockRepository.kt       # Data operations
│   ├── TimezoneDatabase.kt          # Static city→timezone mappings
│   └── PreferencesManager.kt        # DataStore wrapper
├── widget/
│   ├── ClockJackedWidget.kt         # Glance widget composable
│   ├── WidgetConfigActivity.kt      # Widget config screen
│   └── WidgetUpdateWorker.kt        # WorkManager for updates
└── util/
    ├── TimeFormatter.kt             # Time/date formatting utils
    └── TimeDiffCalculator.kt        # Offset calculation utils
```

---

## Build Phases — Execute In Order

### Phase 1: Foundation 🏗️
**Agent: ARCHITECT**
1. Initialize Android project with proper Gradle setup
2. Configure all dependencies (Compose, Material 3, DataStore, Glance)
3. Set up theme (Color.kt, Type.kt, Theme.kt) — dark-first
4. Create data models (ClockEntry)
5. Build TimeFormatter and TimeDiffCalculator utils
6. **Verify:** Project compiles, theme renders

### Phase 2: Core Clock Display ⚙️
**Agents: ENGINEER + DESIGNER**
1. Build ClockCard composable with live time updates
2. Build ClockViewModel with StateFlow for clock list
3. Build DashboardScreen with LazyColumn of ClockCards
4. Pre-load 4 default clocks (SLC, Honolulu, Liverpool, Bali)
5. Implement 1-second tick updates via coroutine
6. Add day/night indicator logic
7. **Verify:** 4 clocks display with live updating times

### Phase 3: Add/Remove/Persist 🔧
**Agent: ENGINEER**
1. Build TimezoneDatabase with 300+ city mappings
2. Build PreferencesManager (DataStore)
3. Build ClockRepository
4. Build AddClockScreen with search functionality
5. Implement swipe-to-delete on DashboardScreen
6. Implement drag-to-reorder
7. Wire persistence — clocks survive app restart
8. **Verify:** Add, remove, reorder all work and persist

### Phase 4: Widget 📱
**Agent: WIDGET_SMITH**
1. Build ClockJackedWidget with Glance
2. Support small (2x1), medium (4x1), large (4x2) sizes
3. Build WidgetConfigActivity for clock selection
4. Set up WorkManager for 60-second updates
5. Style widget to match app dark theme
6. **Verify:** Widget displays, updates, and survives reboot

### Phase 5: Polish & Ship 🚀
**Agents: DESIGNER + TESTER + SHIPPER**
1. Build AboutScreen with crew credits
2. Add enter/exit animations for navigation
3. Add haptic feedback on interactions
4. Run full test checklist from AGENTS.md
5. Generate app icon (simple, bold "CJ" or clock icon)
6. Configure R8/ProGuard
7. Build debug and release APKs
8. **Verify:** Full test pass, APK < 10MB

---

## Coding Standards

### Kotlin Style
```kotlin
// Use data classes for models
data class ClockEntry(
    val id: String = UUID.randomUUID().toString(),
    val cityName: String,
    val timezoneId: String,    // IANA timezone ID
    val flagEmoji: String,
    val position: Int = 0
)

// Use StateFlow in ViewModels
private val _clocks = MutableStateFlow<List<ClockEntry>>(emptyList())
val clocks: StateFlow<List<ClockEntry>> = _clocks.asStateFlow()

// Use sealed classes for UI state
sealed class DashboardState {
    data object Loading : DashboardState()
    data class Success(val clocks: List<ClockEntry>) : DashboardState()
    data class Error(val message: String) : DashboardState()
}
```

### Compose Style
```kotlin
// Composables: PascalCase, descriptive names
@Composable
fun ClockCard(
    entry: ClockEntry,
    currentTime: ZonedDateTime,
    onDelete: (ClockEntry) -> Unit,
    modifier: Modifier = Modifier  // Always accept modifier
) { ... }

// Preview every composable
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun ClockCardPreview() { ... }
```

### Comment Style
```kotlin
// Explain WHY, not WHAT
// DST offset can change mid-year, so we recalculate on every tick
val currentOffset = zoneId.rules.getOffset(Instant.now())
```

---

## Default Clock Configuration

```kotlin
val DEFAULT_CLOCKS = listOf(
    ClockEntry(
        cityName = "Salt Lake City",
        timezoneId = "America/Denver",
        flagEmoji = "🇺🇸",
        position = 0
    ),
    ClockEntry(
        cityName = "Honolulu",
        timezoneId = "Pacific/Honolulu",
        flagEmoji = "🇺🇸",
        position = 1
    ),
    ClockEntry(
        cityName = "Liverpool",
        timezoneId = "Europe/London",
        flagEmoji = "🇬🇧",
        position = 2
    ),
    ClockEntry(
        cityName = "Bali",
        timezoneId = "Asia/Makassar",
        flagEmoji = "🇮🇩",
        position = 3
    ),
)
```

---

## Performance Budgets

| Metric | Target |
|--------|--------|
| Cold start | < 1 second |
| Clock update interval | Exactly 1 second |
| Search response | < 100ms |
| APK size | < 10MB |
| Memory usage | < 50MB RAM |
| Widget update | Every 60 seconds |
| UI frame rate | 60fps (no jank) |

---

## Error Handling

- Invalid timezone ID → fall back to UTC, show warning
- DataStore corruption → reset to defaults, show toast
- Widget removed → clean up WorkManager
- Process death → restore full state from DataStore
- Unknown city in search → show "No results" with suggestion to search by country

---

## Git Commit Convention

```
feat: add clock dashboard with live updates
fix: correct DST offset calculation for half-hour zones
style: refine clock card dark theme colors
refactor: extract time formatting to utility class
docs: update TODO with completed Phase 1
chore: configure R8 rules for release build
```

---

### Phase 6: Extras & Personalization 🎨
**See FEATURES.md for full specs. Build in this order:**
1. Home Base Clock (data model update + UI pinning)
2. Traffic Light Call Status (call status logic + colored dots)
3. Vibe Labels (time-of-day string mapping)
4. Custom Nicknames (inline edit + persistence)
5. Breathing Background Tints (time-based color overlay)
6. Quick Share (long-press context menu + clipboard)
7. Crew Mode (timeline overlap calculator + UI)
8. Easter Eggs (sprinkle throughout)

---

*Read CLOCKJACKED.md for full feature spec. Read AGENTS.md for agent roles. Read FEATURES.md for extras. Check TODO.md for current progress.* ⚡
