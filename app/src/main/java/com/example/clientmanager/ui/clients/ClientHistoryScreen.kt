package com.example.clientmanager.ui.clients

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.clientmanager.data.models.ClientSession
import com.example.clientmanager.ui.components.SessionItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientHistoryScreen(
    navController: NavController,
    clientId: Int,
    clientsViewModel: ClientsViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    // Получаем имя клиента и сессии из ViewModel
    val clientName = clientsViewModel.getClientNameById(clientId)
    val clientSessions = clientsViewModel.getSessionsByClientId(clientId)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("История: $clientName") },
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
            LazyColumn(modifier = Modifier.padding(padding)) {
                items(clientSessions) { session ->
                    SessionItem(
                        session = session,
                        onClick = { navController.navigate("session_detail/${session.id}") }
                    )
                }
            }
        }
    )
}



    @Composable
    fun SessionItem(session: ClientSession, onClick: () -> Unit) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(16.dp)
        ) {
            Text(
                text = "Дата: ${session.date}",
                fontWeight = FontWeight.Bold
            )
            Text(text = "Стоимость: ${session.totalCost} ₽")
        }
    }

    // Заглушка для получения имени клиента по ID
    fun getClientNameById(clientId: Int): String {
        return when (clientId) {
            1 -> "Иван Иванов"
            2 -> "Мария Смирнова"
            else -> "Алексей Кузнецов"
        }
    }

    // Заглушка для истории сеансов
    fun mockClientSessions(clientId: Int): List<ClientSession> {
        return listOf(
            ClientSession(
                id = 1,
                clientId = clientId,
                date = "2024-12-01",
                totalCost = 1500.0,
                resourcesUsed = listOf("Ресурс 1", "Ресурс 2").toString(),
                bodyZone = "Спина"
            ),
            ClientSession(
                id = 2,
                clientId = clientId,
                date = "2024-12-05",
                totalCost = 2000.0,
                resourcesUsed = listOf("Ресурс 3").toString(),
                bodyZone = "Руки"
            )
        )
    }

