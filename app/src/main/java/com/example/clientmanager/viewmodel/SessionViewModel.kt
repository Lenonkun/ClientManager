package com.example.clientmanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clientmanager.data.entity.ClientEntity
import com.example.clientmanager.data.entity.SessionEntity
import com.example.clientmanager.data.repository.ClientRepository
import com.example.clientmanager.repository.SessionRepository
import com.example.clientmanager.ui.screens.Client
import com.example.clientmanager.util.toEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import com.example.clientmanager.util.toClient

@HiltViewModel
class SessionViewModel @Inject constructor(
    private val sessionRepository: SessionRepository,
    private val clientRepository: ClientRepository
) : ViewModel() {

    private val _sessions = MutableStateFlow<List<SessionEntity>>(emptyList())
    val sessions: StateFlow<List<SessionEntity>> = _sessions

    fun addSession(session: SessionEntity) {
        viewModelScope.launch {
            sessionRepository.addSession(session)
            loadSessions(session.clientId)
        }
    }

    fun loadSessions(clientId: Int) {
        viewModelScope.launch {
            _sessions.value = sessionRepository.getSessionsByClient(clientId)
        }
    }

    fun addPhotosToSession(sessionId: Int, photoUris: List<String>) {
        viewModelScope.launch {
            sessionRepository.addPhotosToSession(sessionId, photoUris)
            _sessions.value = sessionRepository.getSessionsByClient(
                _sessions.value.find { it.id == sessionId }?.clientId ?: return@launch
            )
        }
    }

    fun searchClients(query: String): List<Client> {
        return runBlocking {
            // Получаем список ClientEntity из репозитория
            clientRepository.searchClients(query).map { it.toClient() } // Преобразуем каждый ClientEntity в Client для UI
        }
    }


    fun createClient(name: String): Int {
        val newClient = Client(
            id = 0, // Room сгенерирует ID
            fullName = name,
            allergens = "",
            contraindications = "",
            additionalInfo = ""
        )
        return runBlocking {
            clientRepository.addClient(newClient)
        }
    }


}
