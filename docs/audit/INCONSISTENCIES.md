# INCONSISTENCIES.md — ClockJacked Audit Findings

**Date:** 2026-02-07

---

## Issue 1: SDK Version Mismatch in Documentation

**Severity:** Medium (documentation only — code is correct)

| Document | Says | Actual (app/build.gradle.kts) |
|----------|------|------|
| CLAUDE.md:47 | `Kotlin 1.9+` | Kotlin 2.0.21 |
| CLAUDE.md:55 | `Target SDK: 34` | targetSdk = 35 |
| CLAUDE.md:56 | `Compile SDK: 34` | compileSdk = 35 |
| CLAUDE.md:27 | `Target Android 14 (API 34)` | targetSdk = 35 (Android 15) |
| SKILL.md:21 | `compileSdk = 34` | compileSdk = 35 |
| SKILL.md:24 | `targetSdk = 34` | targetSdk = 35 |
| SKILL.md:30 | `compose-bom:2024.09.00` | compose-bom:2024.12.01 |
| _v4/CLAUDE.md | `Target SDK: 34`, `Compile SDK: 34` | 35/35 |

**Resolution:** Update CLAUDE.md Tech Stack and DO list, update SKILL.md code examples to reflect actual values.

---

## Issue 2: CLAUDE.md Project Structure is Outdated

**Severity:** High (misleads Claude Code about file locations)

**Files listed in CLAUDE.md but DON'T EXIST:**
- `widget/BootReceiver.kt` — referenced at CLAUDE.md structure but never implemented

**Files that EXIST but are NOT listed in CLAUDE.md:**
- `audio/MusicManager.kt` — music playback management
- `ui/components/ClockActionSheet.kt` — long-press action sheet
- `ui/screens/CrewModeScreen.kt` — crew overlap timeline view
- `util/CallStatusCalculator.kt` — traffic light call status logic
- `util/VibeLabelCalculator.kt` — time-of-day vibe labels
- `util/TimeTintCalculator.kt` — breathing background tints
- `util/ShareFormatter.kt` — quick share text formatting
- `util/EasterEggManager.kt` — easter egg session management
- `util/CrewOverlapCalculator.kt` — crew overlap calculation
- `widget/ClockJackedWidgetReceiver.kt` — Glance widget receiver

**Total:** 1 phantom file listed, 10 real files missing from structure.

**Resolution:** Update CLAUDE.md project structure to match all 33 actual Kotlin files.

---

## Issue 3: CLAUDE.md Coding Standards ClockEntry is Stale

**Severity:** Low (example code, not functional)

**CLAUDE.md:162-169 shows:**
```kotlin
data class ClockEntry(
    val id: String = UUID.randomUUID().toString(),
    val cityName: String,
    val timezoneId: String,
    val flagEmoji: String,
    val position: Int = 0
)
```

**Actual ClockEntry.kt has 3 additional fields:**
```kotlin
val isHomeBase: Boolean = false,
val nickname: String? = null,
val isCrew: Boolean = false
```

**Resolution:** Update CLAUDE.md example to include Phase 6 fields.

---

## Issue 4: WIDGET-FIX.md Referenced But Not Present in Repo

**Severity:** Low (document was in _v3_widgets.zip, never extracted)

- `_v4/CLAUDE.md` references `WIDGET-FIX.md` as a doc to read
- `v4/CLAUDE-CODE-PROMPT.md` references it multiple times
- The file exists only inside `_v3_widgets.zip` — never extracted to repo

**Resolution:** Archive to `docs/archive/_v3/WIDGET-FIX.md` during restructure.

---

## Issue 5: Duplicate/Redundant Files in Root

**Severity:** Low (gitignored, not affecting build)

| Root File | Also Exists At | Status |
|-----------|---------------|--------|
| `jack_profile_tight.jpg` (52KB) | `_v2/files/jack_profile_tight.jpg` (52KB) | Identical duplicate |
| `ic_launcher_source.png` (1.3MB) | Inside `v5.zip` | Source artwork sitting loose in root |
| `nul` (3KB) | N/A | Windows artifact — JDK bin directory listing |

**Resolution:** Delete root copies of jack_profile_tight.jpg and nul. Move ic_launcher_source.png to docs/assets/.

---

## Issue 6: Version Folder Naming Inconsistency

**Severity:** Low (organizational)

| Expected | Actual | Issue |
|----------|--------|-------|
| `_v1/` | `_v/` | Missing "1" suffix |
| `_v3/` | `_v3_widgets.zip` (no folder) | Never extracted |
| `_v4/` | `_v4/` AND `v4/` | Two v4 folders with different content and naming conventions |
| `_v5/` | `v5.zip` (no folder) | Never extracted |

**Resolution:** During restructure, normalize to `docs/archive/_v1/`, `_v2/`, `_v3/`, `_v4/`, `v4/` (keep v4 separate since it has different content).

---

## Issue 7: CLAUDE.md Doc References Point to Root (Pre-Restructure)

**Severity:** Medium (will break after Phase 5 restructure)

CLAUDE.md line 2-3 says:
> Read this file first. Then read CLOCKJACKED.md, AGENTS.md, TODO.md, and FEATURES.md before writing any code.

After restructure, CLOCKJACKED.md, AGENTS.md, and FEATURES.md will be in `docs/`. References must be updated.

Similarly, CLAUDE.md Phase 6 reference:
> See FEATURES.md for full specs.

And the bottom:
> Read CLOCKJACKED.md for full feature spec. Read AGENTS.md for agent roles. Read FEATURES.md for extras.

**Resolution:** Update all doc references to use `docs/` path after restructure.

---

## Issue 8: .claude/skills/ Directory Missing

**Severity:** Low (skills work from root SKILL.md, but not structured)

- `files.zip` contains 3 skill files: android-compose, android-widget, timezone-engine
- Root `SKILL.md` is just the android-compose skill
- `.claude/` directory only has `settings.local.json` — no `skills/` subdirectory
- Audit target structure expects `.claude/skills/{android-compose,android-widget,timezone-engine}/SKILL.md`

**Resolution:** Create `.claude/skills/` structure and place all 3 skill files during restructure.

---

## Issue 9: No README.md

**Severity:** Low (functional but unprofessional for GitHub repo)

The repo has been pushed to `origin/main` at GitHub but has no README.md for visitors.

**Resolution:** Create a concise README.md with project overview, build instructions, and links to docs.

---

## Summary

| # | Issue | Severity | Phase to Fix |
|---|-------|----------|-------------|
| 1 | SDK version mismatch in docs | Medium | Phase 5 (update CLAUDE.md) |
| 2 | CLAUDE.md project structure outdated | High | Phase 5 (update CLAUDE.md) |
| 3 | ClockEntry example stale | Low | Phase 5 (update CLAUDE.md) |
| 4 | WIDGET-FIX.md not extracted | Low | Phase 5 (archive to docs/) |
| 5 | Duplicate/redundant files in root | Low | Phase 4 (cleanup) |
| 6 | Version folder naming inconsistent | Low | Phase 5 (restructure) |
| 7 | Doc references point to root | Medium | Phase 5 (update paths) |
| 8 | .claude/skills/ missing | Low | Phase 5 (create structure) |
| 9 | No README.md | Low | Phase 5 (create) |

**Total:** 9 inconsistencies found, 0 critical, 2 medium, 2 high, 5 low.
All resolvable during Phases 4-5.
