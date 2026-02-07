# ClockJacked v4 — Claude Code Implementation Prompt

Copy and paste the appropriate prompt below into Claude Code.

---

## Main Prompt (paste this):

```
Read ALL .md files in the _v4/ folder in this exact order:
1. _v4/CLAUDE.md (master instructions)
2. _v4/CLOCKJACKED.md (core spec)
3. _v4/AGENTS.md (agent roles)
4. _v4/TODO.md (task tracker)
5. _v4/FEATURES.md (extras & personalization)
6. _v4/ASSETS.md (branding, icon, profile photo)
7. _v4/WIDGET-FIX.md (widget debugging — CRITICAL)
8. _v4/AUDIO.md (background music feature)

Also read the 3 skills in _v4/.claude/skills/:
- android-compose/SKILL.md
- android-widget/SKILL.md
- timezone-engine/SKILL.md

This is ClockJacked — an Android world clock app (Kotlin + Jetpack Compose + Material 3). The app is partially built. Audit the existing codebase against TODO.md and pick up where we left off.

PRIORITIES (in order):
1. FIX WIDGETS FIRST — they show "Can't show content" on Xiaomi 14 Pro. Follow WIDGET-FIX.md debug checklist
2. Complete any unfinished core phases (1-5) from TODO.md
3. Implement the background music feature from AUDIO.md:
   - Place _v4/like_a_bird_30s.mp3 → app/src/main/assets/audio/like_a_bird_30s.mp3
   - Place _v4/like_a_bird_96.mp3 → app/src/main/assets/audio/like_a_bird_full.mp3
   - Build MusicManager singleton per AUDIO.md spec
   - Add mute toggle to dashboard top bar
   - Wire lifecycle (pause on background, resume on foreground)
   - Persist mute state in DataStore
4. Implement assets from ASSETS.md:
   - Place _v4/jack_profile_tight.jpg → app/src/main/res/drawable/jack_profile.jpg
   - Build the About screen with circular profile photo and crew credits
   - Generate or create app icon per ASSETS.md spec
5. Implement Phase 6 extras from FEATURES.md in order:
   Home Base → Traffic Lights → Vibe Labels → Nicknames → Breathing Tints → Quick Share → Crew Mode → Easter Eggs
6. Implement expanded widget selection from WIDGET-FIX.md:
   Quick Glance (2x1) → The Crew (4x2) → Duo (4x1) → Next Call (3x1) → Timeline (5x1)

Update _v4/TODO.md as you complete each task. Check off items and update the progress table.

Work phase by phase. Build it clean. Lets go.
```

---

## Quick Fix Prompt (if you just want the widget fixed):

```
Read _v4/WIDGET-FIX.md and _v4/.claude/skills/android-widget/SKILL.md.

The ClockJacked widget shows "Can't show content" on my Xiaomi 14 Pro (HyperOS). Follow the debug checklist in WIDGET-FIX.md — check initialLayout XML, wrap provideGlance in try-catch, add DataStore fallback defaults, and handle MIUI background restrictions. Fix it and verify it renders.
```

---

## Audio-Only Prompt (if you just want the music added):

```
Read _v4/AUDIO.md.

Add background music to ClockJacked:
1. Copy _v4/like_a_bird_30s.mp3 to app/src/main/assets/audio/like_a_bird_30s.mp3
2. Copy _v4/like_a_bird_96.mp3 to app/src/main/assets/audio/like_a_bird_full.mp3
3. Build the MusicManager singleton from AUDIO.md
4. Auto-play the 30s loop at 30% volume with 2s fade-in on app open
5. Add mute toggle button (volume icon) to the dashboard TopAppBar
6. Pause on background, resume on foreground, release on destroy
7. Persist mute state in DataStore so it remembers across restarts
```

---

## v4 Folder Structure

```
_v4/
├── CLAUDE.md                    ← Master instructions
├── CLOCKJACKED.md               ← Core app spec
├── AGENTS.md                    ← AI agent roles
├── TODO.md                      ← Task tracker (update as you go)
├── FEATURES.md                  ← Phase 6 extras
├── ASSETS.md                    ← Branding, icon, photo specs
├── WIDGET-FIX.md                ← Widget debug + expanded widgets
├── AUDIO.md                     ← Background music spec
├── CLAUDE-CODE-PROMPT.md        ← This file
├── jack_profile.jpg             ← Profile photo (wide crop)
├── jack_profile_tight.jpg       ← Profile photo (tight crop — USE THIS)
├── like_a_bird_30s.mp3          ← 30s loop (550KB — default)
├── like_a_bird_96.mp3           ← Full track (2.3MB — optional)
└── .claude/
    └── skills/
        ├── android-compose/SKILL.md
        ├── android-widget/SKILL.md
        └── timezone-engine/SKILL.md
```
