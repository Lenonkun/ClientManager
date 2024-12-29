package com.example.clientmanager.util

import com.example.clientmanager.data.entity.ClientEntity
import com.example.clientmanager.ui.screens.Client


fun ClientEntity.toClient(): Client {
    return Client(
        id = this.id,
        fullName = this.fullName,
        allergens = this.allergens ?: "",
        contraindications = this.contraindications ?: "",
        additionalInfo = this.additionalInfo ?: ""
    )
}

fun Client.toEntity(): ClientEntity {
    return ClientEntity(
        id = this.id, // ID будет использоваться как есть; если это новый клиент, Room сгенерирует ID.
        fullName = this.fullName,
        allergens = this.allergens?:"",
        contraindications = this.contraindications?: "",
        additionalInfo = this.additionalInfo?:""
    )
}
