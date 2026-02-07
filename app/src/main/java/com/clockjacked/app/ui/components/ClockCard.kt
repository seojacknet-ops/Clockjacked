package com.clockjacked.app.ui.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.clockjacked.app.data.model.ClockEntry
import com.clockjacked.app.data.model.displayName
import com.clockjacked.app.ui.theme.AccentPurple
import com.clockjacked.app.ui.theme.CallAmber
import com.clockjacked.app.ui.theme.CallGreen
import com.clockjacked.app.ui.theme.CallRed
import com.clockjacked.app.util.CallStatus
import com.clockjacked.app.util.TimeDiffCalculator
import com.clockjacked.app.util.TimeFormatter
import com.clockjacked.app.util.getCallStatus
import com.clockjacked.app.util.getTimeTint
import com.clockjacked.app.util.getVibeLabel
import java.time.ZoneId

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ClockCard(
    entry: ClockEntry,
    currentTick: Long,
    homeBaseTimezoneId: String? = null,
    onLongPress: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val currentTime = remember(currentTick, entry.timezoneId) {
        TimeFormatter.getCurrentTime(entry.timezoneId)
    }
    val formattedTime = remember(currentTick, entry.timezoneId) {
        TimeFormatter.formatTime(currentTime)
    }
    val formattedDate = remember(currentTick, entry.timezoneId) {
        TimeFormatter.formatDate(currentTime)
    }

    // Diff relative to Home Base timezone (or device timezone if no Home Base)
    val timeDiff = remember(currentTick, entry.timezoneId, homeBaseTimezoneId) {
        TimeDiffCalculator.calculateTimeDifference(
            localTimezoneId = homeBaseTimezoneId ?: ZoneId.systemDefault().id,
            targetTimezoneId = entry.timezoneId
        )
    }

    // Traffic light — not shown for Home Base clock
    val callStatus = if (!entry.isHomeBase) {
        remember(currentTick, entry.timezoneId) { getCallStatus(entry.timezoneId) }
    } else null

    val vibeLabel = remember(currentTick, entry.timezoneId) { getVibeLabel(entry.timezoneId) }
    val timeTint = remember(currentTick, entry.timezoneId) { getTimeTint(entry.timezoneId) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .then(
                if (onLongPress != null) {
                    Modifier.combinedClickable(
                        onClick = {},
                        onLongClick = onLongPress
                    )
                } else Modifier
            ),
        shape = RoundedCornerShape(16.dp),
        border = if (entry.isHomeBase) {
            BorderStroke(2.dp, AccentPurple.copy(alpha = 0.3f))
        } else null,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Box {
            // Breathing time-of-day tint overlay
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clip(RoundedCornerShape(16.dp))
                    .background(timeTint)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left: flag, name, date, vibe
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = entry.flagEmoji,
                        fontSize = 28.sp
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            if (entry.isHomeBase) {
                                Text("\uD83C\uDFE0 ", fontSize = 14.sp)
                            }
                            Text(
                                text = entry.displayName(),
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                        // Show original city name when a nickname is set
                        if (!entry.nickname.isNullOrBlank() && entry.nickname != entry.cityName) {
                            Text(
                                text = entry.cityName,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = formattedDate,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            // Home Base is always "Same time" — skip it
                            if (!entry.isHomeBase) {
                                Text(
                                    text = timeDiff.format(),
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Medium,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                        // Vibe label
                        Text(
                            text = vibeLabel,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                        )
                    }
                }

                // Right: time + day/night + traffic light
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = formattedTime,
                        style = MaterialTheme.typography.displayLarge.copy(
                            fontSize = if (entry.isHomeBase) 32.sp else 28.sp
                        ),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        DayNightIndicator(
                            timezoneId = entry.timezoneId,
                            modifier = Modifier.size(20.dp)
                        )
                        if (callStatus != null) {
                            TrafficLightDot(callStatus)
                        }
                    }
                }
            }
        }
    }
}

/** Colored dot indicating call status — pulses when GREEN */
@Composable
private fun TrafficLightDot(status: CallStatus) {
    val color = when (status) {
        CallStatus.GREEN -> CallGreen
        CallStatus.AMBER -> CallAmber
        CallStatus.RED -> CallRed
    }

    val dotAlpha = if (status == CallStatus.GREEN) {
        val infiniteTransition = rememberInfiniteTransition(label = "pulse")
        val pulse by infiniteTransition.animateFloat(
            initialValue = 0.6f,
            targetValue = 1f,
            animationSpec = infiniteRepeatable(
                animation = tween(1000),
                repeatMode = RepeatMode.Reverse
            ),
            label = "pulse-alpha"
        )
        pulse
    } else {
        1f
    }

    Box(
        modifier = Modifier
            .size(12.dp)
            .alpha(dotAlpha)
            .clip(CircleShape)
            .background(color)
    )
}
