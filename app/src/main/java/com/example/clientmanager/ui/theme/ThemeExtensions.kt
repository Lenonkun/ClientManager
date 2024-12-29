package com.example.clientmanager.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Spacing(
    val small: Dp = 8.dp,
    val medium: Dp = 16.dp,
    val large: Dp = 24.dp
)

val LocalSpacing = staticCompositionLocalOf { Spacing() }

object CustomTheme {
    val spacing: Spacing
        @Composable
        get() = LocalSpacing.current
}
