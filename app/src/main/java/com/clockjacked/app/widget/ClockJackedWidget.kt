package com.clockjacked.app.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.currentState
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.width
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import com.clockjacked.app.MainActivity
import com.clockjacked.app.data.model.ClockEntry
import com.clockjacked.app.data.model.displayName
import com.clockjacked.app.util.TimeDiffCalculator
import com.clockjacked.app.util.TimeFormatter
import kotlinx.serialization.json.Json
import androidx.glance.appwidget.cornerRadius
import androidx.glance.color.ColorProviders
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import com.clockjacked.app.ui.theme.AccentPurple
import com.clockjacked.app.ui.theme.DarkBackground
import com.clockjacked.app.ui.theme.DarkSurface
import com.clockjacked.app.ui.theme.TextPrimary
import com.clockjacked.app.ui.theme.TextSecondary

class ClockJackedWidget : GlanceAppWidget() {

    companion object {
        val WIDGET_CLOCKS_KEY = stringPreferencesKey("widget_clocks")
        private val json = Json { ignoreUnknownKeys = true }
    }

    override val sizeMode = SizeMode.Exact

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            val prefs = currentState<androidx.glance.appwidget.GlanceAppWidgetManager>()
            GlanceTheme {
                WidgetContent()
            }
        }
    }
}

@Composable
private fun WidgetContent() {
    val state = currentState<androidx.datastore.preferences.core.Preferences>()
    val clocksJson = state[ClockJackedWidget.WIDGET_CLOCKS_KEY]

    val clocks: List<ClockEntry> = if (clocksJson != null) {
        try {
            Json.decodeFromString<List<ClockEntry>>(clocksJson)
        } catch (e: Exception) {
            ClockEntry.DEFAULT_CLOCKS.take(2)
        }
    } else {
        ClockEntry.DEFAULT_CLOCKS.take(2)
    }

    // Use Home Base timezone for diffs (fall back to device timezone)
    val homeBaseTimezoneId = clocks.firstOrNull { it.isHomeBase }?.timezoneId

    Box(
        modifier = GlanceModifier
            .fillMaxSize()
            .cornerRadius(16.dp)
            .background(DarkBackground)
            .clickable(actionStartActivity<MainActivity>())
            .padding(12.dp)
    ) {
        if (clocks.size <= 2) {
            // Horizontal layout for 1-2 clocks
            Row(
                modifier = GlanceModifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalAlignment = Alignment.CenterVertically
            ) {
                clocks.forEachIndexed { index, clock ->
                    WidgetClockItem(
                        clock = clock,
                        homeBaseTimezoneId = homeBaseTimezoneId,
                        modifier = GlanceModifier.defaultWeight()
                    )
                    if (index < clocks.size - 1) {
                        Spacer(modifier = GlanceModifier.width(8.dp))
                    }
                }
            }
        } else {
            // Grid layout for 3-4 clocks
            Column(
                modifier = GlanceModifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = GlanceModifier.fillMaxWidth().defaultWeight(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    WidgetClockItem(
                        clock = clocks[0],
                        homeBaseTimezoneId = homeBaseTimezoneId,
                        modifier = GlanceModifier.defaultWeight()
                    )
                    Spacer(modifier = GlanceModifier.width(8.dp))
                    WidgetClockItem(
                        clock = clocks[1],
                        homeBaseTimezoneId = homeBaseTimezoneId,
                        modifier = GlanceModifier.defaultWeight()
                    )
                }
                Spacer(modifier = GlanceModifier.height(4.dp))
                Row(
                    modifier = GlanceModifier.fillMaxWidth().defaultWeight(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    WidgetClockItem(
                        clock = clocks[2],
                        homeBaseTimezoneId = homeBaseTimezoneId,
                        modifier = GlanceModifier.defaultWeight()
                    )
                    if (clocks.size > 3) {
                        Spacer(modifier = GlanceModifier.width(8.dp))
                        WidgetClockItem(
                            clock = clocks[3],
                            homeBaseTimezoneId = homeBaseTimezoneId,
                            modifier = GlanceModifier.defaultWeight()
                        )
                    } else {
                        Spacer(modifier = GlanceModifier.defaultWeight())
                    }
                }
            }
        }
    }
}

@Composable
private fun WidgetClockItem(
    clock: ClockEntry,
    homeBaseTimezoneId: String?,
    modifier: GlanceModifier = GlanceModifier
) {
    val currentTime = TimeFormatter.getCurrentTime(clock.timezoneId)
    val formattedTime = TimeFormatter.formatTimeShort(currentTime)
    // Use Home Base timezone for diffs when available, otherwise fall back to device timezone
    val timeDiff = if (homeBaseTimezoneId != null) {
        TimeDiffCalculator.calculateTimeDifference(
            localTimezoneId = homeBaseTimezoneId,
            targetTimezoneId = clock.timezoneId
        )
    } else {
        TimeDiffCalculator.calculateTimeDifference(targetTimezoneId = clock.timezoneId)
    }

    Column(
        modifier = modifier
            .cornerRadius(12.dp)
            .background(DarkSurface)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "${clock.flagEmoji} ${clock.displayName()}",
            style = TextStyle(
                color = ColorProvider(TextSecondary),
                fontSize = 12.sp
            ),
            maxLines = 1
        )
        Spacer(modifier = GlanceModifier.height(2.dp))
        Text(
            text = formattedTime,
            style = TextStyle(
                color = ColorProvider(TextPrimary),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        )
        Text(
            text = if (clock.isHomeBase) "Home Base" else timeDiff.format(),
            style = TextStyle(
                color = ColorProvider(AccentPurple),
                fontSize = 10.sp
            )
        )
    }
}
