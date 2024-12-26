package com.example.clientmanager.ui.clients

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.setValue
import com.example.clientmanager.data.models.Client
import com.example.clientmanager.data.models.ClientSession
import com.example.clientmanager.ui.sort.SortOrder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ClientsViewModel : ViewModel() {
    private val _clients = mutableStateListOf<Client>()
    val clients: List<Client> get() = _clients

    private val _sessions = mutableStateListOf<ClientSession>()
    val sessions: List<ClientSession> get() = _sessions


    // Состояние для списка клиентов
    private val _clientSessions = MutableStateFlow<List<ClientSession>>(emptyList())
    val clientSessions: StateFlow<List<ClientSession>> get() = _clientSessions

    fun addClient(client: Client) {
        _clients.add(client)
    }

    fun updateClientName(clientId: Int, updatedName: String) {
        _clients.find { it.id == clientId }?.let {
            val index = _clients.indexOf(it)
            _clients[index] = it.copy(name = updatedName)
        }
    }

    fun getClientNameById(clientId: Int): String {
        return _clients.find { it.id == clientId }?.name ?: "Неизвестно"
    }
    fun getSessionsByClientId(clientId: Int): List<ClientSession> {
        return _sessions.filter { it.clientId == clientId }
    }



    // Добавление новой сессии
    fun addSession(session: ClientSession) {
        _sessions.add(session)
    }

    // Обновление сессии
    fun updateSession(updatedSession: ClientSession) {
        _sessions.replaceAll {
            if (it.id == updatedSession.id) updatedSession else it
        }
    }

    // Удаление сессии
    fun deleteSession(sessionId: Int) {
        _sessions.removeIf { it.id == sessionId }
    }

    // Получение мока сессий (для тестирования)
    fun fetchSessions(clientId: Int): List<ClientSession> {
        return _sessions.filter { it.clientId == clientId }
    }

    fun updateSessions(updatedSessions: List<ClientSession>) {
        _clientSessions.value = updatedSessions
    }




    // Мокаем данные
    private fun mockClientSessions(clientId: Int): List<ClientSession> {
        return listOf(
            ClientSession(
                id = 1, clientId = clientId, date = "2024-12-01", totalCost = 1500.0,
                resourcesUsed = "Resource1", bodyZone = "Face", clientName = "Клиент 1"
            ),
            ClientSession(
                id = 2, clientId = clientId, date = "2024-12-05", totalCost = 2000.0,
                resourcesUsed = "Resource2", bodyZone = "Back", clientName = "Клиент 1"
            )
        )
    }
}