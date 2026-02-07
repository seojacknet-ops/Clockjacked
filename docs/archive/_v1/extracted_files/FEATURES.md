# ⚡ FEATURES.md — ClockJacked Extras & Personalizations

> **These features go BEYOND the MVP. Build these AFTER the core app (Phases 1-5) is complete and stable.**
> **Priority order: top = build first, bottom = build last.**

---

## Feature 1: 🏠 Home Base Clock

**Priority:** HIGH — This changes how the entire app feels.

### Concept
One clock is designated as "Home Base." This is where the user currently is. All time differences across the app are calculated relative to this clock, not the device timezone.

### Behavior
- Home Base clock pins to the top of the dashboard, always
- Home Base card has a distinct visual treatment:
  - Subtle accent border or glow (use primary accent color at 30% opacity)
  - Small "🏠" or home icon badge in the corner
  - Slightly larger time display than other cards
- All other clocks show time difference relative to Home Base (not device timezone)
- When user travels, they change Home Base → all diffs recalculate instantly

### Implementation Notes
```kotlin
data class ClockEntry(
    val id: String,
    val cityName: String,
    val nickname: String? = null,    // Feature 4
    val timezoneId: String,
    val flagEmoji: String,
    val position: Int,
    val isHomeBase: Boolean = false   // <-- Add this
)

// In ViewModel: filter home base to top, calculate all diffs relative to it
fun getTimeDiffFromHome(targetZone: ZoneId): TimeDiff {
    val homeZone = ZoneId.of(homeBaseClock.timezoneId)
    return calculateTimeDifference(homeZone, targetZone)
}
```

### UX Flow
- Default: First clock in list is Home Base on first launch
- Change: Long-press any clock → "Set as Home Base"
- Visual: Smooth animation when switching Home Base (old one slides down, new one slides up)

---

## Feature 2: 🟢🟡🔴 Call Status Traffic Lights

**Priority:** HIGH — This is the killer feature. The reason to open the app.

### Concept
Every non-Home-Base clock displays a traffic light indicator showing whether it's a good time to call/FaceTime someone in that timezone.

### Status Rules
```
🟢 GREEN  — Great time to call
             Target local time: 9:00 AM – 9:00 PM
             Label: "Good time to call"

🟡 AMBER  — Possible but early/late  
             Target local time: 7:00 AM – 9:00 AM  OR  9:00 PM – 10:30 PM
             Label: "Might be busy" (morning) / "Getting late" (evening)

🔴 RED    — They're probably sleeping
             Target local time: 10:30 PM – 7:00 AM
             Label: "Probably sleeping"
```

### Visual Design
- Traffic light is a small colored dot (12dp) on the right side of the clock card
- Dot has a subtle pulse animation on GREEN (inviting — "go ahead, call them")
- Dot is static on AMBER and RED
- Optional: the entire card border can have a very subtle tint matching the status color (5% opacity)

### Implementation
```kotlin
enum class CallStatus {
    GREEN,   // Good to call
    AMBER,   // Possible but not ideal
    RED      // Sleeping / don't disturb
}

fun getCallStatus(timezoneId: String): CallStatus {
    val hour = ZonedDateTime.now(ZoneId.of(timezoneId)).hour
    val minute = ZonedDateTime.now(ZoneId.of(timezoneId)).minute
    val timeDecimal = hour + (minute / 60.0)

    return when {
        timeDecimal in 9.0..21.0 -> CallStatus.GREEN
        timeDecimal in 7.0..9.0 -> CallStatus.AMBER
        timeDecimal in 21.0..22.5 -> CallStatus.AMBER
        else -> CallStatus.RED
    }
}
```

### Colors
```
Green dot:  #4CAF50 (Material Green 500)
Amber dot:  #FF9800 (Material Orange 500)  
Red dot:    #F44336 (Material Red 500)
```

### Home Base Exception
- The Home Base clock does NOT show a traffic light (you know what time it is where you are)
- Only "remote" clocks get the indicator

---

## Feature 3: 😴☕🔥🌙 Vibe Labels

**Priority:** MEDIUM — Adds personality, pairs perfectly with traffic lights.

### Concept
A short, human-readable label under each clock describing the vibe of that time of day. More expressive than just a time.

### Label Map
```
12:00 AM – 4:59 AM  →  "Deep sleep 😴"
5:00 AM  – 6:29 AM  →  "Early birds only 🐦"
6:30 AM  – 8:59 AM  →  "Morning coffee ☕"
9:00 AM  – 11:59 AM →  "Getting stuff done 💪"
12:00 PM – 1:29 PM  →  "Lunch vibes 🍜"
1:30 PM  – 5:29 PM  →  "Peak hours 🔥"
5:30 PM  – 7:29 PM  →  "Winding down 🌅"
7:30 PM  – 9:29 PM  →  "Evening chill 🛋️"
9:30 PM  – 11:59 PM →  "Night owl hours 🦉"
```

### Implementation
```kotlin
fun getVibeLabel(timezoneId: String): String {
    val hour = ZonedDateTime.now(ZoneId.of(timezoneId)).hour
    val minute = ZonedDateTime.now(ZoneId.of(timezoneId)).minute
    val time = hour * 60 + minute  // minutes since midnight

    return when {
        time < 300  -> "Deep sleep 😴"
        time < 390  -> "Early birds only 🐦"
        time < 540  -> "Morning coffee ☕"
        time < 720  -> "Getting stuff done 💪"
        time < 810  -> "Lunch vibes 🍜"
        time < 1050 -> "Peak hours 🔥"
        time < 1170 -> "Winding down 🌅"
        time < 1290 -> "Evening chill 🛋️"
        else        -> "Night owl hours 🦉"
    }
}
```

### UX
- Displayed as small secondary text below the time on each card
- Toggle on/off globally in settings (some people just want clean times)
- Uses `TextSecondary` color — subtle, not competing with the actual time
- Home Base clock can show vibe labels too (fun self-awareness)

---

## Feature 4: ✏️ Custom Nicknames

**Priority:** MEDIUM — Dead simple to build, huge personalization payoff.

### Concept
Users can rename any clock with a custom nickname. The timezone and actual city stay accurate, but the display label is whatever they want.

### Examples
```
"Salt Lake City"  →  "Jake's Gaff 🏠"
"Liverpool"       →  "Campbell HQ 👑"  
"Bali"            →  "Paradise 🌴"
"Honolulu"        →  "The Dream 🏝️"
```

### UX Flow
- Tap the city name on any clock card → inline edit field appears
- Type nickname → tap done or tap away to save
- If nickname is empty, revert to original city name
- Nickname persists in DataStore alongside the clock entry
- Original city name shown as small subtitle under nickname: `"Jake's Gaff 🏠"` / `Salt Lake City`

### Data Model Update
```kotlin
data class ClockEntry(
    val id: String,
    val cityName: String,           // Original: "Salt Lake City"
    val nickname: String? = null,   // Custom: "Jake's Gaff 🏠"
    val timezoneId: String,
    val flagEmoji: String,
    val position: Int,
    val isHomeBase: Boolean = false
)

// Display logic
fun ClockEntry.displayName(): String = nickname ?: cityName
```

---

## Feature 5: 🌅 Breathing Background Tints

**Priority:** MEDIUM — Pure visual polish. Makes the app feel premium.

### Concept
Each clock card has a very subtle background color wash that reflects the current time of day in that timezone. Not a full gradient — just a barely-there tint that gives each card a unique "feel."

### Color Map (applied at 5-8% opacity over the card background)
```
Night    (10 PM – 5 AM)   →  Deep Indigo    #1A237E
Dawn     (5 AM – 7 AM)    →  Warm Rose      #E91E63
Morning  (7 AM – 12 PM)   →  Soft Amber     #FFC107
Midday   (12 PM – 2 PM)   →  Bright Gold    #FFD54F
Afternoon(2 PM – 5 PM)    →  Warm Orange    #FF9800
Sunset   (5 PM – 7 PM)    →  Deep Coral     #FF5722
Evening  (7 PM – 10 PM)   →  Cool Purple    #7B1FA2
```

### Implementation
```kotlin
fun getTimeTint(timezoneId: String): Color {
    val hour = ZonedDateTime.now(ZoneId.of(timezoneId)).hour
    val baseColor = when (hour) {
        in 22..23, in 0..4 -> Color(0xFF1A237E)  // Deep Indigo
        in 5..6            -> Color(0xFFE91E63)  // Warm Rose
        in 7..11           -> Color(0xFFFFC107)  // Soft Amber
        in 12..13          -> Color(0xFFFFD54F)  // Bright Gold
        in 14..16          -> Color(0xFFFF9800)  // Warm Orange
        in 17..18          -> Color(0xFFFF5722)  // Deep Coral
        in 19..21          -> Color(0xFF7B1FA2)  // Cool Purple
        else               -> Color.Transparent
    }
    return baseColor.copy(alpha = 0.07f)  // 7% opacity — barely there
}
```

### Design Rules
- Tint is applied as a background overlay on the card, BEHIND all text
- Must maintain text contrast ratios (7% opacity is safe)
- Tint transitions smoothly between periods (animate over 5 minutes)
- In light mode, tints can be slightly stronger (10-12% opacity)

---

## Feature 6: 📋 Quick Share

**Priority:** LOW — Nice utility, quick to build.

### Concept
Long-press any clock card to copy a pre-formatted message about the current time in that zone, ready to paste into WhatsApp/iMessage/Telegram.

### Share Format
```
🕐 It's 3:47 PM in Bali right now (+8h ahead of you)
```

Or with nickname:
```
🕐 It's 3:47 PM at Paradise 🌴 (Bali) right now
```

### UX Flow
- Long-press clock card → bottom sheet or context menu appears
- Options: "Copy time", "Share time", "Set as Home Base", "Edit nickname", "Delete"
- "Copy time" → copies to clipboard + shows snackbar "Copied!"
- "Share time" → opens Android share sheet with formatted text

### Implementation
```kotlin
fun formatShareText(entry: ClockEntry): String {
    val time = getCurrentTime(entry.timezoneId)
    val formatted = time.format(DateTimeFormatter.ofPattern("h:mm a"))
    val name = entry.nickname ?: entry.cityName
    return "🕐 It's $formatted in $name right now"
}
```

---

## Feature 7: 🤝 Crew Mode

**Priority:** LOW — The coolest feature but most complex. Build last.

### Concept
A special view that shows a horizontal 24-hour timeline with all your clocks mapped onto it, highlighting the overlap window where everyone is awake (the "green zone" for group calls).

### Visual Design
```
Timeline (24h strip):
|-----|-----|-----|-----|-----|-----|-----|-----|
12am  3am   6am   9am   12pm  3pm   6pm   9pm  

🏠 Jack (Bali)     [=========AWAKE=========]
🇺🇸 Jake (SLC)                    [=========AWAKE=========]
🇬🇧 Campbell (LPL)        [=========AWAKE=========]

Green overlap:              [====]  ← "Best time for group call: 4-6 PM Bali / 1-3 AM SLC / 8-10 AM Liverpool"
```

### UX
- Accessible via a "👥 Crew" tab or button on the dashboard
- Shows only clocks the user marks as "crew" (not all clocks)
- Awake hours default to 8 AM – 10 PM (customizable per person)
- Green overlap zone is highlighted with accent color
- Tap the green zone → shows the time in each person's zone
- If NO overlap exists → show message: "No good overlap today. Someone's losing sleep 😅"

### Implementation Approach
```kotlin
data class CrewMember(
    val clockEntry: ClockEntry,
    val awakeStart: LocalTime = LocalTime.of(8, 0),
    val awakeEnd: LocalTime = LocalTime.of(22, 0)
)

fun findOverlap(crew: List<CrewMember>): Pair<LocalTime, LocalTime>? {
    // Convert each member's awake window to UTC
    // Find intersection of all UTC windows
    // Convert result back to Home Base timezone
    // Return null if no overlap
}
```

---

## Feature 8: 🐣 Easter Eggs & Micro-Copy

**Priority:** LOW — Sprinkle in throughout development. No dedicated build phase.

### Cheeky Moments

**Search screen — no results:**
```
"That's not a real place, mate. 🤷"
```

**Adding more than 15 clocks:**
```
"Slow down, you don't know people in that many time zones. 😄"
```

**All clocks in the same timezone:**
```
"Everyone's in the same zone! Go outside and see them. 🤝"
```

**Adding a clock at 3 AM local time:**
```
"Adding clocks at 3 AM? You good? 😅"
```

**Deleting the last clock:**
```
"Are you sure? It's lonely without any clocks. 🥺"
```

**About screen tap on crew names:**
- Tap "Jack" → "The wanderer 🌍"
- Tap "Jake" → "The rock 🏔️"
- Tap "Campbell" → "The legend 👑"

**Shake device on dashboard:**
```
"Time is an illusion. But FaceTime schedules aren't. ⏰"
```

### Implementation
- Use `Toast` or `Snackbar` for most easter eggs
- Keep them rare and contextual — they should surprise, not annoy
- Store a flag to only show each easter egg once per session (no spam)

---

## Feature Build Order Summary

| # | Feature | Effort | Impact | Build After |
|---|---------|--------|--------|-------------|
| 1 | Home Base Clock | Medium | 🔥🔥🔥 | Phase 5 |
| 2 | Traffic Light Call Status | Medium | 🔥🔥🔥 | Feature 1 |
| 3 | Vibe Labels | Easy | 🔥🔥 | Feature 2 |
| 4 | Custom Nicknames | Easy | 🔥🔥 | Phase 5 |
| 5 | Breathing Background Tints | Easy | 🔥🔥 | Phase 5 |
| 6 | Quick Share | Easy | 🔥 | Phase 5 |
| 7 | Crew Mode | Hard | 🔥🔥🔥 | Feature 2 |
| 8 | Easter Eggs | Easy | 🔥 | Ongoing |

---

*"Time shouldn't just be numbers. It should tell you something."* ⚡
