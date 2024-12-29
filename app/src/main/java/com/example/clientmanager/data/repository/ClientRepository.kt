package com.example.clientmanager.data.repository

import com.example.clientmanager.data.dao.ClientDao
import com.example.clientmanager.data.entity.ClientEntity
import com.example.clientmanager.ui.screens.Client
import com.example.clientmanager.util.toClient
import com.example.clientmanager.util.toEntity
import javax.inject.Inject

class ClientRepository @Inject constructor(
    private val clientDao: ClientDao
) {
    suspend fun getClientById(clientId: Int): Client {
        return clientDao.getClientById(clientId).toClient()
    }

    suspend fun searchClients(query: String): List<ClientEntity> {
        return clientDao.searchClientsByName(query)
    }


    suspend fun addClient(client: Client): Int {
        return clientDao.insertClient(client.toEntity()).toInt()
    }
}
