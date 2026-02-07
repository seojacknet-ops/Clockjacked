package com.clockjacked.app.data.repository

import com.clockjacked.app.data.PreferencesManager
import com.clockjacked.app.data.model.ClockEntry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class ClockRepository(private val preferencesManager: PreferencesManager) {

    /** Observe the clock list reactively */
    fun getClocks(): Flow<List<ClockEntry>> = preferencesManager.getClocks()

    /** Add a clock and persist. Returns false if duplicate timezone. */
    suspend fun addClock(entry: ClockEntry): Boolean {
        val current = preferencesManager.getClocks().first().toMutableList()
        if (current.any { it.timezoneId == entry.timezoneId }) return false
        current.add(entry.copy(position = current.size))
        preferencesManager.saveClocks(current)
        return true
    }

    /** Remove a clock by ID and persist */
    suspend fun removeClock(id: String) {
        val updated = preferencesManager.getClocks().first()
            .filter { it.id != id }
            .mapIndexed { index, clock -> clock.copy(position = index) }
        preferencesManager.saveClocks(updated)
    }

    /** Save a reordered list */
    suspend fun saveClocks(clocks: List<ClockEntry>) {
        preferencesManager.saveClocks(
            clocks.mapIndexed { index, clock -> clock.copy(position = index) }
        )
    }

    /** Restore a clock at a specific position (for undo) */
    suspend fun restoreClock(entry: ClockEntry, position: Int) {
        val current = preferencesManager.getClocks().first().toMutableList()
        val insertAt = position.coerceIn(0, current.size)
        current.add(insertAt, entry)
        preferencesManager.saveClocks(
            current.mapIndexed { index, clock -> clock.copy(position = index) }
        )
    }
}
