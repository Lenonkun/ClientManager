import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import com.example.clientmanager.ui.theme.LocalSpacing
import com.example.clientmanager.ui.theme.Spacing
import androidx.compose.material3.Typography

private val CustomTypography = Typography()

@Composable
fun ClientManagerTheme(
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalSpacing provides Spacing()) {
        MaterialTheme(
            colorScheme = lightColorScheme(), // Или darkColorScheme, если нужно
            typography = CustomTypography,
            content = content
        )
    }
}
