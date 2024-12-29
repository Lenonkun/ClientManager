package com.example.clientmanager.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.clientmanager.data.entity.SessionEntity

@Dao
interface SessionDao {
    @Insert
    suspend fun insertSession(session: SessionEntity): Long

    @Query("SELECT * FROM sessions WHERE clientId = :clientId ORDER BY date DESC")
    suspend fun getSessionsByClientId(clientId: Int): List<SessionEntity>

    @Query("SELECT * FROM sessions WHERE id = :sessionId")
    suspend fun getSessionById(sessionId: Int): SessionEntity?

    @Update
    suspend fun updateSession(session: SessionEntity)
}
