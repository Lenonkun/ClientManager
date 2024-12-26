package com.example.clientmanager.ui.sort

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.room.parser.Section

@Composable
fun SortDialog(
    currentSortOrder: SortOrder,
    onSortSelected: (SortOrder) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Section.Text("Сортировка") },
        text = {
            Column {
                TextButton(onClick = { onSortSelected(SortOrder.BY_DATE_ASC) }) {
                    Text("По дате (возрастание)")
                }
                TextButton(onClick = { onSortSelected(SortOrder.BY_DATE_DESC) }) {
                    Text("По дате (убывание)")
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Закрыть")
            }
        }
    )
}
