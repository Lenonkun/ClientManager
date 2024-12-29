package com.example.clientmanager.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.clientmanager.ui.theme.CustomTheme

data class Resource(
    val name: String,
    var totalCost: Double,
    var quantity: Int,
    var percentageUsage: Int?,
    var activeProcedures: MutableList<String>
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResourcesScreen(procedureTypes: List<String>) {
    val resources = remember { mutableStateListOf<Resource>() }
    var showDialog by remember { mutableStateOf(false) }
    var resourceToEdit by remember { mutableStateOf<Resource?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ресурсы") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    resourceToEdit = null
                    showDialog = true
                },
                containerColor = MaterialTheme.colorScheme.secondary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Добавить ресурс")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(CustomTheme.spacing.small)
            ) {
                items(resources.size) { index ->
                    ResourceItem(
                        resource = resources[index],
                        onEdit = {
                            resourceToEdit = resources[index]
                            showDialog = true
                        },
                        onDelete = { resources.removeAt(index) }
                    )
                }
            }
        }

        if (showDialog) {
            AddOrEditResourceDialog(
                procedureTypes = procedureTypes,
                resource = resourceToEdit,
                onSave = { resource ->
                    if (resourceToEdit != null) {
                        val index = resources.indexOf(resourceToEdit!!)
                        resources[index] = resource
                    } else {
                        resources.add(resource)
                    }
                    showDialog = false
                    resourceToEdit = null
                },
                onCancel = {
                    showDialog = false
                    resourceToEdit = null
                }
            )
        }
    }
}

@Composable
fun ResourceItem(resource: Resource, onEdit: () -> Unit, onDelete: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = "Название: ${resource.name}",
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = "Стоимость: ${resource.totalCost}₽, Количество: ${resource.quantity}",
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = "Активен для: ${resource.activeProcedures.joinToString(", ")}",
            style = MaterialTheme.typography.bodySmall
        )
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = onEdit) {
                Icon(Icons.Default.Edit, contentDescription = "Редактировать ресурс")
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Удалить ресурс")
            }
        }
    }
}

@Composable
fun AddOrEditResourceDialog(
    procedureTypes: List<String>,
    resource: Resource?,
    onSave: (Resource) -> Unit,
    onCancel: () -> Unit
) {
    var name by remember { mutableStateOf(resource?.name ?: "") }
    var totalCost by remember { mutableStateOf(resource?.totalCost?.toString() ?: "") }
    var quantity by remember { mutableStateOf(resource?.quantity?.toString() ?: "") }
    var percentageUsage by remember { mutableStateOf(resource?.percentageUsage?.toString() ?: "") }
    val activeProcedures = remember { mutableStateListOf(*resource?.activeProcedures?.toTypedArray() ?: arrayOf()) }

    AlertDialog(
        onDismissRequest = onCancel,
        title = { Text(text = if (resource == null) "Добавить ресурс" else "Редактировать ресурс") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    label = { Text("Название ресурса") }

                )
                TextField(
                    value = totalCost,
                    onValueChange = { totalCost = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Стоимость") }

                )
                TextField(
                    value = quantity,
                    onValueChange = { quantity = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Количество") }
                                    )
                TextField(
                    value = percentageUsage,
                    onValueChange = { percentageUsage = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Процент потребления (необязательно)") }

                )
                Text("Выберите виды процедур:")
                procedureTypes.forEach { type ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Checkbox(
                            checked = activeProcedures.contains(type),
                            onCheckedChange = {
                                if (it) activeProcedures.add(type) else activeProcedures.remove(type)
                            }
                        )
                        Text(type)
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                if (name.isNotBlank() && totalCost.toDoubleOrNull() != null && quantity.toIntOrNull() != null) {
                    onSave(
                        Resource(
                            name = name,
                            totalCost = totalCost.toDouble(),
                            quantity = quantity.toInt(),
                            percentageUsage = percentageUsage.toIntOrNull(),
                            activeProcedures = activeProcedures
                        )
                    )
                }
            }) {
                Text("Сохранить")
            }
        },
        dismissButton = {
            Button(onClick = onCancel) {
                Text("Отмена")
            }
        }
    )
}
