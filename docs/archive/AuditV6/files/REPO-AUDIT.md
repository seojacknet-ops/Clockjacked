# 🔍 REPO-AUDIT.md — ClockJacked Full Repository Audit & Cleanup

> **This is a high-level instruction plan for Claude Code to audit the entire ClockJacked repository, verify all version folders are implemented, perform a health check, resolve inconsistencies, clean up files, and establish a clean architecture.**

---

## Prompt for Claude Code:

```
Read this file (REPO-AUDIT.md) completely before doing anything.

You are performing a full audit of the ClockJacked Android app repository. This repo has evolved through multiple iteration folders (_v1, _v2, _v3, _v4) and may have accumulated dead code, duplicates, inconsistencies, and structural debt.

Your job is to:
1. Audit every version folder and the main codebase
2. Health check the entire project
3. Clean up and restructure into a professional architecture
4. Produce a written audit report

Execute each phase below IN ORDER. Do not skip phases. Be thorough.
```

---

## Phase 1: Discovery & Inventory
**Goal: Understand what exists before changing anything.**

### 1A — Map the entire repo
```
- List every folder and file in the repo root (2 levels deep)
- List every folder and file inside _v1/, _v2/, _v3/, _v4/ (3 levels deep)
- List the main app source code structure (full depth)
- Record file counts and total sizes per folder
- Identify the actual Android project root (where build.gradle.kts lives)
```

### 1B — Identify what each version folder contains
For each of _v1/, _v2/, _v3/, _v4/:
```
- What .md instruction files exist?
- What assets exist (images, audio, skills)?
- What source code exists (if any)?
- What is the apparent purpose/focus of this version?
- Is there a TODO.md or progress tracker? What's the status?
- Are there any files that DON'T belong here?
```

### 1C — Identify the "truth" codebase
```
- Where is the actual runnable Android project?
- Which version folder's instructions are currently implemented?
- Is the app in a buildable state right now?
- What SDK/dependency versions are currently in use?
```

### Output: Write a DISCOVERY.md summarizing findings before proceeding.

---

## Phase 2: Health Check
**Goal: Verify the app builds, runs, and all implemented features work.**

### 2A — Build verification
```
- Run: ./gradlew clean assembleDebug
- Record: Does it compile? Any warnings? Any errors?
- Check: Gradle dependency versions — any outdated or conflicting?
- Check: Min SDK, target SDK, compile SDK — correct per spec?
- Check: ProGuard/R8 rules — present and valid?
- Check: AndroidManifest.xml — all components registered?
```

### 2B — Dependency audit
```
- List ALL dependencies in build.gradle.kts
- Flag any unused dependencies (imported but never referenced in code)
- Flag any duplicate dependencies (different versions of same lib)
- Flag any deprecated dependencies
- Flag any security vulnerabilities in dependencies
- Verify Compose BOM is used and all Compose deps align
```

### 2C — Code health
```
- Run lint check: ./gradlew lint
- Check for: unused imports, unused variables, dead code
- Check for: TODO/FIXME/HACK comments left in code
- Check for: hardcoded strings that should be in strings.xml
- Check for: hardcoded colors that should be in theme
- Check for: memory leaks (unclosed resources, unscoped coroutines)
- Check for: deprecated API usage (java.util.Date, etc.)
- Verify all Kotlin files have proper package declarations
- Verify no duplicate class definitions across folders
```

### 2D — Feature verification against spec
Cross-reference the actual implemented code against _v4/TODO.md:
```
For each TODO item marked as complete:
- Is it actually implemented in the codebase?
- Does the implementation match the spec?
- Are there any half-finished implementations?

For each TODO item marked as incomplete:
- Is there any partial code for it?
- Are there any stub files or placeholder implementations?
```

### 2E — Widget health (CRITICAL)
```
- Is the widget registered in AndroidManifest.xml?
- Does widget_info.xml exist with correct attributes?
- Does initialLayout XML exist?
- Is provideGlance() wrapped in try-catch?
- Is there a fallback for empty DataStore?
- Is WorkManager configured for updates?
- Is BootReceiver registered?
```

### 2F — Asset verification
```
- Does jack_profile_tight.jpg exist in res/drawable/?
- Are app icons present in all mipmap densities?
- Are audio files in assets/audio/?
- Are any assets referenced in code but missing from files?
- Are any assets present in files but never referenced in code?
```

### Output: Append findings to DISCOVERY.md as "Health Check Results" section.

---

## Phase 3: Inconsistency Detection
**Goal: Find conflicts between version folders, docs, and actual code.**

### 3A — Cross-version conflicts
```
- Compare data models across _v1, _v2, _v3, _v4:
  - Is ClockEntry defined differently in different versions?
  - Are there conflicting field names or types?
- Compare theme/colors across versions:
  - Are color values consistent?
  - Are there conflicting theme definitions?
- Compare feature specs across versions:
  - Do any versions contradict each other?
  - Which version is the source of truth?
```

### 3B — Doc vs code inconsistencies
```
- Compare CLAUDE.md file paths vs actual project structure
- Compare AGENTS.md package structure vs actual packages
- Compare FEATURES.md data model updates vs actual ClockEntry.kt
- Compare ASSETS.md file locations vs actual file locations
- Compare AUDIO.md implementation vs actual MusicManager code
- Compare default clocks in spec vs default clocks in code
- Compare timezone IDs in spec vs timezone IDs in code
```

### 3C — Naming inconsistencies
```
- Check package names: is it consistently com.clockjacked.app?
- Check class naming: PascalCase for classes, camelCase for functions?
- Check file naming: matches class names?
- Check resource naming: snake_case for XML resources?
- Check Compose function naming: PascalCase for composables?
```

### 3D — String/resource inconsistencies
```
- Are there hardcoded strings in Kotlin that should be in strings.xml?
- Are there hardcoded dimensions that should be in dimens.xml?
- Are there duplicate string resources?
- Are there unused string resources?
```

### Output: Create INCONSISTENCIES.md listing every issue found with file paths and line numbers.

---

## Phase 4: File Cleanup
**Goal: Remove dead files, duplicates, and unnecessary artifacts.**

### 4A — Identify files to remove
```
REMOVE:
- Build artifacts: .gradle/, build/, .idea/ (if committed)
- Temp files: *.tmp, *.bak, *.swp, .DS_Store, Thumbs.db
- Duplicate assets across version folders (keep only in _v4 and main project)
- Old/abandoned source files not referenced anywhere
- Empty files and empty directories
- Test output files
- Any generated files that should be in .gitignore

DO NOT REMOVE:
- Version folder .md files (they're documentation history)
- .gitignore
- gradle-wrapper.jar and gradle-wrapper.properties
- Any file actively referenced by the build system
```

### 4B — Identify duplicates
```
- Find files with identical content across different folders
- Find source files with similar names but different content (potential merge conflicts)
- Find assets that exist in both version folders AND the main project
```

### 4C — Execute cleanup
```
- Delete identified files (list every deletion)
- Remove empty directories after file deletion
- Verify build still works after cleanup: ./gradlew clean assembleDebug
```

### Output: Log all deletions in CLEANUP-LOG.md.

---

## Phase 5: Architecture Restructure
**Goal: Establish a clean, professional folder structure.**

### 5A — Target repository structure

```
ClockJacked/
├── .gitignore
├── README.md                          ← Project overview (create if missing)
├── build.gradle.kts                   ← Root build file
├── settings.gradle.kts
├── gradle.properties
├── gradle/
│   └── wrapper/
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
│
├── app/
│   ├── build.gradle.kts               ← App build file
│   ├── proguard-rules.pro
│   └── src/
│       └── main/
│           ├── AndroidManifest.xml
│           ├── assets/
│           │   └── audio/
│           │       ├── like_a_bird_30s.mp3
│           │       └── like_a_bird_full.mp3
│           ├── res/
│           │   ├── drawable/
│           │   │   └── jack_profile.jpg
│           │   ├── layout/
│           │   │   └── widget_loading.xml
│           │   ├── mipmap-*/
│           │   │   └── (app icons at all densities)
│           │   ├── values/
│           │   │   ├── strings.xml
│           │   │   ├── colors.xml
│           │   │   ├── themes.xml
│           │   │   └── ic_launcher_background.xml
│           │   └── xml/
│           │       └── clockjacked_widget_info.xml
│           └── java/com/clockjacked/app/
│               ├── ClockJackedApp.kt
│               ├── MainActivity.kt
│               ├── ui/
│               │   ├── theme/
│               │   │   ├── Color.kt
│               │   │   ├── Type.kt
│               │   │   └── Theme.kt
│               │   ├── navigation/
│               │   │   └── NavGraph.kt
│               │   ├── screens/
│               │   │   ├── DashboardScreen.kt
│               │   │   ├── AddClockScreen.kt
│               │   │   └── AboutScreen.kt
│               │   └── components/
│               │       ├── ClockCard.kt
│               │       ├── TimezoneSearchBar.kt
│               │       ├── DayNightIndicator.kt
│               │       ├── CallStatusDot.kt
│               │       ├── VibeLabel.kt
│               │       └── EmptyState.kt
│               ├── viewmodel/
│               │   └── ClockViewModel.kt
│               ├── data/
│               │   ├── model/
│               │   │   └── ClockEntry.kt
│               │   ├── repository/
│               │   │   └── ClockRepository.kt
│               │   ├── TimezoneDatabase.kt
│               │   └── PreferencesManager.kt
│               ├── audio/
│               │   └── MusicManager.kt
│               ├── widget/
│               │   ├── ClockJackedWidget.kt
│               │   ├── WidgetConfigActivity.kt
│               │   ├── WidgetUpdateWorker.kt
│               │   └── BootReceiver.kt
│               └── util/
│                   ├── TimeFormatter.kt
│                   ├── TimeDiffCalculator.kt
│                   └── CallStatusCalculator.kt
│
├── docs/                               ← All project documentation
│   ├── CLOCKJACKED.md                  ← Core spec
│   ├── AGENTS.md                       ← Agent roles
│   ├── FEATURES.md                     ← Extras spec
│   ├── ASSETS.md                       ← Branding spec
│   ├── AUDIO.md                        ← Music spec
│   ├── WIDGET-FIX.md                   ← Widget debug guide
│   └── CLAUDE-CODE-PROMPT.md           ← Claude Code prompts
│
├── docs/audit/                         ← Audit outputs (generated by this process)
│   ├── DISCOVERY.md
│   ├── INCONSISTENCIES.md
│   ├── CLEANUP-LOG.md
│   └── AUDIT-REPORT.md
│
├── docs/archive/                       ← Version history (read-only reference)
│   ├── _v1/                            ← Original v1 docs
│   ├── _v2/                            ← v2 iteration docs
│   ├── _v3/                            ← v3 iteration docs
│   └── _v4/                            ← v4 iteration docs (source of truth)
│
├── CLAUDE.md                           ← Stays in root (Claude Code reads this automatically)
├── TODO.md                             ← Stays in root (active tracker)
│
└── .claude/
    └── skills/
        ├── android-compose/SKILL.md
        ├── android-widget/SKILL.md
        └── timezone-engine/SKILL.md
```

### 5B — Migration rules
```
1. CLAUDE.md and TODO.md MUST stay in the project root (Claude Code reads root automatically)
2. All other .md docs move to docs/
3. Version folders (_v1 through _v4) move to docs/archive/ as read-only reference
4. Source assets (profile photo, audio) must be in the actual app resource paths
5. Skills stay in .claude/skills/
6. Update all internal file path references in CLAUDE.md after restructure
7. Update CLAUDE-CODE-PROMPT.md paths to reflect new structure
```

### 5C — Execute restructure
```
- Create target directory structure
- Move files to correct locations
- Update path references in all .md files
- Verify build: ./gradlew clean assembleDebug
- Verify app launches
- Verify widget renders
- Git commit: "chore: restructure repo — audit and cleanup complete"
```

---

## Phase 6: Final Audit Report
**Goal: Produce a single summary document.**

### Create AUDIT-REPORT.md with:
```
# ClockJacked Audit Report
Date: [today]

## Summary
- Total files audited: X
- Files removed: X
- Files moved: X
- Inconsistencies found: X
- Inconsistencies resolved: X
- Build status: PASS/FAIL
- Widget status: WORKING/BROKEN
- APK size: X MB

## Feature Implementation Status
(table from TODO.md — current state)

## Issues Found & Resolved
(summary from INCONSISTENCIES.md)

## Files Cleaned Up
(summary from CLEANUP-LOG.md)

## Architecture Changes
(before/after folder structure comparison)

## Remaining Action Items
(anything that couldn't be auto-fixed)

## Recommendations
(suggestions for future maintainability)
```

---

## Critical Rules for This Audit

1. **DO NOT delete any version folder .md files** — archive them, don't destroy them
2. **DO NOT change any logic or features** — this is a structural audit, not a feature sprint
3. **DO back up before major moves** — `git stash` or commit current state first
4. **DO verify the build after EVERY phase** — never leave the repo in a broken state
5. **DO log every action** — file moves, deletions, renames, all documented
6. **DO update CLAUDE.md** — it must reflect the final structure so Claude Code can still navigate
7. **DO update TODO.md** — mark audit-related tasks as complete
8. **ASK before deleting** anything you're unsure about — flag it in the report instead

---

## Estimated Effort

| Phase | Time |
|-------|------|
| Phase 1: Discovery | 10-15 min |
| Phase 2: Health Check | 15-20 min |
| Phase 3: Inconsistencies | 10-15 min |
| Phase 4: Cleanup | 10-15 min |
| Phase 5: Restructure | 15-20 min |
| Phase 6: Report | 5-10 min |
| **Total** | **~60-90 min** |

---

*"A clean repo is a fast repo."* ⚡
