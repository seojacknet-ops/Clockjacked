package com.clockjacked.app.util

import java.time.ZoneId
import java.time.ZonedDateTime

enum class CallStatus {
    GREEN,
    AMBER,
    RED
}

/** Determine whether it's a good time to call someone in the given timezone */
fun getCallStatus(timezoneId: String): CallStatus {
    val now = ZonedDateTime.now(ZoneId.of(timezoneId))
    val hour = now.hour
    val minute = now.minute
    val timeDecimal = hour + (minute / 60.0)

    return when {
        timeDecimal in 9.0..21.0 -> CallStatus.GREEN
        timeDecimal in 7.0..9.0 -> CallStatus.AMBER
        timeDecimal in 21.0..22.5 -> CallStatus.AMBER
        else -> CallStatus.RED
    }
}
