package com.example.clientmanager.ui.clients

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.clientmanager.data.models.ClientSession
import com.example.clientmanager.ui.components.SessionItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientDetailsScreen(
    navController: NavController,
    clientId: Int,
    clientsViewModel: ClientsViewModel
) {
    // Загружаем данные о сессиях клиента при запуске
    LaunchedEffect(clientId) {
        clientsViewModel.fetchSessions(clientId)
    }

    val clientSessions by clientsViewModel.clientSessions.collectAsState(emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Клиент $clientId") },
                actions = {
                    IconButton(onClick = { navController.navigate("add_session/$clientId") }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Добавить сессию"
                        )
                    }
                }
            )
        },
        content = { padding ->
            Column(modifier = Modifier.padding(padding)) {
                if (clientSessions.isEmpty()) {
                    Text(
                        text = "Сессий пока нет",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                } else {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(clientSessions) { session ->
                            SessionItem(
                                session = session,
                                onClick = {
                                    navController.navigate("session_details/${session.id}")
                                }
                            )
                        }
                    }
                }
            }
        }
    )
}
