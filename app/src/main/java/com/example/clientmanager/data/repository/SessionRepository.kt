package com.example.clientmanager.repository

import com.example.clientmanager.data.dao.SessionDao
import com.example.clientmanager.data.entity.SessionEntity
import javax.inject.Inject

class SessionRepository @Inject constructor(
    private val sessionDao: SessionDao
) {
    suspend fun addSession(session: SessionEntity) = sessionDao.insertSession(session)
    suspend fun getSessionsByClient(clientId: Int) = sessionDao.getSessionsByClientId(clientId)
    suspend fun addPhotosToSession(sessionId: Int, photoUris: List<String>) {
        val session = sessionDao.getSessionById(sessionId)
        if (session != null) {
            val updatedPhotos = session.photos + photoUris
            sessionDao.updateSession(session.copy(photos = updatedPhotos))
        }
    }
}
