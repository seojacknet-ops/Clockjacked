package com.clockjacked.app.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.clockjacked.app.data.model.ClockEntry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "clockjacked_prefs")

class PreferencesManager(private val context: Context) {

    private val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    companion object {
        private val CLOCKS_KEY = stringPreferencesKey("saved_clocks")
        private val FIRST_LAUNCH_KEY = stringPreferencesKey("first_launch_done")
        private val MUSIC_MUTED_KEY = booleanPreferencesKey("music_muted")
    }

    /** Get the saved clocks, or defaults on first launch */
    fun getClocks(): Flow<List<ClockEntry>> {
        return context.dataStore.data.map { preferences ->
            val clocksJson = preferences[CLOCKS_KEY]
            val firstLaunchDone = preferences[FIRST_LAUNCH_KEY]

            if (clocksJson != null) {
                try {
                    json.decodeFromString<List<ClockEntry>>(clocksJson)
                } catch (e: Exception) {
                    // Data corruption — reset to defaults
                    ClockEntry.DEFAULT_CLOCKS
                }
            } else if (firstLaunchDone == null) {
                // First launch — return defaults
                ClockEntry.DEFAULT_CLOCKS
            } else {
                // User deliberately removed all clocks
                emptyList()
            }
        }
    }

    /** Save the clock list to DataStore */
    suspend fun saveClocks(clocks: List<ClockEntry>) {
        context.dataStore.edit { preferences ->
            preferences[CLOCKS_KEY] = json.encodeToString(clocks)
            preferences[FIRST_LAUNCH_KEY] = "true"
        }
    }

    /** Get the music mute state (false = playing, true = muted) */
    fun getMusicMuted(): Flow<Boolean> {
        return context.dataStore.data.map { preferences ->
            preferences[MUSIC_MUTED_KEY] ?: false
        }
    }

    /** Save the music mute state */
    suspend fun saveMusicMuted(muted: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[MUSIC_MUTED_KEY] = muted
        }
    }
}
