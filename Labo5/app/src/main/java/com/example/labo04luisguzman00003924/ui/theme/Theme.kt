package com.example.labo04luisguzman00003924.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = MinimalPrimary,
    secondary = MinimalSecondary,
    background = DarkBackground,
    surface = DarkSurface,
    surfaceVariant = DarkSurfaceVariant,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = DarkOnSurface,
    onSurface = DarkOnSurface,
    onSurfaceVariant = DarkOnSurfaceVariant,
    outline = DarkSurfaceVariant,
    outlineVariant = DarkSurfaceVariant
)

private val LightColorScheme = lightColorScheme(
    primary = MinimalPrimary,
    secondary = MinimalSecondary,
    background = MinimalBackground,
    surface = MinimalSurface,
    surfaceVariant = MinimalSurfaceVariant,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = MinimalOnBackground,
    onSurface = MinimalOnSurface,
    onSurfaceVariant = MinimalOnSurfaceVariant,
    outline = MinimalOutline,
    outlineVariant = MinimalOutline
)

@Composable
fun Labo04LuisGuzman00003924Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}