package com.clockjacked.app.data.model

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class ClockEntry(
    val id: String = UUID.randomUUID().toString(),
    val cityName: String,
    val timezoneId: String,
    val flagEmoji: String,
    val position: Int = 0,
    val isHomeBase: Boolean = false,
    val nickname: String? = null,
    val isCrew: Boolean = false
) {
    companion object {
        val DEFAULT_CLOCKS = listOf(
            ClockEntry(
                id = "default-slc",
                cityName = "Salt Lake City",
                timezoneId = "America/Denver",
                flagEmoji = "\uD83C\uDDFA\uD83C\uDDF8",
                position = 0,
                isHomeBase = true
            ),
            ClockEntry(
                id = "default-honolulu",
                cityName = "Honolulu",
                timezoneId = "Pacific/Honolulu",
                flagEmoji = "\uD83C\uDDFA\uD83C\uDDF8",
                position = 1
            ),
            ClockEntry(
                id = "default-liverpool",
                cityName = "Liverpool",
                timezoneId = "Europe/London",
                flagEmoji = "\uD83C\uDDEC\uD83C\uDDE7",
                position = 2
            ),
            ClockEntry(
                id = "default-bali",
                cityName = "Bali",
                timezoneId = "Asia/Makassar",
                flagEmoji = "\uD83C\uDDEE\uD83C\uDDE9",
                position = 3
            )
        )
    }
}

/** Display nickname if set, otherwise fall back to city name */
fun ClockEntry.displayName(): String = nickname?.takeIf { it.isNotBlank() } ?: cityName
