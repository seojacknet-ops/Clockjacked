package com.clockjacked.app.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.clockjacked.app.ui.theme.DayIndicator
import com.clockjacked.app.ui.theme.NightIndicator
import java.time.ZoneId
import java.time.ZonedDateTime

enum class TimeOfDay {
    DAY,    // 6:00 - 17:59
    NIGHT   // 18:00 - 5:59
}

/** Determine if it's day or night in the given timezone */
fun getTimeOfDay(timezoneId: String): TimeOfDay {
    return try {
        val hour = ZonedDateTime.now(ZoneId.of(timezoneId)).hour
        if (hour in 6..17) TimeOfDay.DAY else TimeOfDay.NIGHT
    } catch (e: Exception) {
        TimeOfDay.DAY
    }
}

@Composable
fun DayNightIndicator(
    timezoneId: String,
    modifier: Modifier = Modifier
) {
    val timeOfDay = getTimeOfDay(timezoneId)

    when (timeOfDay) {
        TimeOfDay.DAY -> Icon(
            imageVector = Icons.Filled.WbSunny,
            contentDescription = "Daytime",
            tint = DayIndicator,
            modifier = modifier
        )
        TimeOfDay.NIGHT -> Icon(
            imageVector = Icons.Filled.DarkMode,
            contentDescription = "Nighttime",
            tint = NightIndicator,
            modifier = modifier
        )
    }
}
