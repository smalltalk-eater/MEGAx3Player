package com.example.megax3player.ui.player

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.megax3player.ui.PlayerUiState

@Composable
internal fun PlayerDevice(
    state: PlayerUiState.Content,
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
    if (windowHeightSizeClass == WindowHeightSizeClass.Compact) {
        LandscapePlayer(
            state = state,
            darkTheme = darkTheme,
            currentLanguage = currentLanguage,
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
        return
    }

    if (windowWidthSizeClass == WindowWidthSizeClass.Expanded) {
        WidePlayer(
            state = state,
            darkTheme = darkTheme,
            currentLanguage = currentLanguage,
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
        return
    }

    val maxWidth =
        if (windowWidthSizeClass == WindowWidthSizeClass.Medium) {
            520.dp
        } else {
            390.dp
        }

    CompactPlayer(
        state = state,
        darkTheme = darkTheme,
        currentLanguage = currentLanguage,
        maxWidth = maxWidth,
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

@Composable
private fun CompactPlayer(
    state: PlayerUiState.Content,
    darkTheme: Boolean,
    currentLanguage: String,
    maxWidth: androidx.compose.ui.unit.Dp,
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
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .widthIn(max = maxWidth)
            .fillMaxHeight()
            .clip(RoundedCornerShape(28.dp))
            .background(
                MaterialTheme.colorScheme.surfaceVariant
            )
            .border(
                2.dp,
                MaterialTheme.colorScheme.outline,
                RoundedCornerShape(28.dp)
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PlayerDisplay(
            state = state,
            darkTheme = darkTheme,
            currentLanguage = currentLanguage,
            compactHeight = false,
            onDarkThemeChange = onDarkThemeChange,
            onLanguageChange = onLanguageChange,
            onSeek = onSeek
        )

        Spacer(
            modifier = Modifier.weight(1f)
        )

        PlayerWheel(
            isPlaying = state.isPlaying,
            compactHeight = false,
            onOpenPlaylist = onOpenPlaylist,
            onPlayPause = onPlayPause,
            onPrevious = onPrevious,
            onNext = onNext
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        SecondaryControls(
            shuffle = state.shuffle,
            repeat = state.repeat,
            onShuffle = onShuffle,
            onRepeat = onRepeat
        )
    }
}

@Composable
private fun LandscapePlayer(
    state: PlayerUiState.Content,
    darkTheme: Boolean,
    currentLanguage: String,
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
    Row(
        modifier = Modifier
            .fillMaxSize()
            .widthIn(max = 900.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(
                MaterialTheme.colorScheme.surfaceVariant
            )
            .border(
                2.dp,
                MaterialTheme.colorScheme.outline,
                RoundedCornerShape(24.dp)
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1.18f)
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            PlayerDisplay(
                state = state,
                darkTheme = darkTheme,
                currentLanguage = currentLanguage,
                compactHeight = true,
                onDarkThemeChange = onDarkThemeChange,
                onLanguageChange = onLanguageChange,
                onSeek = onSeek
            )
        }

        Column(
            modifier = Modifier
                .weight(0.82f)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            PlayerWheel(
                isPlaying = state.isPlaying,
                compactHeight = true,
                onOpenPlaylist = onOpenPlaylist,
                onPlayPause = onPlayPause,
                onPrevious = onPrevious,
                onNext = onNext
            )

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            SecondaryControls(
                shuffle = state.shuffle,
                repeat = state.repeat,
                onShuffle = onShuffle,
                onRepeat = onRepeat,
                compact = true
            )
        }
    }
}

@Composable
private fun WidePlayer(
    state: PlayerUiState.Content,
    darkTheme: Boolean,
    currentLanguage: String,
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
    Row(
        modifier = Modifier
            .widthIn(max = 850.dp)
            .fillMaxHeight(0.86f)
            .clip(RoundedCornerShape(30.dp))
            .background(
                MaterialTheme.colorScheme.surfaceVariant
            )
            .border(
                2.dp,
                MaterialTheme.colorScheme.outline,
                RoundedCornerShape(30.dp)
            )
            .padding(24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(36.dp)
    ) {
        Box(
            modifier = Modifier.weight(1.1f)
        ) {
            PlayerDisplay(
                state = state,
                darkTheme = darkTheme,
                currentLanguage = currentLanguage,
                compactHeight = false,
                onDarkThemeChange = onDarkThemeChange,
                onLanguageChange = onLanguageChange,
                onSeek = onSeek
            )
        }

        Column(
            modifier = Modifier.weight(0.9f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            PlayerWheel(
                isPlaying = state.isPlaying,
                compactHeight = false,
                onOpenPlaylist = onOpenPlaylist,
                onPlayPause = onPlayPause,
                onPrevious = onPrevious,
                onNext = onNext
            )

            Spacer(
                modifier = Modifier.height(18.dp)
            )

            SecondaryControls(
                shuffle = state.shuffle,
                repeat = state.repeat,
                onShuffle = onShuffle,
                onRepeat = onRepeat
            )
        }
    }
}