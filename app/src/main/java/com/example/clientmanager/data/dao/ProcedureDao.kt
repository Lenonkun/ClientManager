package com.example.clientmanager.data.dao

import androidx.room.*
import com.example.clientmanager.data.entity.ProcedureTypeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProcedureDao {
    @Query("SELECT * FROM procedure_types")
    fun getProcedureTypes(): Flow<List<ProcedureTypeEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProcedureType(procedureType: ProcedureTypeEntity)

    @Update
    suspend fun updateProcedureType(procedureType: ProcedureTypeEntity)

    @Query("DELETE FROM procedure_types WHERE name = :name")
    suspend fun deleteProcedureType(name: String)
}
