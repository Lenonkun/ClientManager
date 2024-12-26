package com.example.clientmanager.data.models

data class ClientSession(
    val id: Int, // Уникальный идентификатор сессии
    val clientId: Int, // Идентификатор клиента
    val date: String, // Дата сессии
    val totalCost: Double, // Общая стоимость
    val resourcesUsed: String, // Использованные ресурсы
    val bodyZone: String, // Зона тела
    val clientName: String = "" // Имя клиента (по желанию, для отображения)
)
