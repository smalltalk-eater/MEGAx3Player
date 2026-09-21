package com.example.megax3player.ui.player

import android.content.res.Configuration
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.megax3player.model.Track
import com.example.megax3player.ui.PlayerScreen
import com.example.megax3player.ui.PlayerUiState
import com.example.megax3player.ui.theme.MegaX3PlayerTheme

private val previewTrack = Track(
    id = 1,
    title = "Lonely Day",
    artist = "System of a Down",
    duration = "02:47",
    audioResId = 0,
    coverResId = 0
)

@Preview(
    name = "Player Portrait",
    showBackground = true,
    locale = "ru",
    widthDp = 390,
    heightDp = 800
)
@Composable
private fun PlayerPortraitPreview() {
    PreviewPlayer(
        darkTheme = false,
        width = WindowWidthSizeClass.Compact,
        height = WindowHeightSizeClass.Medium
    )
}

@Preview(
    name = "Player Dark",
    showBackground = true,
    locale = "ru",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    widthDp = 390,
    heightDp = 800
)
@Composable
private fun PlayerDarkPreview() {
    PreviewPlayer(
        darkTheme = true,
        width = WindowWidthSizeClass.Compact,
        height = WindowHeightSizeClass.Medium
    )
}

@Preview(
    name = "Player Landscape",
    showBackground = true,
    locale = "ru",
    widthDp = 800,
    heightDp = 360
)
@Composable
private fun PlayerLandscapePreview() {
    PreviewPlayer(
        darkTheme = false,
        width = WindowWidthSizeClass.Medium,
        height = WindowHeightSizeClass.Compact
    )
}

@Preview(
    name = "Player Landscape Dark",
    showBackground = true,
    locale = "ru",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    widthDp = 800,
    heightDp = 360
)
@Composable
private fun PlayerLandscapeDarkPreview() {
    PreviewPlayer(
        darkTheme = true,
        width = WindowWidthSizeClass.Medium,
        height = WindowHeightSizeClass.Compact
    )
}

@Preview(
    name = "Player Tablet",
    showBackground = true,
    locale = "en",
    widthDp = 900,
    heightDp = 600
)
@Composable
private fun PlayerTabletPreview() {
    PreviewPlayer(
        darkTheme = false,
        width = WindowWidthSizeClass.Expanded,
        height = WindowHeightSizeClass.Medium
    )
}

@Preview(
    name = "Player Loading",
    showBackground = true,
    widthDp = 390,
    heightDp = 800
)
@Composable
private fun PlayerLoadingPreview() {
    MegaX3PlayerTheme(
        darkTheme = false
    ) {
        PlayerScreen(
            state = PlayerUiState.Loading,
            darkTheme = false,
            currentLanguage = "en",
            windowWidthSizeClass =
                WindowWidthSizeClass.Compact,
            windowHeightSizeClass =
                WindowHeightSizeClass.Medium,
            onDarkThemeChange = {},
            onLanguageChange = {},
            onOpenPlaylist = {},
            onPlayPause = {},
            onNext = {},
            onPrevious = {},
            onSeek = {},
            onShuffle = {},
            onRepeat = {}
        )
    }
}

@Preview(
    name = "Player Empty",
    showBackground = true,
    locale = "ru",
    widthDp = 390,
    heightDp = 800
)
@Composable
private fun PlayerEmptyPreview() {
    MegaX3PlayerTheme(
        darkTheme = false
    ) {
        PlayerScreen(
            state = PlayerUiState.Empty,
            darkTheme = false,
            currentLanguage = "ru",
            windowWidthSizeClass =
                WindowWidthSizeClass.Compact,
            windowHeightSizeClass =
                WindowHeightSizeClass.Medium,
            onDarkThemeChange = {},
            onLanguageChange = {},
            onOpenPlaylist = {},
            onPlayPause = {},
            onNext = {},
            onPrevious = {},
            onSeek = {},
            onShuffle = {},
            onRepeat = {}
        )
    }
}

@Composable
private fun PreviewPlayer(
    darkTheme: Boolean,
    width: WindowWidthSizeClass,
    height: WindowHeightSizeClass
) {
    MegaX3PlayerTheme(
        darkTheme = darkTheme
    ) {
        PlayerScreen(
            state = PlayerUiState.Content(
                track = previewTrack,
                isPlaying = true,
                positionMs = 80000L,
                durationMs = 167000L,
                shuffle = true,
                repeat = false
            ),
            darkTheme = darkTheme,
            currentLanguage = "ru",
            windowWidthSizeClass = width,
            windowHeightSizeClass = height,
            onDarkThemeChange = {},
            onLanguageChange = {},
            onOpenPlaylist = {},
            onPlayPause = {},
            onNext = {},
            onPrevious = {},
            onSeek = {},
            onShuffle = {},
            onRepeat = {}
        )
    }
}