package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = VelvetPrimary,
    onPrimary = VelvetOnPrimary,
    primaryContainer = VelvetPrimaryContainer,
    onPrimaryContainer = VelvetOnPrimaryContainer,
    secondary = VelvetSecondary,
    onSecondary = VelvetOnSecondary,
    secondaryContainer = VelvetSecondaryContainer,
    onSecondaryContainer = VelvetOnSecondaryContainer,
    tertiary = VelvetTertiary,
    onTertiary = VelvetOnTertiary,
    tertiaryContainer = VelvetTertiaryContainer,
    onTertiaryContainer = VelvetOnTertiaryContainer,
    background = VelvetBackground,
    onBackground = VelvetOnBackground,
    surface = VelvetSurface,
    onSurface = VelvetOnBackground,
    surfaceVariant = VelvetSurfaceVariant,
    onSurfaceVariant = VelvetOnSurfaceVariant,
    outline = VelvetOutline,
    error = WaxCrimsonDark
)

private val LightColorScheme = lightColorScheme(
    primary = PlumPrimary,
    onPrimary = PlumOnPrimary,
    primaryContainer = PlumPrimaryContainer,
    onPrimaryContainer = PlumOnPrimaryContainer,
    secondary = GoldSecondary,
    onSecondary = GoldOnSecondary,
    secondaryContainer = GoldSecondaryContainer,
    onSecondaryContainer = GoldOnSecondaryContainer,
    tertiary = EmeraldTertiary,
    onTertiary = EmeraldOnTertiary,
    tertiaryContainer = EmeraldTertiaryContainer,
    onTertiaryContainer = EmeraldOnTertiaryContainer,
    background = ParchmentBackground,
    onBackground = ParchmentOnBackground,
    surface = ParchmentSurface,
    onSurface = ParchmentOnBackground,
    surfaceVariant = ParchmentSurfaceVariant,
    onSurfaceVariant = ParchmentOnSurfaceVariant,
    outline = ParchmentOutline,
    error = WaxCrimson
)

@Composable
fun FairyDetectiveTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}
