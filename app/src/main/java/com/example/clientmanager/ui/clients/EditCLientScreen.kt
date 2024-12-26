package com.example.clientmanager.ui.clients

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditClientScreen(
    navController: NavController?, // Теперь NavController может быть null
    clientId: Int,
    initialName: String,
    clientsViewModel: ClientsViewModel = viewModel(),
    onSave: (String) -> Unit
) {
    var clientName by remember { mutableStateOf(initialName) }
    var isSaving by remember { mutableStateOf(false) }
    var showError by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Редактирование клиента") },
                navigationIcon = {
                    IconButton(onClick = { navController?.navigateUp() ?: Unit }) { // Проверка на null
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Редактировать имя клиента",
                    style = MaterialTheme.typography.headlineSmall
                )
                OutlinedTextField(
                    value = clientName,
                    onValueChange = { clientName = it },
                    label = { Text("Имя клиента") },
                    modifier = Modifier.fillMaxWidth()
                )
                if (showError) {
                    Text(
                        text = "Ошибка: Имя не может быть пустым!",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Button(
                    onClick = {
                        if (clientName.isBlank()) {
                            showError = true
                        } else {
                            showError = false
                            isSaving = true
                            clientsViewModel.updateClientName(clientId, clientName)
                            navController?.navigateUp() // Безопасный вызов
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isSaving
                ) {
                    Text(if (isSaving) "Сохранение..." else "Сохранить")
                }
            }
        }
    )
}

@Preview
@Composable
fun EditClientScreenPreview() {
    EditClientScreen(
        navController = null, // Для предпросмотра NavController не нужен
        clientId = 1,
        initialName = "Иван Иванов",
        onSave = {}
    )
}

