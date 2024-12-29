package com.example.clientmanager.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationItem(val route: String, val title: String, val icon: ImageVector) {
    object Clients : NavigationItem("clients", "Клиенты", Icons.Default.Person)
    object Sessions : NavigationItem("sessions", "Сеансы", Icons.Default.Menu)
    object Calendar : NavigationItem("calendar", "Календарь", Icons.Default.DateRange)
    object Resources : NavigationItem("resources", "Ресурсы", Icons.Default.Build)
    object More : NavigationItem("more", "Дополнительно", Icons.Default.Star)
}
