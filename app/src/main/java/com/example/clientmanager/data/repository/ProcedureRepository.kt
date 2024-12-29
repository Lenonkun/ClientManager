package com.example.clientmanager.data.repository

import com.example.clientmanager.data.dao.ProcedureDao
import com.example.clientmanager.data.entity.ProcedureTypeEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProcedureRepository @Inject constructor(
    private val procedureDao: ProcedureDao
) {
    // Преобразуем Flow<List<ProcedureTypeEntity>> в Flow<List<String>>
    fun getProcedureTypes(): Flow<List<String>> {
        return procedureDao.getProcedureTypes().map { entities ->
            entities.map { it.name }
        }
    }

    suspend fun addProcedureType(name: String) {
        procedureDao.insertProcedureType(ProcedureTypeEntity(name = name))
    }

    suspend fun updateProcedureType(id: Int, name: String) {
        val existingProcedure = procedureDao.getProcedureTypes()
            .firstOrNull()?.find { it.id == id } ?: return
        procedureDao.updateProcedureType(existingProcedure.copy(name = name))
    }

    suspend fun deleteProcedureType(name: String) {
        procedureDao.deleteProcedureType(name)
    }
}
