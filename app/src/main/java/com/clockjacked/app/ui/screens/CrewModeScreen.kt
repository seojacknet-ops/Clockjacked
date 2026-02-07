package com.clockjacked.app.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.clockjacked.app.data.model.ClockEntry
import com.clockjacked.app.data.model.displayName
import com.clockjacked.app.ui.theme.AccentPurple
import com.clockjacked.app.ui.theme.CallGreen
import com.clockjacked.app.util.CrewMember
import com.clockjacked.app.util.CrewOverlapCalculator
import com.clockjacked.app.viewmodel.ClockViewModel
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrewModeScreen(
    onNavigateBack: () -> Unit,
    viewModel: ClockViewModel
) {
    val crewClocks by viewModel.crewClocks.collectAsStateWithLifecycle()
    val textMeasurer = rememberTextMeasurer()

    val crewMembers = remember(crewClocks) {
        crewClocks.map { CrewMember(entry = it) }
    }

    val overlap = remember(crewClocks) {
        CrewOverlapCalculator.findOverlap(crewMembers)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Crew Mode") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            if (crewClocks.size < 2) {
                // Not enough crew members
                Text(
                    text = "Mark 2+ clocks as crew members to see overlap",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 64.dp)
                )
                Text(
                    text = "Long-press a clock on the dashboard, then tap \"Add to Crew\"",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                // Crew members list
                crewMembers.forEach { member ->
                    CrewMemberRow(member)
                    Spacer(modifier = Modifier.height(4.dp))
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Timeline visualization
                val barColors = listOf(
                    AccentPurple,
                    Color(0xFF00BCD4),
                    Color(0xFFFF9800),
                    Color(0xFFE91E63),
                    Color(0xFF4CAF50),
                    Color(0xFF9C27B0)
                )
                val surfaceVariant = MaterialTheme.colorScheme.surfaceVariant
                val onSurfaceVariant = MaterialTheme.colorScheme.onSurfaceVariant
                val textStyle = MaterialTheme.typography.labelSmall.copy(fontSize = 9.sp)

                Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height((60 + crewMembers.size * 40 + 30).dp)
                        .horizontalScroll(rememberScrollState())
                        .width(600.dp)
                ) {
                    val totalWidth = size.width
                    val topOffset = 30f
                    val barHeight = 30f
                    val barSpacing = 40f

                    // Draw hour markers
                    for (h in 0..24) {
                        val x = (h / 24f) * totalWidth
                        drawLine(
                            color = onSurfaceVariant.copy(alpha = 0.2f),
                            start = Offset(x, topOffset),
                            end = Offset(x, topOffset + crewMembers.size * barSpacing + 10),
                            strokeWidth = 1f
                        )
                        if (h < 24 && h % 3 == 0) {
                            val label = if (h == 0) "12a" else if (h < 12) "${h}a" else if (h == 12) "12p" else "${h - 12}p"
                            drawText(
                                textMeasurer = textMeasurer,
                                text = label,
                                topLeft = Offset(x + 2, 0f),
                                style = textStyle.copy(color = onSurfaceVariant.copy(alpha = 0.5f))
                            )
                        }
                    }

                    // Draw awake bars for each crew member
                    crewMembers.forEachIndexed { index, member ->
                        val y = topOffset + index * barSpacing
                        val color = barColors[index % barColors.size]

                        // Background bar
                        drawRect(
                            color = surfaceVariant,
                            topLeft = Offset(0f, y),
                            size = Size(totalWidth, barHeight)
                        )

                        // Awake window in UTC
                        val zone = ZoneId.of(member.entry.timezoneId)
                        val now = ZonedDateTime.now(zone)
                        val startLocal = now.withHour(member.awakeStart.hour).withMinute(member.awakeStart.minute)
                        val endLocal = now.withHour(member.awakeEnd.hour).withMinute(member.awakeEnd.minute)
                        val startUtc = startLocal.withZoneSameInstant(ZoneId.of("UTC"))
                        val endUtc = endLocal.withZoneSameInstant(ZoneId.of("UTC"))

                        val startFrac = (startUtc.hour * 60 + startUtc.minute) / 1440f
                        val endFrac = (endUtc.hour * 60 + endUtc.minute) / 1440f

                        if (startFrac <= endFrac) {
                            drawRect(
                                color = color.copy(alpha = 0.6f),
                                topLeft = Offset(startFrac * totalWidth, y),
                                size = Size((endFrac - startFrac) * totalWidth, barHeight)
                            )
                        } else {
                            // Wraps around midnight
                            drawRect(
                                color = color.copy(alpha = 0.6f),
                                topLeft = Offset(startFrac * totalWidth, y),
                                size = Size((1f - startFrac) * totalWidth, barHeight)
                            )
                            drawRect(
                                color = color.copy(alpha = 0.6f),
                                topLeft = Offset(0f, y),
                                size = Size(endFrac * totalWidth, barHeight)
                            )
                        }
                    }

                    // Draw overlap zone
                    if (overlap != null) {
                        val startFrac = overlap.startUtcMinute / 1440f
                        val endFrac = overlap.endUtcMinute / 1440f
                        val overlapY = topOffset
                        val overlapHeight = crewMembers.size * barSpacing

                        if (startFrac <= endFrac) {
                            drawRect(
                                color = CallGreen.copy(alpha = 0.25f),
                                topLeft = Offset(startFrac * totalWidth, overlapY),
                                size = Size((endFrac - startFrac) * totalWidth, overlapHeight)
                            )
                        } else {
                            drawRect(
                                color = CallGreen.copy(alpha = 0.25f),
                                topLeft = Offset(startFrac * totalWidth, overlapY),
                                size = Size((1f - startFrac) * totalWidth, overlapHeight)
                            )
                            drawRect(
                                color = CallGreen.copy(alpha = 0.25f),
                                topLeft = Offset(0f, overlapY),
                                size = Size(endFrac * totalWidth, overlapHeight)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Overlap result card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        if (overlap != null && overlap.durationMinutes > 0) {
                            val hours = overlap.durationMinutes / 60
                            val mins = overlap.durationMinutes % 60
                            val durationText = if (mins == 0) "${hours}h" else "${hours}h ${mins}m"
                            Text(
                                text = "Best window for a group call ($durationText):",
                                style = MaterialTheme.typography.titleSmall,
                                color = CallGreen
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = overlap.formatForCrew(crewMembers),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        } else {
                            Text(
                                text = "No good overlap today. Someone's losing sleep \uD83D\uDE05",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CrewMemberRow(member: CrewMember) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = member.entry.flagEmoji,
            fontSize = 20.sp
        )
        Text(
            text = member.entry.displayName(),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = "(${member.awakeStart}–${member.awakeEnd})",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
