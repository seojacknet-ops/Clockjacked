package com.clockjacked.app.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.GroupAdd
import androidx.compose.material.icons.filled.GroupRemove
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.clockjacked.app.data.model.ClockEntry
import com.clockjacked.app.data.model.displayName
import com.clockjacked.app.ui.theme.DeleteRed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClockActionSheet(
    clock: ClockEntry,
    isHomeBase: Boolean,
    onDismiss: () -> Unit,
    onCopyTime: () -> Unit,
    onShareTime: () -> Unit,
    onSetHomeBase: () -> Unit,
    onEditNickname: () -> Unit,
    isCrew: Boolean,
    onToggleCrew: () -> Unit,
    onDelete: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Text(
            text = clock.displayName(),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        val itemColors = ListItemDefaults.colors(
            containerColor = MaterialTheme.colorScheme.surface
        )

        ListItem(
            headlineContent = { Text("Copy time") },
            leadingContent = {
                Icon(Icons.Filled.ContentCopy, contentDescription = null)
            },
            colors = itemColors,
            modifier = Modifier.clickable {
                onCopyTime()
                onDismiss()
            }
        )

        ListItem(
            headlineContent = { Text("Share time") },
            leadingContent = {
                Icon(Icons.Filled.Share, contentDescription = null)
            },
            colors = itemColors,
            modifier = Modifier.clickable {
                onShareTime()
                onDismiss()
            }
        )

        if (!isHomeBase) {
            ListItem(
                headlineContent = { Text("Set as Home Base") },
                leadingContent = {
                    Icon(Icons.Filled.Home, contentDescription = null)
                },
                colors = itemColors,
                modifier = Modifier.clickable {
                    onSetHomeBase()
                    onDismiss()
                }
            )
        }

        ListItem(
            headlineContent = { Text("Edit nickname") },
            leadingContent = {
                Icon(Icons.Filled.Edit, contentDescription = null)
            },
            colors = itemColors,
            modifier = Modifier.clickable {
                onEditNickname()
                onDismiss()
            }
        )

        ListItem(
            headlineContent = { Text(if (isCrew) "Remove from Crew" else "Add to Crew") },
            leadingContent = {
                Icon(
                    if (isCrew) Icons.Filled.GroupRemove else Icons.Filled.GroupAdd,
                    contentDescription = null
                )
            },
            colors = itemColors,
            modifier = Modifier.clickable {
                onToggleCrew()
                onDismiss()
            }
        )

        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))

        ListItem(
            headlineContent = {
                Text(
                    text = if (isHomeBase) "Delete (change Home Base first)" else "Delete",
                    color = if (isHomeBase) MaterialTheme.colorScheme.onSurfaceVariant else DeleteRed
                )
            },
            leadingContent = {
                Icon(
                    Icons.Filled.Delete,
                    contentDescription = null,
                    tint = if (isHomeBase) MaterialTheme.colorScheme.onSurfaceVariant else DeleteRed
                )
            },
            colors = itemColors,
            modifier = if (isHomeBase) Modifier else Modifier.clickable {
                onDelete()
                onDismiss()
            }
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}
