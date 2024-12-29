package com.example.clientmanager.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clients")
data class ClientEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val fullName: String,//ФИО
    val allergens: String = "",//Аллергены
    val contraindications: String = "", // противопоказания
    val additionalInfo: String = "" // Дополнительная информация
)
