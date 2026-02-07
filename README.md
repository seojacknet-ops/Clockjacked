# ClockJacked

A clean, ad-free Android world clock app built with Kotlin + Jetpack Compose. Displays multiple time zones with a dark-first Material 3 UI and home screen widget. Works 100% offline.

## Features

- Live-updating world clocks with 1-second precision
- 422 searchable cities across all time zones
- Home Base clock with relative time diffs
- Traffic Light call status (green/amber/red)
- Vibe labels and breathing background tints
- Crew Mode — overlap timeline for remote teams
- Drag-to-reorder, swipe-to-delete
- Custom nicknames and quick share
- Jetpack Glance home screen widget (3 sizes)
- Easter eggs

## Tech Stack

- **Language:** Kotlin 2.0.21
- **UI:** Jetpack Compose + Material 3 (BOM 2024.12.01)
- **Architecture:** MVVM (ViewModel + StateFlow)
- **Persistence:** DataStore + kotlinx.serialization
- **Widget:** Jetpack Glance 1.1.1
- **Build:** Gradle Kotlin DSL, AGP 8.7.3
- **Target:** Android 8.0+ (API 26), compiled against API 35

## Build

```bash
export JAVA_HOME="/c/Program Files/Android/openjdk/jdk-21.0.8"
bash gradlew assembleDebug    # ~20MB debug APK
bash gradlew assembleRelease  # ~2.1MB release APK (R8 minified)
```

## Project Docs

- [CLAUDE.md](CLAUDE.md) — Development instructions and coding standards
- [TODO.md](TODO.md) — Build progress tracker
- [docs/](docs/) — Full documentation (specs, agent roles, feature details, audit reports)
