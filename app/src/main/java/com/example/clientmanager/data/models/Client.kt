package com.example.clientmanager.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "clients")
data class Client(
    val id: Int,
    val name: String,
    val visits: Int = 0,
    val phoneNumber: String = "",
    val email: String = ""
)

