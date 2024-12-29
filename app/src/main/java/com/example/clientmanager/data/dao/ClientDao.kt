package com.example.clientmanager.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.clientmanager.data.entity.ClientEntity
import com.example.clientmanager.ui.screens.Client

@Dao
interface ClientDao {
    @Query("SELECT * FROM clients WHERE id = :clientId LIMIT 1")
    suspend fun getClientById(clientId: Int): ClientEntity

    @Query("SELECT * FROM clients WHERE fullName LIKE '%' || :name || '%'")
    suspend fun searchClientsByName(name: String): List<ClientEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClient(client: ClientEntity): Long
}

