package com.example.ui.theme

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
    primary = AmberSun,
    onPrimary = Color.Black,
    primaryContainer = AmberSunDark,
    onPrimaryContainer = Color.White,
    secondary = EmeraldEthiopia,
    onSecondary = Color.Black,
    tertiary = SkyBlueEthiopia,
    background = DarkBg,
    onBackground = LightText,
    surface = DarkSurface,
    onSurface = LightText,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = SoftLightText
)

private val LightColorScheme = lightColorScheme(
    primary = AmberSunDark,
    onPrimary = Color.White,
    primaryContainer = AmberSunLight,
    onPrimaryContainer = DarkText,
    secondary = EmeraldDark,
    onSecondary = Color.White,
    secondaryContainer = EmeraldLight,
    onSecondaryContainer = DarkText,
    tertiary = SkyBlueEthiopia,
    onTertiary = Color.White,
    background = WarmBgLight,
    onBackground = DarkText,
    surface = WarmSurfaceLight,
    onSurface = DarkText,
    surfaceVariant = WarmSurfaceVariantLight,
    onSurfaceVariant = SoftDarkText
)

@Composable
fun SpeakEngTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Keep consistent cheerful branding by default
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
