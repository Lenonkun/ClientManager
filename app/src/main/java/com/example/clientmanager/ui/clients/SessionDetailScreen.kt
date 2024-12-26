package com.example.clientmanager.ui.clients

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.clientmanager.data.models.ClientSession

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SessionDetailScreen(
    navController: NavController,
    sessionId: Int
) {
    val session = getSessionById(sessionId) // TODO: Заменить на данные из ViewModel

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Детали сеанса") },
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
                    .padding(16.dp)
            ) {
                Text(text = "Дата: ${session.date}", style = MaterialTheme.typography.titleMedium)
                Text(text = "Стоимость: ${session.totalCost} ₽", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Затраченные ресурсы: ${session.resourcesUsed}", style = MaterialTheme.typography.bodySmall)
                Text(text = "Зона тела: ${session.bodyZone}", style = MaterialTheme.typography.bodySmall)

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = { /* Логика редактирования сеанса */ }) {
                    Text("Редактировать")
                }
            }
        }
    )
}

// Заглушка для получения данных о сеансе
fun getSessionById(sessionId: Int): ClientSession {
    return ClientSession(
        id = sessionId,
        clientId = 1,
        date = "2024-12-01",
        totalCost = 1500.0,
        resourcesUsed = "Ресурс 1, Ресурс 2",
        bodyZone = "Спина"
    )
}
