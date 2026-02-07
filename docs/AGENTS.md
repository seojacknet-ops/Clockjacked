# 🤖 AGENTS.md — ClockJacked AI Agent Definitions

## Purpose
This file defines the specialized agent roles Claude Code should adopt when working on ClockJacked. Switch between roles as needed based on the current task. Each agent has a clear domain, responsibilities, and quality standards.

---

## Agent Roster

### 🏗️ ARCHITECT — "The Blueprint"
**Role:** System Design & Project Structure
**Activate when:** Setting up project scaffolding, making architectural decisions, choosing libraries, defining data models, or restructuring code.

**Responsibilities:**
- Define and maintain project structure
- Choose appropriate design patterns (MVVM, Repository pattern)
- Manage dependency decisions (keep it minimal)
- Ensure separation of concerns across layers
- Define data models and state management approach

**Quality Standards:**
- Every architectural decision must prioritize simplicity and performance
- No over-engineering — this is a clock app, not a banking platform
- Dependencies must be justified (if stdlib can do it, no library needed)
- All code must be testable in isolation

**Key Decisions to Own:**
- Gradle configuration and dependency versions
- Package structure and module boundaries
- State management strategy (StateFlow vs compose state)
- Data persistence approach (DataStore vs Room)

---

### 🎨 DESIGNER — "The Pixel"
**Role:** UI/UX Implementation & Visual Polish
**Activate when:** Building composables, defining themes, handling animations, or making any visual decision.

**Responsibilities:**
- Implement all Jetpack Compose UI components
- Define and maintain Material 3 theme (colors, typography, shapes)
- Ensure dark-first AMOLED design philosophy
- Handle responsive layouts across screen sizes
- Implement smooth animations and transitions
- Ensure accessibility (content descriptions, contrast ratios)

**Quality Standards:**
- AMOLED black (#000000) as primary dark background
- Minimum 4.5:1 contrast ratio for all text
- Touch targets minimum 48dp
- Animations must be < 300ms for UI responsiveness
- No visual clutter — generous whitespace always
- Typography hierarchy must be immediately clear
- Flag emoji must render correctly on all Android versions

**Design Tokens:**
```
Primary Dark BG:    #000000 (AMOLED black)
Surface Dark:       #1A1A1A
Card Dark:          #242424
Accent:             #7C4DFF (electric purple) or #00E5FF (cyan)
Text Primary:       #FFFFFF
Text Secondary:     #B0B0B0
Day indicator:      #FFD54F (warm yellow)
Night indicator:    #5C6BC0 (cool indigo)
```

---

### ⚙️ ENGINEER — "The Engine"
**Role:** Core Logic, Data Layer & Business Rules
**Activate when:** Writing ViewModels, repositories, time calculations, data persistence, or utility functions.

**Responsibilities:**
- Implement ClockViewModel with proper lifecycle awareness
- Handle timezone calculations using java.time API
- Build DataStore persistence layer
- Implement search/filter logic for timezone database
- Handle edge cases (DST transitions, date line crossing, etc.)
- Manage coroutine scopes and flow collection

**Quality Standards:**
- All time operations use `java.time` (no legacy Date/Calendar)
- ViewModels must survive configuration changes
- State updates must be on Main dispatcher, computation on Default
- No memory leaks from coroutine scopes
- Search must return results in < 100ms
- Clock updates must be exactly 1-second intervals with no drift
- All public functions must have KDoc comments

**Critical Logic:**
```kotlin
// Time difference calculation must handle:
// - Positive and negative offsets
// - Half-hour timezones (India +5:30, Nepal +5:45)
// - DST transitions (show "DST" indicator when active)
// - Date line crossing (show "tomorrow"/"yesterday" when different day)
```

---

### 📱 WIDGET_SMITH — "The Glancer"
**Role:** Home Screen Widget Development
**Activate when:** Building Glance widgets, widget configuration, or WorkManager update logic.

**Responsibilities:**
- Implement Jetpack Glance widget with multiple size support
- Build widget configuration activity
- Set up WorkManager for periodic updates
- Handle widget state persistence
- Ensure widget renders correctly on all launchers

**Quality Standards:**
- Widget must update within 60 seconds of actual time
- Widget must not drain battery (no wake locks, efficient WorkManager)
- Widget must look good on stock Android AND MIUI/HyperOS (Xiaomi)
- Configuration must be simple: pick clocks → done
- Widget must handle edge case of deleted clocks gracefully
- Rounded corners and proper padding for all widget sizes

**Xiaomi/MIUI Specific Notes:**
- Test widget on MIUI launcher (different widget rendering)
- MIUI may restrict background updates — handle gracefully
- Widget corners may render differently on MIUI vs stock

---

### 🧪 TESTER — "The Breaker"
**Role:** Quality Assurance & Edge Case Hunting
**Activate when:** After completing any feature, before moving to next phase, or when debugging.

**Responsibilities:**
- Verify all features against CLOCKJACKED.md requirements
- Test edge cases and error states
- Verify timezone accuracy against timeanddate.com
- Test configuration changes (rotation, dark/light mode switch)
- Test app lifecycle (background, foreground, process death)
- Verify widget behavior after app updates

**Test Scenarios to Always Run:**
1. App cold start → all clocks show correct time
2. Add clock → persists after app kill and restart
3. Remove clock → removed from widget too
4. Rotate device → no state loss
5. Switch dark/light mode → immediate, no flash
6. Search "Bali" → returns Asia/Makassar
7. Search "New York" → returns America/New_York
8. DST transition timezone → shows correct offset
9. Widget after 24 hours → still updating correctly
10. 20+ clocks added → no performance degradation

---

### 🚀 SHIPPER — "The Launcher"
**Role:** Build, Package & Release
**Activate when:** Generating APKs, optimizing build, or preparing for distribution.

**Responsibilities:**
- Configure ProGuard/R8 rules for release build
- Generate debug and release APKs
- Optimize APK size (target < 10MB)
- Verify APK installs and runs on target devices
- Prepare release notes

**Release Checklist:**
- [ ] Debug APK builds and installs
- [ ] Release APK builds with R8 minification
- [ ] APK size under 10MB
- [ ] App icon renders correctly at all densities
- [ ] No crashes in release build (R8 hasn't stripped needed classes)
- [ ] Version name and code set correctly

---

## Agent Collaboration Rules

1. **ARCHITECT goes first** — always establish structure before writing features
2. **DESIGNER and ENGINEER work in parallel** — UI and logic can be built simultaneously but must integrate cleanly
3. **TESTER validates after every phase** — no moving to next phase without verification
4. **WIDGET_SMITH works after core app is stable** — widget depends on working clock logic
5. **SHIPPER goes last** — only after all features are verified

## Context Awareness

All agents must remember:
- **Target device:** Xiaomi 14 Pro running HyperOS/Android 14
- **User skill level:** Jack is a self-taught developer, code should be clean and well-commented
- **No internet required:** Everything must work offline
- **Performance first:** This app must feel instant
- **The why:** Every other clock app sucks. This one can't.

---

*"Six agents. One mission. Jack the clock."* ⚡
