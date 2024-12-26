package com.example.clientmanager.ui.clients

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.clientmanager.data.models.ClientSession


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddSessionScreen(
    navController: NavController,
    clientId: Int, // Передаём ID клиента для привязки
    clientsViewModel: ClientsViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onSave: (ClientSession) -> Unit
) {
    var resourcesUsed by remember { mutableStateOf("") }
    var bodyZone by remember { mutableStateOf("") }
    var totalCost by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Добавить сессию") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }
                }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                TextField(
                    value = date,
                    onValueChange = { date = it },
                    label = { Text("Дата (YYYY-MM-DD)") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = resourcesUsed,
                    onValueChange = { resourcesUsed = it },
                    label = { Text("Используемые ресурсы") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = bodyZone,
                    onValueChange = { bodyZone = it },
                    label = { Text("Зона тела") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = totalCost,
                    onValueChange = { totalCost = it },
                    label = { Text("Стоимость (в рублях)") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number // Используем KeyboardType.Number
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        val session = ClientSession(
                            id = (1..1000).random(), // Временный генератор ID
                            clientId = clientId, // Привязка к клиенту
                            date = date,
                            totalCost = totalCost.toDoubleOrNull() ?: 0.0,
                            resourcesUsed = resourcesUsed,
                            bodyZone = bodyZone
                        )
                        clientsViewModel.addSession(session) // Сохраняем через ViewModel
                        navController.popBackStack()
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Сохранить")
                }
            }
        }
    )
}
