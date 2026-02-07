package com.clockjacked.app

import android.app.Application
import com.clockjacked.app.data.PreferencesManager
import com.clockjacked.app.data.repository.ClockRepository

class ClockJackedApp : Application() {

    lateinit var preferencesManager: PreferencesManager
        private set

    lateinit var clockRepository: ClockRepository
        private set

    override fun onCreate() {
        super.onCreate()
        preferencesManager = PreferencesManager(this)
        clockRepository = ClockRepository(preferencesManager)
    }
}
