package com.clockjacked.app.util

import com.clockjacked.app.data.model.ClockEntry
import com.clockjacked.app.data.model.displayName
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

object ShareFormatter {

    private val timeFormat = DateTimeFormatter.ofPattern("h:mm a")

    fun formatShareText(entry: ClockEntry, homeBaseTimezoneId: String? = null): String {
        val now = ZonedDateTime.now(ZoneId.of(entry.timezoneId))
        val formatted = now.format(timeFormat)
        val name = entry.displayName()
        return "\uD83D\uDD50 It's $formatted in $name right now"
    }

    fun formatCopyText(entry: ClockEntry, homeBaseTimezoneId: String? = null): String {
        val now = ZonedDateTime.now(ZoneId.of(entry.timezoneId))
        val formatted = now.format(timeFormat)
        val name = entry.displayName()

        if (homeBaseTimezoneId != null) {
            val diff = TimeDiffCalculator.calculateTimeDifference(
                localTimezoneId = homeBaseTimezoneId,
                targetTimezoneId = entry.timezoneId
            )
            if (!diff.isSame) {
                return "\uD83D\uDD50 It's $formatted in $name right now (${diff.format()})"
            }
        }
        return "\uD83D\uDD50 It's $formatted in $name right now"
    }
}
