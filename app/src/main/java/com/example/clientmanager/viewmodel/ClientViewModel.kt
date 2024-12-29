package com.example.clientmanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clientmanager.data.repository.ClientRepository
import com.example.clientmanager.ui.screens.Client
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClientViewModel @Inject constructor(
    private val clientRepository: ClientRepository
) : ViewModel() {
    private val _client = MutableStateFlow<Client?>(null)
    val client: StateFlow<Client?> get() = _client

    fun loadClient(clientId: Int) {
        viewModelScope.launch {
            _client.value = clientRepository.getClientById(clientId)
        }
    }
}
