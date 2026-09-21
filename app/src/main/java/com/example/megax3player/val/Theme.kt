package com.example.megax3player.`val`

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = KittyPinkLight,
    onPrimary = IpodSilverLight,

    secondary = KittyYellow,
    onSecondary = IpodScreenText,

    background = IpodBlue,
    onBackground = IpodScreenText,

    surface = IpodScreen,
    onSurface = IpodScreenText,

    surfaceVariant = IpodSilver,
    onSurfaceVariant = IpodScreenText,

    outline = IpodOutline
)

private val DarkColors = darkColorScheme(
    primary = KittyPinkDark,
    onPrimary = DarkBackground,

    secondary = KittyYellow,
    onSecondary = DarkBackground,

    background = DarkBackground,
    onBackground = DarkText,

    surface = DarkScreen,
    onSurface = DarkText,

    surfaceVariant = DarkBody,
    onSurfaceVariant = DarkText,

    outline = DarkOutline
)

@Composable
fun MegaX3PlayerTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColors else LightColors,
        typography = AppTypography,
        content = content
    )
}