package com.clockjacked.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.clockjacked.app.audio.MusicManager
import com.clockjacked.app.data.PreferencesManager
import com.clockjacked.app.data.model.ClockEntry
import com.clockjacked.app.data.repository.ClockRepository
import com.clockjacked.app.util.EasterEggManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ClockViewModel(
    private val repository: ClockRepository,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    private val _clocks = MutableStateFlow<List<ClockEntry>>(emptyList())
    val clocks: StateFlow<List<ClockEntry>> = _clocks.asStateFlow()

    /** One-shot easter egg events for snackbar display */
    private val _easterEggEvent = MutableSharedFlow<String>(extraBufferCapacity = 1)
    val easterEggEvent: SharedFlow<String> = _easterEggEvent.asSharedFlow()

    /** Music mute state — true = muted, false = playing */
    private val _isMusicMuted = MutableStateFlow(false)
    val isMusicMuted: StateFlow<Boolean> = _isMusicMuted.asStateFlow()

    /** Home Base clock sorted to top, others by position */
    val sortedClocks: StateFlow<List<ClockEntry>> = _clocks.map { list ->
        val homeBase = list.firstOrNull { it.isHomeBase }
        val others = list.filter { !it.isHomeBase }.sortedBy { it.position }
        listOfNotNull(homeBase) + others
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    /** The current Home Base clock, if any */
    val homeBaseClock: StateFlow<ClockEntry?> = _clocks.map { list ->
        list.firstOrNull { it.isHomeBase }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    /** Clocks marked as crew members */
    val crewClocks: StateFlow<List<ClockEntry>> = _clocks.map { list ->
        list.filter { it.isCrew }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    /** Emits every second so the UI recomposes with fresh times */
    val currentTick: StateFlow<Long> = flow {
        while (true) {
            emit(System.currentTimeMillis())
            delay(1000L)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = System.currentTimeMillis()
    )

    init {
        viewModelScope.launch {
            repository.getClocks().collect { clocks ->
                // Upgrade path: if no clock is Home Base, set the first one
                val needsHomeBase = clocks.isNotEmpty() && clocks.none { it.isHomeBase }
                if (needsHomeBase) {
                    val fixed = clocks.mapIndexed { index, clock ->
                        if (index == 0) clock.copy(isHomeBase = true) else clock
                    }
                    _clocks.value = fixed
                    repository.saveClocks(fixed)
                } else {
                    _clocks.value = clocks
                }
            }
        }
        // Restore mute state from DataStore and sync to MusicManager
        viewModelScope.launch {
            preferencesManager.getMusicMuted().collect { muted ->
                _isMusicMuted.value = muted
                MusicManager.setMuted(muted)
            }
        }
    }

    /** Returns true if the clock was added (not a duplicate) */
    fun addClock(entry: ClockEntry): Boolean {
        if (_clocks.value.any { it.timezoneId == entry.timezoneId }) return false
        val updated = _clocks.value.toMutableList()
        // If this is the first clock, make it Home Base
        val isFirst = updated.isEmpty()
        updated.add(entry.copy(position = updated.size, isHomeBase = isFirst))
        _clocks.value = updated
        viewModelScope.launch { repository.saveClocks(updated) }

        // Easter eggs on add
        EasterEggManager.onAddClockLateNight()?.let { _easterEggEvent.tryEmit(it) }
        EasterEggManager.onTooManyClocks(updated.size)?.let { _easterEggEvent.tryEmit(it) }
        EasterEggManager.onAllSameTimezone(updated.map { it.timezoneId })?.let { _easterEggEvent.tryEmit(it) }

        return true
    }

    fun removeClock(id: String) {
        val removedClock = _clocks.value.firstOrNull { it.id == id } ?: return
        val remaining = _clocks.value
            .filter { it.id != id }
            .mapIndexed { index, clock -> clock.copy(position = index) }

        // If we removed the Home Base, promote the first remaining clock
        val updated = if (removedClock.isHomeBase && remaining.isNotEmpty()) {
            remaining.mapIndexed { index, clock ->
                if (index == 0) clock.copy(isHomeBase = true) else clock
            }
        } else {
            remaining
        }

        _clocks.value = updated
        viewModelScope.launch { repository.saveClocks(updated) }

        // Easter egg on delete last clock
        EasterEggManager.onDeleteLastClock(updated.size)?.let { _easterEggEvent.tryEmit(it) }
    }

    fun reorderClocks(from: Int, to: Int) {
        val current = _clocks.value.toMutableList()
        if (from !in current.indices || to !in current.indices) return
        val item = current.removeAt(from)
        current.add(to, item)
        val updated = current.mapIndexed { index, clock -> clock.copy(position = index) }
        _clocks.value = updated
        viewModelScope.launch { repository.saveClocks(updated) }
    }

    /** Re-add a previously removed clock (for undo) */
    fun restoreClock(entry: ClockEntry, position: Int) {
        val current = _clocks.value.toMutableList()
        val insertAt = position.coerceIn(0, current.size)
        current.add(insertAt, entry)
        val updated = current.mapIndexed { index, clock -> clock.copy(position = index) }
        _clocks.value = updated
        viewModelScope.launch { repository.saveClocks(updated) }
    }

    /** Update a clock's custom nickname */
    fun updateNickname(clockId: String, nickname: String?) {
        val updated = _clocks.value.map { clock ->
            if (clock.id == clockId) clock.copy(nickname = nickname?.takeIf { it.isNotBlank() }) else clock
        }
        _clocks.value = updated
        viewModelScope.launch { repository.saveClocks(updated) }
    }

    /** Toggle a clock's crew membership */
    fun toggleCrew(clockId: String) {
        val updated = _clocks.value.map { clock ->
            if (clock.id == clockId) clock.copy(isCrew = !clock.isCrew) else clock
        }
        _clocks.value = updated
        viewModelScope.launch { repository.saveClocks(updated) }
    }

    /** Designate a clock as the Home Base — all diffs calculated relative to it */
    fun setHomeBase(clockId: String) {
        val updated = _clocks.value.map { clock ->
            clock.copy(isHomeBase = clock.id == clockId)
        }
        _clocks.value = updated
        viewModelScope.launch { repository.saveClocks(updated) }
    }

    /** Toggle background music mute state */
    fun toggleMusicMute() {
        val newMuted = MusicManager.toggleMute()
        _isMusicMuted.value = newMuted
        viewModelScope.launch { preferencesManager.saveMusicMuted(newMuted) }
    }

    class Factory(
        private val repository: ClockRepository,
        private val preferencesManager: PreferencesManager
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ClockViewModel(repository, preferencesManager) as T
        }
    }
}
