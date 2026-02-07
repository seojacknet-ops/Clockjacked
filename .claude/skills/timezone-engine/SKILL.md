---
name: timezone-engine
description: Java.time timezone handling skill for world clock applications. Use when working with timezone conversions, DST transitions, time difference calculations, IANA timezone IDs, city-to-timezone mappings, or time formatting across zones. Covers ZonedDateTime, ZoneId, offset calculations, half-hour timezones, date line crossing, day/night detection, and building searchable timezone databases. Trigger on any timezone, world clock, time conversion, or DST-related task.
---

# Timezone Engine Skill

## Core Principle
Always use `java.time` API. Never use `java.util.Date`, `Calendar`, or `SimpleDateFormat`.

## Essential Imports

```kotlin
import java.time.*
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale
```

## Live Clock Update Pattern

```kotlin
// Get current time in any timezone
fun getCurrentTime(timezoneId: String): ZonedDateTime {
    return ZonedDateTime.now(ZoneId.of(timezoneId))
}

// Format for display
fun formatTime(zdt: ZonedDateTime): String =
    zdt.format(DateTimeFormatter.ofPattern("h:mm:ss a"))

fun formatDate(zdt: ZonedDateTime): String =
    zdt.format(DateTimeFormatter.ofPattern("EEE, MMM d"))
```

## Time Difference Calculation

Handle all edge cases: positive/negative offsets, half-hour zones, different days.

```kotlin
fun calculateTimeDifference(localZone: ZoneId, targetZone: ZoneId): TimeDiff {
    val now = Instant.now()
    val localOffset = localZone.rules.getOffset(now)
    val targetOffset = targetZone.rules.getOffset(now)

    val diffSeconds = targetOffset.totalSeconds - localOffset.totalSeconds
    val hours = diffSeconds / 3600
    val minutes = (diffSeconds % 3600) / 60

    // Determine if target is on a different day
    val localDate = LocalDate.now(localZone)
    val targetDate = LocalDate.now(targetZone)
    val dayDiff = when {
        targetDate.isAfter(localDate) -> DayRelation.TOMORROW
        targetDate.isBefore(localDate) -> DayRelation.YESTERDAY
        else -> DayRelation.SAME_DAY
    }

    return TimeDiff(hours, minutes, dayDiff)
}

data class TimeDiff(val hours: Int, val minutes: Int, val dayRelation: DayRelation)

enum class DayRelation { YESTERDAY, SAME_DAY, TOMORROW }

// Format: "+5h", "-8h", "+5:30h", "+13h (tomorrow)"
fun TimeDiff.format(): String {
    val sign = if (hours >= 0) "+" else ""
    val minutePart = if (minutes != 0) ":${abs(minutes).toString().padStart(2, '0')}" else ""
    val dayPart = when (dayRelation) {
        DayRelation.TOMORROW -> " (tmw)"
        DayRelation.YESTERDAY -> " (yday)"
        DayRelation.SAME_DAY -> ""
    }
    return "${sign}${hours}${minutePart}h$dayPart"
}
```

## Day/Night Detection

```kotlin
fun isDayTime(timezoneId: String): Boolean {
    val hour = ZonedDateTime.now(ZoneId.of(timezoneId)).hour
    return hour in 6..17  // 6 AM to 6 PM = day
}

// More nuanced: dawn/day/dusk/night
enum class TimeOfDay { DAWN, DAY, DUSK, NIGHT }

fun getTimeOfDay(timezoneId: String): TimeOfDay {
    val hour = ZonedDateTime.now(ZoneId.of(timezoneId)).hour
    return when (hour) {
        in 5..6 -> TimeOfDay.DAWN
        in 7..17 -> TimeOfDay.DAY
        in 18..19 -> TimeOfDay.DUSK
        else -> TimeOfDay.NIGHT
    }
}
```

## DST Detection

```kotlin
fun isDST(timezoneId: String): Boolean {
    val zone = ZoneId.of(timezoneId)
    val rules = zone.rules
    return rules.isDaylightSavings(Instant.now())
}

// Get timezone abbreviation (handles DST: "EST" vs "EDT", "GMT" vs "BST")
fun getTimezoneAbbreviation(timezoneId: String): String {
    val zone = ZoneId.of(timezoneId)
    return zone.getDisplayName(TextStyle.SHORT, Locale.getDefault())
}
```

## Half-Hour Timezone Edge Cases

These timezones have non-whole-hour offsets. The time diff calculator MUST handle them:

```
Asia/Kolkata         +5:30  (India)
Asia/Kathmandu       +5:45  (Nepal)
Asia/Yangon          +6:30  (Myanmar)
Australia/Adelaide   +9:30 / +10:30 DST
Australia/Darwin     +9:30  (no DST)
Pacific/Chatham      +12:45 / +13:45 DST (Chatham Islands)
Asia/Tehran          +3:30 / +4:30 DST
Canada/Newfoundland  -3:30 / -2:30 DST
Pacific/Marquesas    -9:30
```

## Searchable City Database Structure

```kotlin
data class CityTimezone(
    val city: String,
    val country: String,
    val timezoneId: String,
    val flagEmoji: String,
    val searchTerms: List<String>  // Alternative names, abbreviations
)

// Example entries
val TIMEZONE_DATABASE = listOf(
    CityTimezone("New York", "United States", "America/New_York", "🇺🇸",
        listOf("NYC", "Manhattan", "Brooklyn", "Eastern")),
    CityTimezone("London", "United Kingdom", "Europe/London", "🇬🇧",
        listOf("UK", "England", "GMT", "BST")),
    CityTimezone("Bali", "Indonesia", "Asia/Makassar", "🇮🇩",
        listOf("Denpasar", "Ubud", "Seminyak", "WITA", "Kuta")),
    CityTimezone("Salt Lake City", "United States", "America/Denver", "🇺🇸",
        listOf("SLC", "Utah", "Mountain")),
    // ... 300+ more
)
```

## Search Implementation

```kotlin
fun searchTimezones(query: String, database: List<CityTimezone>): List<CityTimezone> {
    if (query.isBlank()) return emptyList()
    val q = query.trim().lowercase()
    return database.filter { entry ->
        entry.city.lowercase().contains(q) ||
        entry.country.lowercase().contains(q) ||
        entry.timezoneId.lowercase().contains(q) ||
        entry.searchTerms.any { it.lowercase().contains(q) }
    }.sortedBy { entry ->
        // Exact prefix matches first
        when {
            entry.city.lowercase().startsWith(q) -> 0
            entry.searchTerms.any { it.lowercase().startsWith(q) } -> 1
            entry.country.lowercase().startsWith(q) -> 2
            else -> 3
        }
    }.take(20)
}
```

## Critical: Bali Timezone

Bali uses `Asia/Makassar` (WITA, UTC+8). Do NOT use `Asia/Jakarta` (WIB, UTC+7) — that's western Indonesia. This is a common mistake in timezone apps.

```
Indonesia has 3 timezones:
- WIB  (UTC+7): Asia/Jakarta     — Java, Sumatra, West Kalimantan
- WITA (UTC+8): Asia/Makassar    — Bali, Sulawesi, East/South Kalimantan
- WIT  (UTC+9): Asia/Jayapura    — Papua, Maluku
```

## 1-Second Tick Without Drift

```kotlin
// Naive approach drifts over time:
// while(true) { emit(now); delay(1000) }  // BAD — accumulates drift

// Correct: snap to next second boundary
fun tickEverySecond(): Flow<Long> = flow {
    while (true) {
        val now = System.currentTimeMillis()
        val nextSecond = ((now / 1000) + 1) * 1000
        delay(nextSecond - now)
        emit(nextSecond)
    }
}
```

## IANA Timezone ID Validation

```kotlin
fun isValidTimezone(id: String): Boolean {
    return try {
        ZoneId.of(id)
        true
    } catch (e: Exception) {
        false
    }
}
```
