package com.clockjacked.app.util

import com.clockjacked.app.data.model.ClockEntry
import com.clockjacked.app.data.model.displayName
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime

data class CrewMember(
    val entry: ClockEntry,
    val awakeStart: LocalTime = LocalTime.of(8, 0),
    val awakeEnd: LocalTime = LocalTime.of(22, 0)
)

data class OverlapWindow(
    val startUtcMinute: Int,
    val endUtcMinute: Int
) {
    val durationMinutes: Int
        get() = if (endUtcMinute >= startUtcMinute) {
            endUtcMinute - startUtcMinute
        } else {
            (1440 - startUtcMinute) + endUtcMinute
        }

    /** Format overlap times in each crew member's local timezone */
    fun formatForCrew(crew: List<CrewMember>): String {
        return crew.joinToString("\n") { member ->
            val zone = ZoneId.of(member.entry.timezoneId)
            val startLocal = utcMinuteToLocalTime(startUtcMinute, zone)
            val endLocal = utcMinuteToLocalTime(endUtcMinute, zone)
            "${member.entry.displayName()}: ${formatTime(startLocal)}–${formatTime(endLocal)}"
        }
    }

    private fun utcMinuteToLocalTime(utcMinute: Int, zone: ZoneId): LocalTime {
        val now = ZonedDateTime.now(ZoneId.of("UTC"))
        val utcTime = now.withHour(utcMinute / 60).withMinute(utcMinute % 60)
        val local = utcTime.withZoneSameInstant(zone)
        return local.toLocalTime()
    }

    private fun formatTime(time: LocalTime): String {
        val hour = if (time.hour % 12 == 0) 12 else time.hour % 12
        val amPm = if (time.hour < 12) "AM" else "PM"
        return if (time.minute == 0) "$hour $amPm" else "$hour:${time.minute.toString().padStart(2, '0')} $amPm"
    }
}

object CrewOverlapCalculator {

    /**
     * Find the overlapping awake window for all crew members.
     * Uses a 1440-element boolean array (one per UTC minute) — AND all ranges,
     * then scan for the longest consecutive true run.
     */
    fun findOverlap(crew: List<CrewMember>): OverlapWindow? {
        if (crew.size < 2) return null

        // Start with all minutes available
        val available = BooleanArray(1440) { true }

        for (member in crew) {
            val zone = ZoneId.of(member.entry.timezoneId)
            val awakeMinutes = BooleanArray(1440) { false }

            // Convert local awake window to UTC minutes
            val now = ZonedDateTime.now(zone)
            val startLocal = now.withHour(member.awakeStart.hour).withMinute(member.awakeStart.minute)
            val endLocal = now.withHour(member.awakeEnd.hour).withMinute(member.awakeEnd.minute)

            val startUtc = startLocal.withZoneSameInstant(ZoneId.of("UTC"))
            val endUtc = endLocal.withZoneSameInstant(ZoneId.of("UTC"))

            val startMin = startUtc.hour * 60 + startUtc.minute
            val endMin = endUtc.hour * 60 + endUtc.minute

            if (startMin <= endMin) {
                for (i in startMin until endMin) awakeMinutes[i] = true
            } else {
                // Wraps around midnight in UTC
                for (i in startMin until 1440) awakeMinutes[i] = true
                for (i in 0 until endMin) awakeMinutes[i] = true
            }

            // AND with available
            for (i in available.indices) {
                available[i] = available[i] && awakeMinutes[i]
            }
        }

        // Find longest consecutive run of true
        var bestStart = -1
        var bestLen = 0
        var currentStart = -1
        var currentLen = 0

        // Handle wrap-around by checking double the array
        for (i in 0 until 2880) {
            val idx = i % 1440
            if (available[idx]) {
                if (currentStart == -1) currentStart = i
                currentLen++
                if (currentLen > bestLen) {
                    bestLen = currentLen
                    bestStart = currentStart
                }
            } else {
                currentStart = -1
                currentLen = 0
            }
        }

        if (bestLen == 0) return null
        // Cap at 1440 (full day)
        if (bestLen > 1440) bestLen = 1440

        return OverlapWindow(
            startUtcMinute = bestStart % 1440,
            endUtcMinute = (bestStart + bestLen) % 1440
        )
    }
}
