package com.example.megax3player.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
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
    var demoMode by rememberSaveable {
        mutableIntStateOf(0)
    }

    val displayState = when (demoMode) {
        1 -> PlayerUiState.Loading
        2 -> PlayerUiState.Empty
        else -> state
    }

    val screenMode = when (displayState) {
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
            transitionSpec = {
                when {
                    initialState == PlayerScreenMode.LOADING &&
                            targetState == PlayerScreenMode.CONTENT -> {

                        (
                                fadeIn(
                                    animationSpec = tween(450)
                                ) +
                                        scaleIn(
                                            animationSpec = tween(450),
                                            initialScale = 0.94f
                                        )
                                ).togetherWith(
                                fadeOut(
                                    animationSpec = tween(250)
                                )
                            )
                    }

                    initialState == PlayerScreenMode.EMPTY &&
                            targetState == PlayerScreenMode.CONTENT -> {

                        (
                                fadeIn(
                                    animationSpec = tween(400)
                                ) +
                                        scaleIn(
                                            animationSpec = tween(400),
                                            initialScale = 0.96f
                                        )
                                ).togetherWith(
                                fadeOut(
                                    animationSpec = tween(250)
                                ) +
                                        scaleOut(
                                            animationSpec = tween(250),
                                            targetScale = 0.96f
                                        )
                            )
                    }

                    else -> {
                        fadeIn(
                            animationSpec = tween(300)
                        ).togetherWith(
                            fadeOut(
                                animationSpec = tween(220)
                            )
                        )
                    }
                }
            },
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
                        displayState as? PlayerUiState.Content
                            ?: state as? PlayerUiState.Content

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

        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .size(56.dp)
                .zIndex(10f)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = {
                            demoMode = (demoMode + 1) % 3
                        }
                    )
                }
        )
    }
}