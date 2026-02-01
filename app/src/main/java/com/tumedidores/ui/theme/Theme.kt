package com.tumedidores.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF006A65),
    onPrimary = Color(0xFFFFFFFF),
    secondary = Color(0xFF4A6360),
    onSecondary = Color(0xFFFFFFFF),
    tertiary = Color(0xFF006A65),
    onTertiary = Color(0xFFFFFFFF),
    background = Color(0xFFFAFDFB),
    onBackground = Color(0xFF191C1C),
    surface = Color(0xFFFAFDFB),
    onSurface = Color(0xFF191C1C),
    surfaceVariant = Color(0xFFDAE5E2),
    onSurfaceVariant = Color(0xFF3F4947),
    error = Color(0xFFBA1A1A),
    onError = Color(0xFFFFFFFF),
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF4FDAD2),
    onPrimary = Color(0xFF003734),
    secondary = Color(0xFFB0CCC8),
    onSecondary = Color(0xFF1B3532),
    tertiary = Color(0xFF4FDAD2),
    onTertiary = Color(0xFF003734),
    background = Color(0xFF191C1C),
    onBackground = Color(0xFFE0E3E2),
    surface = Color(0xFF191C1C),
    onSurface = Color(0xFFE0E3E2),
    surfaceVariant = Color(0xFF3F4947),
    onSurfaceVariant = Color(0xFFBEC9C6),
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
)

@Composable
fun MedidoresAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}