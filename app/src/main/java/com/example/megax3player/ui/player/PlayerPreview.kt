package com.example.megax3player.ui

import android.content.res.Configuration
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.megax3player.model.Track
import com.example.megax3player.`val`.MegaX3PlayerTheme

private val previewTracks = listOf(
    Track(
        id = 1,
        title = "Windows XP",
        artist = "window",
        duration = "00:04",
        audioResId = 0
    ),
    Track(
        id = 2,
        title = "Lonely Day",
        artist = "System of a Down",
        duration = "02:47",
        audioResId = 0
    ),
    Track(
        id = 3,
        title = "Other People",
        artist = "LP",
        duration = "03:48",
        audioResId = 0
    )
)

private val previewState =
    PlayerUiState.Content(
        track = previewTracks.first(),
        isPlaying = true,
        positionMs = 2000L,
        durationMs = 4000L
    )

@Preview(
    name = "Playlist Portrait",
    showBackground = true,
    locale = "ru",
    widthDp = 390,
    heightDp = 800
)
@Composable
private fun PlaylistPortraitPreview() {
    PreviewPlaylist(
        darkTheme = false,
        width = WindowWidthSizeClass.Compact,
        height = WindowHeightSizeClass.Medium
    )
}

@Preview(
    name = "Playlist Dark",
    showBackground = true,
    locale = "ru",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    widthDp = 390,
    heightDp = 800
)
@Composable
private fun PlaylistDarkPreview() {
    PreviewPlaylist(
        darkTheme = true,
        width = WindowWidthSizeClass.Compact,
        height = WindowHeightSizeClass.Medium
    )
}

@Preview(
    name = "Playlist Landscape",
    showBackground = true,
    locale = "ru",
    widthDp = 800,
    heightDp = 360
)
@Composable
private fun PlaylistLandscapePreview() {
    PreviewPlaylist(
        darkTheme = false,
        width = WindowWidthSizeClass.Medium,
        height = WindowHeightSizeClass.Compact
    )
}

@Preview(
    name = "Playlist Landscape Dark",
    showBackground = true,
    locale = "ru",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    widthDp = 800,
    heightDp = 360
)
@Composable
private fun PlaylistLandscapeDarkPreview() {
    PreviewPlaylist(
        darkTheme = true,
        width = WindowWidthSizeClass.Medium,
        height = WindowHeightSizeClass.Compact
    )
}

@Preview(
    name = "Playlist Tablet",
    showBackground = true,
    locale = "en",
    widthDp = 900,
    heightDp = 600
)
@Composable
private fun PlaylistTabletPreview() {
    PreviewPlaylist(
        darkTheme = false,
        width = WindowWidthSizeClass.Expanded,
        height = WindowHeightSizeClass.Medium
    )
}

@Preview(
    name = "Playlist Empty",
    showBackground = true,
    locale = "ru",
    widthDp = 390,
    heightDp = 800
)
@Composable
private fun PlaylistEmptyPreview() {
    MegaX3PlayerTheme(
        darkTheme = false
    ) {
        PlaylistScreen(
            tracks = emptyList(),
            playerState = PlayerUiState.Empty,
            windowWidthSizeClass =
                WindowWidthSizeClass.Compact,
            windowHeightSizeClass =
                WindowHeightSizeClass.Medium,
            onBack = {},
            onTrackClick = {},
            onPlayPause = {},
            onNext = {},
            onOpenPlayer = {}
        )
    }
}

@Composable
private fun PreviewPlaylist(
    darkTheme: Boolean,
    width: WindowWidthSizeClass,
    height: WindowHeightSizeClass
) {
    MegaX3PlayerTheme(
        darkTheme = darkTheme
    ) {
        PlaylistScreen(
            tracks = previewTracks,
            playerState = previewState,
            windowWidthSizeClass = width,
            windowHeightSizeClass = height,
            onBack = {},
            onTrackClick = {},
            onPlayPause = {},
            onNext = {},
            onOpenPlayer = {}
        )
    }
}