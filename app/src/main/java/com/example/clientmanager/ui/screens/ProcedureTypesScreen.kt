package com.example.clientmanager.ui.screens

import androidx.compose.foundation.border
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.clientmanager.viewmodel.ProcedureTypesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProcedureTypesScreen(viewModel: ProcedureTypesViewModel = hiltViewModel()) {
    val procedureTypes by viewModel.procedureTypes.collectAsState(initial = emptyList())
    var newProcedureName by remember { mutableStateOf("") }
    var editMode by remember { mutableStateOf(false) }
    var editIndex by remember { mutableStateOf(-1) }

    Scaffold(
        // Остальная разметка
    ) { padding ->
        // Работа с ViewModel вместо локального списка
        if (editMode || editIndex != -1) {
            AddOrEditProcedureType(
                initialName = if (editMode) procedureTypes[editIndex] else "",
                onSave = { name ->
                    if (editMode && editIndex != -1) {
                        viewModel.updateProcedureType(editIndex, name)
                    } else {
                        viewModel.addProcedureType(name)
                    }
                    editMode = false
                    editIndex = -1
                    newProcedureName = ""
                },
                onCancel = {
                    editMode = false
                    editIndex = -1
                    newProcedureName = ""
                }
            )
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(procedureTypes.size) { index ->
                    ProcedureTypeItem(
                        name = procedureTypes[index],
                        onEdit = {
                            editMode = true
                            editIndex = index
                            newProcedureName = procedureTypes[index]
                        },
                        onDelete = { viewModel.deleteProcedureType(procedureTypes[index]) }
                    )
                }
            }
        }
    }
}

@Composable
fun AddOrEditProcedureType(
    initialName: String,
    onSave: (String) -> Unit,
    onCancel: () -> Unit
) {
    var name by remember { mutableStateOf(initialName) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text("Введите название вида процедуры:", style = MaterialTheme.typography.bodyLarge)
        BasicTextField(
            value = name,
            onValueChange = { name = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .border(1.dp, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.medium)
                .padding(8.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = { onCancel() }) {
                Text("Отменить")
            }
            Button(onClick = { if (name.isNotBlank()) onSave(name) }) {
                Text("Сохранить")
            }
        }
    }
}

@Composable
fun ProcedureTypeItem(name: String, onEdit: () -> Unit, onDelete: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(name, style = MaterialTheme.typography.bodyLarge)
        Row {
            IconButton(onClick = { onEdit() }) {
                Icon(Icons.Default.Edit, contentDescription = "Редактировать")
            }
            IconButton(onClick = { onDelete() }) {
                Icon(Icons.Default.Delete, contentDescription = "Удалить")
            }
        }
    }
}
