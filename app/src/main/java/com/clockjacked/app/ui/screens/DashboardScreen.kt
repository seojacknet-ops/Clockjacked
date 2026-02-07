package com.clockjacked.app.ui.screens

import android.content.Intent
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.automirrored.filled.VolumeOff
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.clockjacked.app.data.model.ClockEntry
import com.clockjacked.app.data.model.displayName
import com.clockjacked.app.ui.components.ClockActionSheet
import com.clockjacked.app.ui.components.ClockCard
import com.clockjacked.app.ui.components.EmptyState
import com.clockjacked.app.ui.theme.DeleteRed
import com.clockjacked.app.util.ShareFormatter
import com.clockjacked.app.viewmodel.ClockViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun DashboardScreen(
    onNavigateToAddClock: () -> Unit = {},
    onNavigateToAbout: () -> Unit = {},
    onNavigateToCrewMode: () -> Unit = {},
    isMusicMuted: Boolean = false,
    onToggleMute: () -> Unit = {},
    viewModel: ClockViewModel
) {
    val clocks by viewModel.sortedClocks.collectAsStateWithLifecycle()
    val homeBaseClock by viewModel.homeBaseClock.collectAsStateWithLifecycle()
    val currentTick by viewModel.currentTick.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val haptic = LocalHapticFeedback.current
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    val homeBaseTimezoneId = homeBaseClock?.timezoneId

    // Collect easter egg one-shot events
    LaunchedEffect(Unit) {
        viewModel.easterEggEvent.collect { message ->
            snackbarHostState.showSnackbar(
                message = message,
                duration = SnackbarDuration.Short
            )
        }
    }

    // Bottom sheet state
    var selectedClock by remember { mutableStateOf<ClockEntry?>(null) }
    // Nickname edit dialog state
    var editingNicknameClock by remember { mutableStateOf<ClockEntry?>(null) }
    var nicknameText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "ClockJacked",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                actions = {
                    IconButton(onClick = onToggleMute) {
                        Icon(
                            imageVector = if (isMusicMuted) Icons.AutoMirrored.Filled.VolumeOff
                                          else Icons.AutoMirrored.Filled.VolumeUp,
                            contentDescription = if (isMusicMuted) "Unmute music" else "Mute music",
                            tint = if (isMusicMuted)
                                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                            else
                                MaterialTheme.colorScheme.primary
                        )
                    }
                    IconButton(onClick = onNavigateToCrewMode) {
                        Icon(
                            imageVector = Icons.Filled.Groups,
                            contentDescription = "Crew Mode",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    IconButton(onClick = onNavigateToAbout) {
                        Icon(
                            imageVector = Icons.Filled.Info,
                            contentDescription = "About",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToAddClock,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add Clock"
                )
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        if (clocks.isEmpty()) {
            EmptyState(
                onAddClock = onNavigateToAddClock,
                modifier = Modifier.padding(innerPadding)
            )
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(
                    items = clocks,
                    key = { it.id }
                ) { clock ->
                    if (clock.isHomeBase) {
                        // Home Base card is not swipeable to prevent accidental deletion
                        ClockCard(
                            entry = clock,
                            currentTick = currentTick,
                            homeBaseTimezoneId = homeBaseTimezoneId,
                            onLongPress = {
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                selectedClock = clock
                            },
                            modifier = Modifier.animateItem()
                        )
                    } else {
                        SwipeableClockItem(
                            clock = clock,
                            currentTick = currentTick,
                            homeBaseTimezoneId = homeBaseTimezoneId,
                            onLongPress = {
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                selectedClock = clock
                            },
                            onDelete = { deletedClock ->
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                val position = clocks.indexOf(deletedClock)
                                viewModel.removeClock(deletedClock.id)
                                scope.launch {
                                    val result = snackbarHostState.showSnackbar(
                                        message = "Removed ${deletedClock.displayName()}",
                                        actionLabel = "Undo",
                                        duration = SnackbarDuration.Short
                                    )
                                    if (result == SnackbarResult.ActionPerformed) {
                                        viewModel.restoreClock(deletedClock, position)
                                    }
                                }
                            },
                            modifier = Modifier.animateItem()
                        )
                    }
                }
            }
        }
    }

    // Bottom sheet for clock actions
    selectedClock?.let { clock ->
        ClockActionSheet(
            clock = clock,
            isHomeBase = clock.isHomeBase,
            onDismiss = { selectedClock = null },
            onCopyTime = {
                val text = ShareFormatter.formatCopyText(clock, homeBaseTimezoneId)
                clipboardManager.setText(AnnotatedString(text))
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = "Copied!",
                        duration = SnackbarDuration.Short
                    )
                }
            },
            onShareTime = {
                val text = ShareFormatter.formatShareText(clock, homeBaseTimezoneId)
                val sendIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, text)
                    type = "text/plain"
                }
                context.startActivity(Intent.createChooser(sendIntent, "Share time"))
            },
            onSetHomeBase = {
                viewModel.setHomeBase(clock.id)
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = "${clock.displayName()} is now Home Base",
                        duration = SnackbarDuration.Short
                    )
                }
            },
            onEditNickname = {
                nicknameText = clock.nickname ?: ""
                editingNicknameClock = clock
            },
            isCrew = clock.isCrew,
            onToggleCrew = {
                viewModel.toggleCrew(clock.id)
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = if (clock.isCrew) "${clock.displayName()} removed from Crew"
                                  else "${clock.displayName()} added to Crew",
                        duration = SnackbarDuration.Short
                    )
                }
            },
            onDelete = {
                val position = clocks.indexOf(clock)
                viewModel.removeClock(clock.id)
                scope.launch {
                    val result = snackbarHostState.showSnackbar(
                        message = "Removed ${clock.displayName()}",
                        actionLabel = "Undo",
                        duration = SnackbarDuration.Short
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.restoreClock(clock, position)
                    }
                }
            }
        )
    }
    // Nickname edit dialog
    editingNicknameClock?.let { clock ->
        AlertDialog(
            onDismissRequest = { editingNicknameClock = null },
            title = { Text("Edit nickname") },
            text = {
                OutlinedTextField(
                    value = nicknameText,
                    onValueChange = { nicknameText = it },
                    label = { Text("Nickname") },
                    placeholder = { Text(clock.cityName) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.updateNickname(clock.id, nicknameText)
                    editingNicknameClock = null
                }) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(onClick = { editingNicknameClock = null }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun SwipeableClockItem(
    clock: ClockEntry,
    currentTick: Long,
    homeBaseTimezoneId: String?,
    onLongPress: () -> Unit,
    onDelete: (ClockEntry) -> Unit,
    modifier: Modifier = Modifier
) {
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            if (value == SwipeToDismissBoxValue.EndToStart) {
                onDelete(clock)
                true
            } else {
                false
            }
        }
    )

    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = {
            val color by animateColorAsState(
                targetValue = when (dismissState.targetValue) {
                    SwipeToDismissBoxValue.EndToStart -> DeleteRed
                    else -> Color.Transparent
                },
                label = "swipe-bg"
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color)
                    .padding(end = 24.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                if (dismissState.targetValue == SwipeToDismissBoxValue.EndToStart) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Delete",
                        tint = Color.White
                    )
                }
            }
        },
        enableDismissFromStartToEnd = false,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier.combinedClickable(
                onClick = {},
                onLongClick = onLongPress
            )
        ) {
            ClockCard(
                entry = clock,
                currentTick = currentTick,
                homeBaseTimezoneId = homeBaseTimezoneId
            )
        }
    }
}
