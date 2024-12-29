package com.example.clientmanager.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.clientmanager.ui.theme.CustomTheme

data class Client(
    val id: Int,
    var fullName: String,
    var allergens: String?,
    var contraindications: String?,
    var additionalInfo: String?,
    val sessions: MutableList<Session> = mutableListOf()
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientsScreen(navController: NavController) {
    val clients = remember { mutableStateListOf<Client>() }
    var showAddClientDialog by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") } // Для строки поиска


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Клиенты") },
                actions = { // Добавляем кнопку в правый верхний угол
                    IconButton(onClick = { showAddClientDialog = true }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Добавить клиента",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        }

    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            // Строка поиска
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(CustomTheme.spacing.small),
                label = { Text("Поиск клиента") }
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(CustomTheme.spacing.small)
            ) {
                val filteredClients = clients.filter { client ->
                    client.fullName.contains(searchQuery, ignoreCase = true)
                }
                items(filteredClients.size) { index ->
                    ClientItem(
                        client = filteredClients[index],
                        onClick = {
                            navController.navigate("clientDetails/${filteredClients[index].id}")
                        }
                    )
                }
            }
        }

        if (showAddClientDialog) {
            AddOrEditClientDialog(
                client = null,
                onSave = { client ->
                    clients.add(client)
                    showAddClientDialog = false
                },
                onCancel = { showAddClientDialog = false }
            )
        }
    }
}

@Composable
fun ClientItem(client: Client, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = client.fullName,
                style = MaterialTheme.typography.titleMedium
            )
            client.additionalInfo?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
fun AddOrEditClientDialog(
    client: Client?,
    onSave: (Client) -> Unit,
    onCancel: () -> Unit
) {
    var fullName by remember { mutableStateOf(client?.fullName ?: "") }
    var allergens by remember { mutableStateOf(client?.allergens ?: "") }
    var contraindications by remember { mutableStateOf(client?.contraindications ?: "") }
    var additionalInfo by remember { mutableStateOf(client?.additionalInfo ?: "") }

    AlertDialog(
        onDismissRequest = onCancel,
        title = { Text(text = if (client == null) "Добавить клиента" else "Редактировать клиента") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                TextField(
                    value = fullName,
                    onValueChange = { fullName = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("ФИО") }
                )
                TextField(
                    value = allergens,
                    onValueChange = { allergens = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Аллергены") }
                )
                TextField(
                    value = contraindications,
                    onValueChange = { contraindications = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Противопоказания") }
                )
                TextField(
                    value = additionalInfo,
                    onValueChange = { additionalInfo = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Дополнительная информация") }
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                if (fullName.isNotBlank()) {
                    onSave(
                        Client(
                            id = (1..Int.MAX_VALUE).random(),
                            fullName = fullName,
                            allergens = allergens.takeIf { it.isNotBlank() },
                            contraindications = contraindications.takeIf { it.isNotBlank() },
                            additionalInfo = additionalInfo.takeIf { it.isNotBlank() }
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


@Composable
fun SessionItem(session: Session, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Дата: ${session.date}",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Вид процедуры: ${session.procedureType}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Зона: ${session.zone}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
