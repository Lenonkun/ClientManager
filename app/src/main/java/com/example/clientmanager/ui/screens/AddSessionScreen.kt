package com.example.clientmanager.ui.session

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.clientmanager.data.entity.SessionEntity
import com.example.clientmanager.data.entity.ClientEntity
import com.example.clientmanager.ui.screens.Client
import com.example.clientmanager.viewmodel.SessionViewModel

@Composable
fun AddSessionScreen(
    sessionViewModel: SessionViewModel,
    onBack: () -> Unit
) {
    var clientName by remember { mutableStateOf("") } // Имя клиента
    var filteredClients by remember { mutableStateOf(emptyList<Client>()) } // Список совпадений
    var selectedClient by remember { mutableStateOf<Client?>(null) } // Выбранный клиент
    var procedureType by remember { mutableStateOf("") }
    var zone by remember { mutableStateOf("") }
    var duration by remember { mutableStateOf("") }
    var resourcesUsed by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Добавить сеанс", style = MaterialTheme.typography.h6)

        // Поле для поиска клиента
        Column {
            BasicTextField(
                value = clientName,
                onValueChange = { input ->
                    clientName = input
                    filteredClients = sessionViewModel.searchClients(input) // Динамический поиск
                },
                modifier = Modifier.fillMaxWidth(),
                decorationBox = { innerTextField ->
                    if (clientName.isEmpty()) Text("Введите имя клиента")
                    innerTextField()
                }
            )
            DropdownMenu(
                expanded = filteredClients.isNotEmpty() && selectedClient == null,
                onDismissRequest = { filteredClients = emptyList() }
            ) {
                filteredClients.forEach { client ->
                    DropdownMenuItem(onClick = {
                        clientName = client.fullName
                        selectedClient = client
                        filteredClients = emptyList()
                    }) {
                        Text(client.fullName)
                    }
                }
            }
        }

        // Поля для ввода данных сеанса
        BasicTextField(
            value = procedureType,
            onValueChange = { procedureType = it },
            modifier = Modifier.fillMaxWidth(),
            decorationBox = { innerTextField ->
                if (procedureType.isEmpty()) Text("Введите вид процедуры")
                innerTextField()
            }
        )

        BasicTextField(
            value = zone,
            onValueChange = { zone = it },
            modifier = Modifier.fillMaxWidth(),
            decorationBox = { innerTextField ->
                if (zone.isEmpty()) Text("Введите зону")
                innerTextField()
            }
        )

        BasicTextField(
            value = duration,
            onValueChange = { duration = it },
            modifier = Modifier.fillMaxWidth(),
            decorationBox = { innerTextField ->
                if (duration.isEmpty()) Text("Введите длительность в минутах")
                innerTextField()
            }
        )

        BasicTextField(
            value = resourcesUsed,
            onValueChange = { resourcesUsed = it },
            modifier = Modifier.fillMaxWidth(),
            decorationBox = { innerTextField ->
                if (resourcesUsed.isEmpty()) Text("Введите ресурсы")
                innerTextField()
            }
        )

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(onClick = onBack) {
                Text("Отменить")
            }
            Button(onClick = {
                val clientId = selectedClient?.id ?: sessionViewModel.createClient(clientName)

                val session = SessionEntity(
                    clientId = clientId,
                    procedureType = procedureType,
                    zone = zone,
                    duration = duration.toIntOrNull() ?: 0,
                    resourcesUsed = resourcesUsed,
                    date = System.currentTimeMillis()
                )
                sessionViewModel.addSession(session)
                onBack()
            }) {
                Text("Добавить")
            }
        }
    }
}
