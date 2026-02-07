package com.clockjacked.app.util

import java.time.ZoneId
import java.time.ZonedDateTime

/**
 * Manages one-per-session easter egg messages. Each egg shows at most once
 * per app process lifetime to avoid spamming the user.
 */
object EasterEggManager {

    private val shownEggs = mutableSetOf<String>()

    /** Returns the message if not yet shown this session, null otherwise */
    private fun showOnce(key: String, message: String): String? {
        return if (shownEggs.add(key)) message else null
    }

    /** Search screen: no results found */
    fun onNoSearchResults(query: String): String? =
        showOnce("no_results", "That's not a real place, mate. \uD83E\uDD37")

    /** Dashboard: user added a 15th+ clock */
    fun onTooManyClocks(count: Int): String? {
        if (count < 15) return null
        return showOnce("too_many", "Slow down, you don't know people in that many time zones. \uD83D\uDE04")
    }

    /** Dashboard: all clocks are in the same timezone */
    fun onAllSameTimezone(timezoneIds: List<String>): String? {
        if (timezoneIds.size < 2) return null
        val allSame = timezoneIds.distinct().size == 1
        if (!allSame) return null
        return showOnce("all_same", "Everyone's in the same zone! Go outside and see them. \uD83E\uDD1D")
    }

    /** Dashboard: adding a clock at 3 AM local time */
    fun onAddClockLateNight(): String? {
        val hour = ZonedDateTime.now(ZoneId.systemDefault()).hour
        if (hour !in 1..4) return null
        return showOnce("late_night", "Adding clocks at 3 AM? You good? \uD83D\uDE05")
    }

    /** Dashboard: deleting the last clock */
    fun onDeleteLastClock(remainingCount: Int): String? {
        if (remainingCount > 0) return null
        return showOnce("last_clock", "Are you sure? It's lonely without any clocks. \uD83E\uDD7A")
    }
}
