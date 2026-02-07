# DISCOVERY.md — ClockJacked Repository Audit

**Date:** 2026-02-07
**Branch:** audit/repo-cleanup (from main)

---

## 1A — Repository Map

### Git-Tracked Files: 77 total

**Root (13 files):**
- `.gitignore`, `CLAUDE.md`, `TODO.md`, `CLOCKJACKED.md`, `AGENTS.md`, `FEATURES.md`, `ASSETS.md`, `SKILL.md`
- `build.gradle.kts`, `settings.gradle.kts`, `gradle.properties`, `gradlew`, `gradlew.bat`

**app/ (64 files):**
- `build.gradle.kts`, `proguard-rules.pro`
- 33 Kotlin source files (~1,894 LOC)
- 1 AndroidManifest.xml
- 2 audio assets (like_a_bird_30s.mp3, like_a_bird_full.mp3)
- 1 drawable (jack_profile.jpg)
- 1 layout (widget_loading.xml)
- 15 mipmap icons (5 densities x 3 variants)
- 4 values XML (colors.xml, ic_launcher_background.xml, strings.xml, themes.xml)
- 1 xml (clockjacked_widget_info.xml)

**gradle/ (2 files):** gradle-wrapper.jar, gradle-wrapper.properties

### Gitignored Files (present on disk, not tracked)

| Item | Size | Contents |
|------|------|----------|
| `_v/` (v1) | 120KB | claude-skills.zip, files (1).zip, extracted_files/ (CLAUDE.md, FEATURES.md, TODO.md — all "NOT STARTED"), extracted_skills/frontend-design/SKILL.md |
| `_v2/` | 240KB | files.zip, files/ (ASSETS.md, jack_profile.jpg 60KB, jack_profile_tight.jpg 52KB) |
| `_v3_widgets.zip` | 9KB | **Contains:** WIDGET-FIX.md, CLAUDE.md (v3 variant with widget focus) |
| `_v4/` | 2.9MB | AUDIO.md, CLAUDE.md (v4), like_a_bird_30s.mp3, like_a_bird_96.mp3 |
| `v4/` | 2.8MB | CLAUDE-CODE-PROMPT.md, files (1).zip |
| `v5.zip` | 1.3MB | **Contains:** ASSETS.md (icon spec), ic_launcher_source.png (1.3MB source artwork) |
| `files.zip` | 8KB | **Contains:** SKILL.md (android-compose), android-widget/SKILL.md, timezone-engine/SKILL.md |
| `AuditV6/` | 28KB | files.zip, files/ (REPO-AUDIT.md, REPO-AUDIT-PROMPT.md) |
| `jack_profile_tight.jpg` | 52KB | Duplicate of _v2/files/jack_profile_tight.jpg |
| `ic_launcher_source.png` | 1.3MB | Source artwork for app icon |
| `nul` | 3KB | Windows artifact — JDK bin directory listing |
| `.claude/` | ~1KB | settings.local.json only (no skills/) |
| `.gradle/` | ~8.6MB | Build cache |
| `.kotlin/` | 0 | Empty cache dir |
| `app/build/` | ~322MB | Build output (debug + release APKs + intermediates) |

---

## 1B — Version Folder Analysis

### _v/ (Version 1) — Original Bootstrap
- **Purpose:** Initial project spec delivery before any code was written
- **Key files:** CLAUDE.md, FEATURES.md, TODO.md — all showing "NOT STARTED" status
- **Also contains:** frontend-design/SKILL.md (web-focused, not relevant to Android)
- **Status:** Fully superseded by root docs and built codebase

### _v2/ (Version 2) — Asset Delivery
- **Purpose:** Delivered branding assets (Jack's profile photos)
- **Key files:** ASSETS.md, jack_profile.jpg (60KB wide crop), jack_profile_tight.jpg (52KB tight crop)
- **Deployment:** jack_profile_tight.jpg was deployed as `res/drawable/jack_profile.jpg` (renamed)
- **Status:** Assets deployed, folder is reference only

### _v3_widgets.zip (Version 3) — Widget Fix Iteration
- **Purpose:** Widget debugging guide for MIUI "Can't show content" bug
- **Key files:** WIDGET-FIX.md (comprehensive widget debug guide + expanded widget specs), CLAUDE.md (v3 variant)
- **Note:** This is the "missing" WIDGET-FIX.md referenced in _v4/CLAUDE.md
- **Status:** Widget fixes were implemented; expanded widget styles (5 types) not yet built

### _v4/ (Version 4) — Audio Delivery
- **Purpose:** Delivered audio assets and updated CLAUDE.md
- **Key files:** AUDIO.md (music spec), CLAUDE.md (v4), like_a_bird_30s.mp3, like_a_bird_96.mp3
- **Deployment:** like_a_bird_96.mp3 deployed as `assets/audio/like_a_bird_full.mp3` (renamed per spec)
- **Status:** Audio deployed, folder is reference only

### v4/ (Version 4 duplicate) — Prompt Delivery
- **Purpose:** Delivered CLAUDE-CODE-PROMPT.md for Claude Code session setup
- **Key files:** CLAUDE-CODE-PROMPT.md, files (1).zip
- **Note:** Separate from `_v4/` — different content, confusing naming
- **Status:** Prompt content integrated into workflow

### v5.zip (Version 5) — Icon Assets
- **Purpose:** Delivered updated ASSETS.md and icon source artwork
- **Key files:** ASSETS.md (icon specification), ic_launcher_source.png (1.3MB)
- **Deployment:** Icon was generated from source and deployed to all mipmap densities
- **Status:** Icon deployed, archive is reference only

### files.zip (Skills) — Claude Code Skills
- **Purpose:** Three Claude Code skill files for the project
- **Key files:** android-compose/SKILL.md, android-widget/SKILL.md, timezone-engine/SKILL.md
- **Note:** Root SKILL.md is a copy of android-compose/SKILL.md
- **Status:** Skills not yet placed in `.claude/skills/` directory structure

### AuditV6/ — This Audit
- **Purpose:** Audit instructions for the current cleanup
- **Key files:** REPO-AUDIT.md, REPO-AUDIT-PROMPT.md
- **Status:** Active — being executed now

---

## 1C — Truth Codebase

- **Project root:** `c:\Dev\ClockJacked\` (build.gradle.kts here)
- **App source:** `app/src/main/java/com/clockjacked/app/` (33 Kotlin files)
- **Build config:** compileSdk=35, targetSdk=35, minSdk=26, buildTools=36.0.0
- **Kotlin:** 2.0.21, AGP 8.7.3, Compose BOM 2024.12.01
- **All 6 build phases complete** per TODO.md (completed 2026-02-06 to 2026-02-07)
- **Git:** Initialized on `main`, pushed to origin, working tree clean
- **APK sizes:** Debug ~20MB, Release (R8) ~2.1MB

---

## Health Check Results

*(Appended during Phase 2)*

### 2A — Build Verification
- **Command:** `bash gradlew clean assembleDebug`
- **Result:** BUILD SUCCESSFUL in 62s (38 tasks executed)
- **Warnings:** 1 cosmetic — "Unable to strip libandroidx.graphics.path.so, libdatastore_shared_counter.so" (normal, packaging as-is)
- **Errors:** None
- **SDK Config:** compileSdk=35, targetSdk=35, minSdk=26, buildTools=36.0.0 — all correct
- **ProGuard/R8:** Configured with minify + shrinkResources in release build type
- **Manifest:** All components registered (MainActivity, WidgetConfigActivity, ClockJackedWidgetReceiver)

### 2B — Dependency Audit

All 16 dependencies in app/build.gradle.kts:

| Dependency | Version | Status |
|------------|---------|--------|
| compose-bom | 2024.12.01 | OK — aligns all Compose deps |
| compose-ui | (BOM) | OK |
| compose-ui-graphics | (BOM) | OK |
| compose-ui-tooling-preview | (BOM) | OK |
| material3 | (BOM) | OK |
| material-icons-extended | (BOM) | OK — large but needed for icon variety |
| compose-foundation | (BOM) | OK |
| activity-compose | 1.9.2 | OK |
| lifecycle-viewmodel-compose | 2.8.5 | OK |
| lifecycle-runtime-compose | 2.8.5 | OK |
| navigation-compose | 2.8.0 | OK |
| datastore-preferences | 1.1.1 | OK |
| kotlinx-serialization-json | 1.7.3 | OK |
| glance-appwidget | 1.1.1 | OK |
| glance-material3 | 1.1.1 | OK |
| work-runtime-ktx | 2.9.1 | OK |
| core-ktx | 1.13.1 | OK |

**Findings:**
- No unused dependencies detected (all referenced in code)
- No duplicate or conflicting versions
- Compose BOM properly aligns all Compose library versions
- No known security vulnerabilities
- `material-icons-extended` is the largest dep (~20MB in debug) but R8 shrinks to only used icons

### 2C — Code Health

| Check | Result |
|-------|--------|
| `java.util.Date` / `Calendar` usage | NONE (clean) |
| `TODO` / `FIXME` / `HACK` comments | NONE (clean) |
| Package declarations | All 33 files correct (`com.clockjacked.app.*`) |
| Lint | *(pending)* |

### 2D — Feature Verification

All TODO.md items marked complete have corresponding source files present:
- Phase 1: ClockEntry.kt, Color.kt, Type.kt, Theme.kt, TimeFormatter.kt, TimeDiffCalculator.kt
- Phase 2: ClockCard.kt, ClockViewModel.kt, DashboardScreen.kt, DayNightIndicator.kt
- Phase 3: TimezoneDatabase.kt, PreferencesManager.kt, ClockRepository.kt, TimezoneSearchBar.kt, AddClockScreen.kt, NavGraph.kt
- Phase 4: ClockJackedWidget.kt, ClockJackedWidgetReceiver.kt, WidgetConfigActivity.kt, WidgetUpdateWorker.kt
- Phase 5: AboutScreen.kt, EmptyState.kt
- Phase 6: CallStatusCalculator.kt, VibeLabelCalculator.kt, TimeTintCalculator.kt, ShareFormatter.kt, ClockActionSheet.kt, CrewOverlapCalculator.kt, CrewModeScreen.kt, EasterEggManager.kt, MusicManager.kt

### 2E — Widget Health

| Check | Status |
|-------|--------|
| Widget receiver in Manifest | YES — ClockJackedWidgetReceiver |
| clockjacked_widget_info.xml | YES — res/xml/ |
| widget_loading.xml initialLayout | YES — res/layout/ |
| WidgetConfigActivity registered | YES — with APPWIDGET_CONFIGURE filter |
| BootReceiver | NOT PRESENT — referenced in WIDGET-FIX.md but never built |
| WorkManager (WidgetUpdateWorker) | YES — present in source |

**Gap:** No BootReceiver means widget updates may not restart after device reboot. This is a feature gap, not an audit action item (structural only).

### 2F — Asset Verification

| Asset | Location | Status |
|-------|----------|--------|
| jack_profile.jpg | res/drawable/ | Present (52KB) |
| App icons (all densities) | res/mipmap-*/ | Present (hdpi, mdpi, xhdpi, xxhdpi, xxxhdpi + anydpi-v26) |
| like_a_bird_30s.mp3 | assets/audio/ | Present |
| like_a_bird_full.mp3 | assets/audio/ | Present |
| widget_loading.xml | res/layout/ | Present |
| clockjacked_widget_info.xml | res/xml/ | Present |
| colors.xml | res/values/ | Present |
| strings.xml | res/values/ | Present |
| themes.xml | res/values/ | Present |
| ic_launcher_background.xml | res/values/ | Present |

All referenced assets are present. No orphaned assets detected.
