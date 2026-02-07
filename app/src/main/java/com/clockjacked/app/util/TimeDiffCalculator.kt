package com.clockjacked.app.util

import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import kotlin.math.abs

enum class DayRelation {
    YESTERDAY,
    SAME_DAY,
    TOMORROW
}

data class TimeDiff(
    val totalMinutes: Int,
    val dayRelation: DayRelation
) {
    val hours: Int get() = totalMinutes / 60
    val minutes: Int get() = abs(totalMinutes % 60)
    val isAhead: Boolean get() = totalMinutes > 0
    val isBehind: Boolean get() = totalMinutes < 0
    val isSame: Boolean get() = totalMinutes == 0

    /** Format as "+5h", "-8h", "+5:30h", "+13h (tmw)", "-10h (yest)" */
    fun format(): String {
        if (isSame) return "Same time"

        val sign = if (isAhead) "+" else "-"
        val absMinutes = abs(totalMinutes)
        val h = absMinutes / 60
        val m = absMinutes % 60

        val timeStr = if (m == 0) {
            "${sign}${h}h"
        } else {
            "${sign}${h}:${m.toString().padStart(2, '0')}h"
        }

        return when (dayRelation) {
            DayRelation.TOMORROW -> "$timeStr (tmw)"
            DayRelation.YESTERDAY -> "$timeStr (yest)"
            DayRelation.SAME_DAY -> timeStr
        }
    }
}

object TimeDiffCalculator {

    /**
     * Calculate the time difference between the local timezone and a target timezone.
     * Handles DST transitions and half-hour offsets correctly by using
     * the current instant's offset rather than the zone's standard offset.
     */
    fun calculateTimeDifference(
        localZoneId: ZoneId = ZoneId.systemDefault(),
        targetZoneId: ZoneId
    ): TimeDiff {
        val now = ZonedDateTime.now(localZoneId)
        val targetNow = now.withZoneSameInstant(targetZoneId)

        // Calculate offset difference in minutes
        val localOffsetSeconds = now.offset.totalSeconds
        val targetOffsetSeconds = targetNow.offset.totalSeconds
        val diffMinutes = (targetOffsetSeconds - localOffsetSeconds) / 60

        // Determine day relation by comparing dates
        val localDate: LocalDate = now.toLocalDate()
        val targetDate: LocalDate = targetNow.toLocalDate()

        val dayRelation = when {
            targetDate.isAfter(localDate) -> DayRelation.TOMORROW
            targetDate.isBefore(localDate) -> DayRelation.YESTERDAY
            else -> DayRelation.SAME_DAY
        }

        return TimeDiff(
            totalMinutes = diffMinutes,
            dayRelation = dayRelation
        )
    }

    /** Convenience overload using timezone ID strings */
    fun calculateTimeDifference(
        localTimezoneId: String = ZoneId.systemDefault().id,
        targetTimezoneId: String
    ): TimeDiff {
        return try {
            calculateTimeDifference(
                localZoneId = ZoneId.of(localTimezoneId),
                targetZoneId = ZoneId.of(targetTimezoneId)
            )
        } catch (e: Exception) {
            // Fall back to zero diff if timezone is invalid
            TimeDiff(totalMinutes = 0, dayRelation = DayRelation.SAME_DAY)
        }
    }
}
