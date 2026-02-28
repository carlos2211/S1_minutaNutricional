package com.example.s1_minutanutricional.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

// 🔹 Esquema CLARO - Azul nutricional
private val LightColorScheme = lightColorScheme(
    primary          = PrimaryBlue,
    onPrimary        = TextOnBlue,
    primaryContainer = PrimaryBlueLight,
    secondary        = AccentGreen,
    onSecondary      = TextOnBlue,
    tertiary         = AccentGreenLight,
    background       = SurfaceLight,
    surface          = CardLight,
    onSurface        = TextPrimary,
    onSurfaceVariant = TextSecondary,
    error            = DangerRed
)

// 🔹 Esquema OSCURO
private val DarkColorScheme = darkColorScheme(
    primary          = PrimaryBlueLight,
    onPrimary        = TextOnBlue,
    primaryContainer = PrimaryBlueDark,
    secondary        = AccentGreenLight,
    onSecondary      = TextOnBlue,
    tertiary         = AccentGreen,
    background       = SurfaceDark,
    surface          = CardDark,
    onSurface        = OnSurfaceDark,
    onSurfaceVariant = OnSurfaceDark,
    error            = DangerRed
)

@Composable
fun S1MinutaNutricionalTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // 🔹 Desactivado para usar nuestra paleta siempre
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else      -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography  = Typography,
        content     = content
    )
}