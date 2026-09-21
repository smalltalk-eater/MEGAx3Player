package com.example.megax3player.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.megax3player.ui.player.PlayerDevice
import com.example.megax3player.ui.player.PlayerEmpty
import com.example.megax3player.ui.player.PlayerLoading

private enum class PlayerScreenMode {
    LOADING,
    EMPTY,
    CONTENT
}

@Composable
fun PlayerScreen(
    state: PlayerUiState,
    darkTheme: Boolean,
    currentLanguage: String,
    windowWidthSizeClass: WindowWidthSizeClass,
    windowHeightSizeClass: WindowHeightSizeClass,
    onDarkThemeChange: (Boolean) -> Unit,
    onLanguageChange: () -> Unit,
    onOpenPlaylist: () -> Unit,
    onPlayPause: () -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
    onSeek: (Float) -> Unit,
    onShuffle: () -> Unit,
    onRepeat: () -> Unit
) {
    val screenMode = when (state) {
        PlayerUiState.Loading -> PlayerScreenMode.LOADING
        PlayerUiState.Empty -> PlayerScreenMode.EMPTY
        is PlayerUiState.Content -> PlayerScreenMode.CONTENT
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .padding(14.dp),
        contentAlignment = Alignment.Center
    ) {
        AnimatedContent(
            targetState = screenMode,
            label = "playerState"
        ) { mode ->
            when (mode) {
                PlayerScreenMode.LOADING -> {
                    PlayerLoading(
                        windowWidthSizeClass = windowWidthSizeClass,
                        windowHeightSizeClass = windowHeightSizeClass
                    )
                }

                PlayerScreenMode.EMPTY -> {
                    PlayerEmpty(
                        windowWidthSizeClass = windowWidthSizeClass,
                        windowHeightSizeClass = windowHeightSizeClass,
                        onOpenPlaylist = onOpenPlaylist
                    )
                }

                PlayerScreenMode.CONTENT -> {
                    val content =
                        state as? PlayerUiState.Content

                    if (content != null) {
                        PlayerDevice(
                            state = content,
                            darkTheme = darkTheme,
                            currentLanguage = currentLanguage,
                            windowWidthSizeClass = windowWidthSizeClass,
                            windowHeightSizeClass = windowHeightSizeClass,
                            onDarkThemeChange = onDarkThemeChange,
                            onLanguageChange = onLanguageChange,
                            onOpenPlaylist = onOpenPlaylist,
                            onPlayPause = onPlayPause,
                            onNext = onNext,
                            onPrevious = onPrevious,
                            onSeek = onSeek,
                            onShuffle = onShuffle,
                            onRepeat = onRepeat
                        )
                    }
                }
            }
        }
    }
}