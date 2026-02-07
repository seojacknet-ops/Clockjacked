---
name: android-compose
description: Jetpack Compose + Material 3 Android development skill. Use when building UI composables, setting up themes, handling navigation, managing state with ViewModel + StateFlow, or creating Material 3 components. Covers Compose BOM setup, dark-first theming, MVVM architecture, LazyColumn performance, animations, and Xiaomi/MIUI-specific rendering fixes. Trigger on any Android UI, theme, composable, navigation, or state management task.
---

# Android Compose + Material 3 Skill

## Gradle Setup (Kotlin DSL)

Use Compose BOM to align all Compose versions. Never mix Compose versions manually.

```kotlin
// build.gradle.kts (app level)
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose") // Required for Kotlin 2.0+
}

android {
    compileSdk = 35
    defaultConfig {
        minSdk = 26
        targetSdk = 35
    }
    buildFeatures { compose = true }
}

dependencies {
    val composeBom = platform("androidx.compose:compose-bom:2024.12.01")
    implementation(composeBom)
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.activity:activity-compose:1.9.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.5")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.5")
    implementation("androidx.navigation:navigation-compose:2.8.0")
    implementation("androidx.datastore:datastore-preferences:1.1.1")
    debugImplementation("androidx.compose.ui:ui-tooling")
}
```

## Dark-First AMOLED Theme Pattern

```kotlin
// Color.kt
val DarkBackground = Color(0xFF000000)      // True AMOLED black
val DarkSurface = Color(0xFF1A1A1A)
val DarkCard = Color(0xFF242424)
val Accent = Color(0xFF7C4DFF)
val TextPrimary = Color(0xFFFFFFFF)
val TextSecondary = Color(0xFFB0B0B0)

// Theme.kt — always default to dark
@Composable
fun ClockJackedTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (darkTheme) dynamicDarkColorScheme(LocalContext.current)
            else dynamicLightColorScheme(LocalContext.current)
        }
        darkTheme -> darkColorScheme(
            background = DarkBackground,
            surface = DarkSurface,
            primary = Accent,
            onBackground = TextPrimary,
            onSurface = TextPrimary,
        )
        else -> lightColorScheme(/* light colors */)
    }
    MaterialTheme(colorScheme = colorScheme, content = content)
}
```

## ViewModel + StateFlow Pattern

```kotlin
class ClockViewModel(private val repo: ClockRepository) : ViewModel() {
    private val _clocks = MutableStateFlow<List<ClockEntry>>(emptyList())
    val clocks: StateFlow<List<ClockEntry>> = _clocks.asStateFlow()

    // Tick every second for live time — use whileSubscribed to auto-cancel
    val currentTick: StateFlow<Long> = flow {
        while (true) {
            emit(System.currentTimeMillis())
            delay(1000L)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), System.currentTimeMillis())
}

// Collect in Compose — ALWAYS use lifecycle-aware collection
@Composable
fun DashboardScreen(viewModel: ClockViewModel = viewModel()) {
    val clocks by viewModel.clocks.collectAsStateWithLifecycle()
    val tick by viewModel.currentTick.collectAsStateWithLifecycle()
    // UI here
}
```

## LazyColumn Performance Rules

- Always provide `key` parameter for items: `items(clocks, key = { it.id })`
- Never create new lambdas inside `items {}` — hoist callbacks
- Use `remember` for derived calculations inside item composables
- Use `Modifier.animateItem()` for reorder animations (Compose 1.7+)

## Common Pitfalls

1. **Recomposition hell:** Never pass `System.currentTimeMillis()` directly as param — use a StateFlow tick
2. **State loss on rotation:** Always use `rememberSaveable` for UI state or persist in ViewModel
3. **Navigation args:** Use string route args, not parcelables for Nav Compose
4. **DataStore on main thread:** Always access DataStore via coroutine, never `.first()` on main
5. **Xiaomi/MIUI:** Test edge-to-edge rendering — MIUI may add extra status bar padding. Use `WindowCompat.setDecorFitsSystemWindows(window, false)` and handle insets manually

## Swipe-to-Delete Pattern

```kotlin
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeableClockCard(entry: ClockEntry, onDelete: () -> Unit) {
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            if (value == SwipeToDismissBoxValue.EndToStart) {
                onDelete()
                true
            } else false
        }
    )
    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = { /* Red delete background */ },
        content = { ClockCard(entry) }
    )
}
```

## Navigation Setup

```kotlin
@Composable
fun ClockJackedNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(navController, startDestination = "dashboard") {
        composable("dashboard") { DashboardScreen(navController) }
        composable("add_clock") { AddClockScreen(navController) }
        composable("about") { AboutScreen(navController) }
    }
}
```
