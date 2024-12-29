package com.example.clientmanager.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "procedure_types")
data class ProcedureTypeEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String
)
