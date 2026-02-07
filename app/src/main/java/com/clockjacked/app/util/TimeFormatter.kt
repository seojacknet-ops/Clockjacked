package com.clockjacked.app.util

import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

object TimeFormatter {

    private val timeFormat = DateTimeFormatter.ofPattern("h:mm:ss a", Locale.ENGLISH)
    private val dateFormat = DateTimeFormatter.ofPattern("EEE, MMM d", Locale.ENGLISH)
    private val shortTimeFormat = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH)

    /** Format time as "2:45:30 PM" */
    fun formatTime(dateTime: ZonedDateTime): String = dateTime.format(timeFormat)

    /** Format time as "2:45 PM" (no seconds, for widget) */
    fun formatTimeShort(dateTime: ZonedDateTime): String = dateTime.format(shortTimeFormat)

    /** Format date as "Tue, Feb 6" */
    fun formatDate(dateTime: ZonedDateTime): String = dateTime.format(dateFormat)

    /** Get the timezone abbreviation like "MST", "GMT", "WITA" */
    fun getTimezoneAbbreviation(timezoneId: String): String {
        return try {
            val zone = ZoneId.of(timezoneId)
            val now = ZonedDateTime.now(zone)
            now.zone.getDisplayName(java.time.format.TextStyle.SHORT, Locale.ENGLISH)
        } catch (e: Exception) {
            "UTC"
        }
    }

    /** Get the current time in a specific timezone */
    fun getCurrentTime(timezoneId: String): ZonedDateTime {
        return try {
            ZonedDateTime.now(ZoneId.of(timezoneId))
        } catch (e: Exception) {
            // Fall back to UTC if timezone ID is invalid
            ZonedDateTime.now(ZoneId.of("UTC"))
        }
    }
}
