package com.example.clientmanager.ui.clients

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.clientmanager.data.models.ClientSession
import com.example.clientmanager.ui.sort.SortDialog
import com.example.clientmanager.ui.sort.SortOrder
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientsScreen(navController: NavController, clientsViewModel: ClientsViewModel = viewModel()) {
    LaunchedEffect(Unit) {
        clientsViewModel.fetchSessions(clientId = 1)
    }

    val clientSessions by clientsViewModel.clientSessions.collectAsState()
    var showSortDialog by remember { mutableStateOf(false) }
    var sortOrder by remember { mutableStateOf(SortOrder.BY_DATE_ASC) }

    if (showSortDialog) {
        SortDialog(
            currentSortOrder = sortOrder,
            onSortSelected = {
                sortOrder = it
                clientsViewModel.updateSessions(sortSessions(clientSessions, it))
                showSortDialog = false
            },
            onDismiss = { showSortDialog = false }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Клиенты") },
                actions = {
                    IconButton(onClick = { navController.navigate("add_session") }) {
                        Icon(Icons.Default.Add, contentDescription = "Добавить сессию")
                    }
                    IconButton(onClick = { showSortDialog = true }) {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = "Сортировать")
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
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                                    .clickable {
                                        navController.navigate("client_details/${session.clientId}")
                                    },
                                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text("Дата: ${session.date}", style = MaterialTheme.typography.bodyLarge)
                                    Text("Стоимость: ${session.totalCost}", style = MaterialTheme.typography.bodyMedium)
                                    Text("Ресурсы: ${session.resourcesUsed}", style = MaterialTheme.typography.bodySmall)
                                    Text("Зона тела: ${session.bodyZone}", style = MaterialTheme.typography.bodySmall)
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}

fun sortSessions(sessions: List<ClientSession>, sortOrder: SortOrder): List<ClientSession> {
    return when (sortOrder) {
        SortOrder.BY_DATE_ASC -> sessions.sortedBy { it.date }
        SortOrder.BY_DATE_DESC -> sessions.sortedByDescending { it.date }
    }
}


