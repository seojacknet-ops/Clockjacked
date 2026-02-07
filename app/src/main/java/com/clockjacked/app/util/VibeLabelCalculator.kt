package com.clockjacked.app.util

import java.time.ZoneId
import java.time.ZonedDateTime

/** Get a human-readable vibe label for the current time of day in a timezone */
fun getVibeLabel(timezoneId: String): String {
    val now = ZonedDateTime.now(ZoneId.of(timezoneId))
    val minutesSinceMidnight = now.hour * 60 + now.minute

    return when {
        minutesSinceMidnight < 300  -> "Deep sleep \uD83D\uDE34"
        minutesSinceMidnight < 390  -> "Early birds only \uD83D\uDC26"
        minutesSinceMidnight < 540  -> "Morning coffee \u2615"
        minutesSinceMidnight < 720  -> "Getting stuff done \uD83D\uDCAA"
        minutesSinceMidnight < 810  -> "Lunch vibes \uD83C\uDF5C"
        minutesSinceMidnight < 1050 -> "Peak hours \uD83D\uDD25"
        minutesSinceMidnight < 1170 -> "Winding down \uD83C\uDF05"
        minutesSinceMidnight < 1290 -> "Evening chill \uD83D\uDECB\uFE0F"
        else                        -> "Night owl hours \uD83E\uDD89"
    }
}
