package com.example.clientmanager.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ResourceSelector(
    selectedResources: List<String>,
    onSelectionChange: (List<String>) -> Unit
) {
    var newResource by remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Ресурсы:")
        selectedResources.forEach { resource ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(resource, style = MaterialTheme.typography.bodyMedium)
                IconButton(onClick = {
                    onSelectionChange(selectedResources - resource)
                }) {
                    Icon(Icons.Default.Delete, contentDescription = "Удалить ресурс")
                }
            }
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            BasicTextField(
                value = newResource,
                onValueChange = { newResource = it },
                modifier = Modifier.weight(1f)
            )
            Button(
                onClick = {
                    if (newResource.isNotBlank()) {
                        onSelectionChange(selectedResources + newResource)
                        newResource = ""
                    }
                }
            ) {
                Text("Добавить")
            }
        }
    }
}
