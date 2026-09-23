package com.example.megax3player.`val`

import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

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

private val MiddleColors = lightColorScheme(
    primary = KittyPinkLight,
    onPrimary = IpodSilverLight,

    secondary = KittyPinkDark,
    onSecondary = IpodScreenText,

    background = KittyPinkLight,
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
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current

    val colorScheme = when {
        dynamicColor &&
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {

            if (darkTheme) {
                dynamicDarkColorScheme(context)
            } else {
                dynamicLightColorScheme(context)
            }
        }

        darkTheme -> DarkColors

        else -> MiddleColors
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}