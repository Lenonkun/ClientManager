package com.example.clientmanager.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sessions")
data class SessionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val clientId: Int, // ID клиента из ClientEntity
    val procedureType: String,
    val zone: String,
    val duration: Int, // В минутах
    val resourcesUsed: String, // Сериализованные данные о ресурсах
    val date: Long, // Время в миллисекундах
    val photos: List<String> = emptyList() // Поле для хранения фотографий
)
