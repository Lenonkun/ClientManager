package com.example.clientmanager.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.clientmanager.data.models.ClientSession
//import com.example.clientmanager.models.ClientSession

@Composable
fun SessionItem(
    session: ClientSession,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Text(
            text = "Дата: ${session.date}",
            fontWeight = FontWeight.Bold
        )
        Text(text = "Стоимость: ${session.totalCost} ₽")
    }
}

