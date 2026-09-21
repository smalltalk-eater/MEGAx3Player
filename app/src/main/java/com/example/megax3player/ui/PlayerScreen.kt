package com.example.megax3player.ui

import android.content.res.Configuration
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.megax3player.R
import com.example.megax3player.model.Track
import com.example.megax3player.ui.theme.MegaX3PlayerTheme

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

@Composable
private fun PlayerDevice(
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
    if (
        windowHeightSizeClass ==
        WindowHeightSizeClass.Compact
    ) {
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

    when (windowWidthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            CompactPlayer(
                state = state,
                darkTheme = darkTheme,
                currentLanguage = currentLanguage,
                maxWidthDp = 390,
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

        WindowWidthSizeClass.Medium -> {
            CompactPlayer(
                state = state,
                darkTheme = darkTheme,
                currentLanguage = currentLanguage,
                maxWidthDp = 520,
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

        WindowWidthSizeClass.Expanded -> {
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
        }

        else -> {
            CompactPlayer(
                state = state,
                darkTheme = darkTheme,
                currentLanguage = currentLanguage,
                maxWidthDp = 390,
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

@Composable
private fun CompactPlayer(
    state: PlayerUiState.Content,
    darkTheme: Boolean,
    currentLanguage: String,
    maxWidthDp: Int,
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
            .widthIn(max = maxWidthDp.dp)
            .fillMaxHeight()
            .clip(
                RoundedCornerShape(28.dp)
            )
            .background(
                MaterialTheme.colorScheme.surfaceVariant
            )
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(28.dp)
            )
            .padding(16.dp),
        horizontalAlignment =
            Alignment.CenterHorizontally
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

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "MEGAx3Player",
            style =
                MaterialTheme.typography.labelSmall.copy(
                    fontFamily =
                        FontFamily.Monospace,
                    letterSpacing = 2.sp
                ),
            color =
                MaterialTheme.colorScheme.onSurfaceVariant
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
            .clip(
                RoundedCornerShape(24.dp)
            )
            .background(
                MaterialTheme.colorScheme.surfaceVariant
            )
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(24.dp)
            )
            .padding(12.dp),
        verticalAlignment =
            Alignment.CenterVertically,
        horizontalArrangement =
            Arrangement.spacedBy(18.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1.15f)
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
                .weight(0.85f)
                .fillMaxHeight(),
            horizontalAlignment =
                Alignment.CenterHorizontally,
            verticalArrangement =
                Arrangement.Center
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
                onRepeat = onRepeat
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(
                text = "MEGAx3Player",
                style =
                    MaterialTheme.typography.labelSmall.copy(
                        fontFamily =
                            FontFamily.Monospace
                    ),
                color =
                    MaterialTheme.colorScheme.onSurfaceVariant
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
            .clip(
                RoundedCornerShape(30.dp)
            )
            .background(
                MaterialTheme.colorScheme.surfaceVariant
            )
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(30.dp)
            )
            .padding(24.dp),
        verticalAlignment =
            Alignment.CenterVertically,
        horizontalArrangement =
            Arrangement.spacedBy(36.dp)
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
            horizontalAlignment =
                Alignment.CenterHorizontally
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

@Composable
private fun PlayerDisplay(
    state: PlayerUiState.Content,
    darkTheme: Boolean,
    currentLanguage: String,
    compactHeight: Boolean,
    onDarkThemeChange: (Boolean) -> Unit,
    onLanguageChange: () -> Unit,
    onSeek: (Float) -> Unit
) {
    val progress =
        if (state.durationMs > 0L) {
            state.positionMs.toFloat() /
                    state.durationMs.toFloat()
        } else {
            0f
        }

    val displayPadding =
        if (compactHeight) {
            8.dp
        } else {
            14.dp
        }

    val coverSize =
        if (compactHeight) {
            88.dp
        } else {
            145.dp
        }

    val statusGap =
        if (compactHeight) {
            2.dp
        } else {
            8.dp
        }

    val coverGap =
        if (compactHeight) {
            4.dp
        } else {
            10.dp
        }

    val progressGap =
        if (compactHeight) {
            2.dp
        } else {
            8.dp
        }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(16.dp)
            )
            .background(
                MaterialTheme.colorScheme.surface
            )
            .border(
                width = 3.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(displayPadding),
        horizontalAlignment =
            Alignment.CenterHorizontally
    ) {
        PlayerStatusBar(
            darkTheme = darkTheme,
            currentLanguage = currentLanguage,
            onDarkThemeChange = onDarkThemeChange,
            onLanguageChange = onLanguageChange
        )

        Spacer(
            modifier = Modifier.height(statusGap)
        )

        TrackCover(
            coverResId =
                state.track.coverResId,
            title =
                state.track.title,
            size =
                coverSize
        )

        Spacer(
            modifier = Modifier.height(coverGap)
        )

        Text(
            text = state.track.title,
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            overflow =
                TextOverflow.Ellipsis,
            textAlign =
                TextAlign.Center,
            style =
                if (compactHeight) {
                    MaterialTheme.typography
                        .titleMedium.copy(
                            fontFamily =
                                FontFamily.Monospace
                        )
                } else {
                    MaterialTheme.typography
                        .titleLarge.copy(
                            fontFamily =
                                FontFamily.Monospace
                        )
                },
            color =
                MaterialTheme.colorScheme.onSurface
        )

        Spacer(
            modifier =
                Modifier.height(
                    if (compactHeight) {
                        1.dp
                    } else {
                        3.dp
                    }
                )
        )

        Text(
            text = state.track.artist,
            maxLines = 1,
            overflow =
                TextOverflow.Ellipsis,
            style =
                MaterialTheme.typography
                    .bodyMedium.copy(
                        fontFamily =
                            FontFamily.Monospace
                    ),
            color =
                MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(
            modifier = Modifier.height(progressGap)
        )

        ProgressSection(
            progress = progress,
            positionMs = state.positionMs,
            durationMs = state.durationMs,
            onProgressChange = onSeek
        )
    }
}

@Composable
private fun PlayerStatusBar(
    darkTheme: Boolean,
    currentLanguage: String,
    onDarkThemeChange: (Boolean) -> Unit,
    onLanguageChange: () -> Unit
) {
    val languageDescription =
        stringResource(
            R.string.switch_language
        )

    val languageState =
        stringResource(
            R.string.language_state
        )

    val themeDescription =
        stringResource(
            R.string.switch_theme
        )

    val themeState =
        if (darkTheme) {
            stringResource(
                R.string.dark_theme_enabled
            )
        } else {
            stringResource(
                R.string.light_theme_enabled
            )
        }

    Row(
        modifier =
            Modifier.fillMaxWidth(),
        verticalAlignment =
            Alignment.CenterVertically
    ) {
        Text(
            text = "MEGA",
            modifier =
                Modifier.weight(1f),
            style =
                MaterialTheme.typography
                    .labelSmall.copy(
                        fontFamily =
                            FontFamily.Monospace
                    ),
            fontWeight =
                FontWeight.Bold,
            color =
                MaterialTheme.colorScheme.onSurfaceVariant
        )

        Box(
            modifier = Modifier
                .sizeIn(
                    minWidth = 48.dp,
                    minHeight = 48.dp
                )
                .clip(
                    RoundedCornerShape(8.dp)
                )
                .semantics {
                    role = Role.Button

                    contentDescription =
                        languageDescription

                    stateDescription =
                        languageState
                }
                .clickable(
                    role = Role.Button,
                    onClick =
                        onLanguageChange
                ),
            contentAlignment =
                Alignment.Center
        ) {
            Text(
                text =
                    if (
                        currentLanguage == "ru"
                    ) {
                        "RU"
                    } else {
                        "EN"
                    },
                style =
                    MaterialTheme.typography
                        .labelSmall.copy(
                            fontFamily =
                                FontFamily.Monospace
                        ),
                fontWeight =
                    FontWeight.Bold,
                color =
                    MaterialTheme.colorScheme.primary
            )
        }

        Spacer(
            modifier = Modifier.width(2.dp)
        )

        Box(
            modifier = Modifier
                .sizeIn(
                    minWidth = 56.dp,
                    minHeight = 48.dp
                )
                .clip(
                    RoundedCornerShape(8.dp)
                )
                .semantics {
                    role = Role.Button

                    contentDescription =
                        themeDescription

                    stateDescription =
                        themeState
                }
                .clickable(
                    role = Role.Button
                ) {
                    onDarkThemeChange(
                        !darkTheme
                    )
                },
            contentAlignment =
                Alignment.Center
        ) {
            Text(
                text =
                    if (darkTheme) {
                        stringResource(
                            R.string.theme_dark
                        )
                    } else {
                        stringResource(
                            R.string.theme_light
                        )
                    },
                style =
                    MaterialTheme.typography
                        .labelSmall.copy(
                            fontFamily =
                                FontFamily.Monospace
                        ),
                fontWeight =
                    FontWeight.Bold,
                color =
                    MaterialTheme.colorScheme.primary
            )
        }

        Spacer(
            modifier = Modifier.width(4.dp)
        )

        BatteryIndicator()
    }
}

@Composable
private fun BatteryIndicator() {
    val description =
        stringResource(
            R.string.battery_72
        )

    Box(
        modifier = Modifier
            .width(26.dp)
            .height(12.dp)
            .border(
                width = 1.dp,
                color =
                    MaterialTheme.colorScheme.onSurfaceVariant,
                shape =
                    RoundedCornerShape(2.dp)
            )
            .padding(2.dp)
            .semantics {
                contentDescription =
                    description
            }
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.72f)
                .background(
                    MaterialTheme.colorScheme.onSurfaceVariant
                )
        )
    }
}

@Composable
private fun TrackCover(
    coverResId: Int,
    title: String,
    size: Dp = 145.dp
) {
    val description =
        stringResource(
            R.string.track_cover,
            title
        )

    Box(
        modifier = Modifier
            .size(size)
            .clip(
                RoundedCornerShape(12.dp)
            )
            .background(
                MaterialTheme.colorScheme.surfaceVariant
            )
            .semantics {
                contentDescription =
                    description
            },
        contentAlignment =
            Alignment.Center
    ) {
        if (coverResId != 0) {
            Image(
                painter =
                    painterResource(
                        coverResId
                    ),
                contentDescription = null,
                modifier =
                    Modifier.fillMaxSize(),
                contentScale =
                    ContentScale.Crop
            )
        } else {
            KittyFace(
                size = size * 0.72f
            )
        }
    }
}

@Composable
private fun KittyFace(
    size: Dp = 105.dp
) {
    val faceColor =
        MaterialTheme.colorScheme.surfaceVariant

    val outlineColor =
        MaterialTheme.colorScheme.onSurface

    val bowColor =
        MaterialTheme.colorScheme.primary

    val noseColor =
        MaterialTheme.colorScheme.secondary

    Canvas(
        modifier = Modifier.size(size)
    ) {
        val w = this.size.width
        val h = this.size.height

        val leftEar =
            Path().apply {
                moveTo(
                    w * 0.22f,
                    h * 0.37f
                )
                lineTo(
                    w * 0.29f,
                    h * 0.12f
                )
                lineTo(
                    w * 0.43f,
                    h * 0.30f
                )
                close()
            }

        val rightEar =
            Path().apply {
                moveTo(
                    w * 0.57f,
                    h * 0.30f
                )
                lineTo(
                    w * 0.72f,
                    h * 0.12f
                )
                lineTo(
                    w * 0.79f,
                    h * 0.37f
                )
                close()
            }

        drawPath(
            path = leftEar,
            color = faceColor
        )

        drawPath(
            path = rightEar,
            color = faceColor
        )

        drawRoundRect(
            color = faceColor,
            topLeft = Offset(
                x = w * 0.17f,
                y = h * 0.25f
            ),
            size = Size(
                width = w * 0.66f,
                height = h * 0.57f
            ),
            cornerRadius =
                CornerRadius(
                    x = w * 0.20f,
                    y = w * 0.20f
                )
        )

        drawCircle(
            color = outlineColor,
            radius = w * 0.025f,
            center = Offset(
                x = w * 0.38f,
                y = h * 0.52f
            )
        )

        drawCircle(
            color = outlineColor,
            radius = w * 0.025f,
            center = Offset(
                x = w * 0.62f,
                y = h * 0.52f
            )
        )

        drawOval(
            color = noseColor,
            topLeft = Offset(
                x = w * 0.47f,
                y = h * 0.58f
            ),
            size = Size(
                width = w * 0.06f,
                height = h * 0.045f
            )
        )

        drawLine(
            color = outlineColor,
            start = Offset(
                x = w * 0.29f,
                y = h * 0.57f
            ),
            end = Offset(
                x = w * 0.08f,
                y = h * 0.52f
            ),
            strokeWidth = 3f
        )

        drawLine(
            color = outlineColor,
            start = Offset(
                x = w * 0.29f,
                y = h * 0.63f
            ),
            end = Offset(
                x = w * 0.07f,
                y = h * 0.65f
            ),
            strokeWidth = 3f
        )

        drawLine(
            color = outlineColor,
            start = Offset(
                x = w * 0.71f,
                y = h * 0.57f
            ),
            end = Offset(
                x = w * 0.92f,
                y = h * 0.52f
            ),
            strokeWidth = 3f
        )

        drawLine(
            color = outlineColor,
            start = Offset(
                x = w * 0.71f,
                y = h * 0.63f
            ),
            end = Offset(
                x = w * 0.93f,
                y = h * 0.65f
            ),
            strokeWidth = 3f
        )

        drawOval(
            color = bowColor,
            topLeft = Offset(
                x = w * 0.60f,
                y = h * 0.14f
            ),
            size = Size(
                width = w * 0.17f,
                height = h * 0.18f
            )
        )

        drawOval(
            color = bowColor,
            topLeft = Offset(
                x = w * 0.75f,
                y = h * 0.16f
            ),
            size = Size(
                width = w * 0.17f,
                height = h * 0.18f
            )
        )

        drawCircle(
            color = bowColor,
            radius = w * 0.055f,
            center = Offset(
                x = w * 0.75f,
                y = h * 0.24f
            )
        )
    }
}

@Composable
private fun ProgressSection(
    progress: Float,
    positionMs: Long,
    durationMs: Long,
    onProgressChange: (Float) -> Unit
) {
    var sliderValue by remember {
        mutableFloatStateOf(progress)
    }

    var isSeeking by remember {
        mutableStateOf(false)
    }

    val progressDescription =
        stringResource(
            R.string.playback_position
        )

    LaunchedEffect(
        progress,
        isSeeking
    ) {
        if (!isSeeking) {
            sliderValue =
                progress.coerceIn(
                    0f,
                    1f
                )
        }
    }

    Column(
        modifier =
            Modifier.fillMaxWidth()
    ) {
        Slider(
            value =
                sliderValue.coerceIn(
                    0f,
                    1f
                ),
            onValueChange = {
                isSeeking = true
                sliderValue = it
            },
            onValueChangeFinished = {
                onProgressChange(
                    sliderValue
                )

                isSeeking = false
            },
            valueRange = 0f..1f,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .semantics {
                    contentDescription =
                        progressDescription
                },
            colors =
                SliderDefaults.colors(
                    thumbColor =
                        MaterialTheme.colorScheme.primary,

                    activeTrackColor =
                        MaterialTheme.colorScheme.primary,

                    inactiveTrackColor =
                        MaterialTheme.colorScheme.outline
                )
        )

        Row(
            modifier =
                Modifier.fillMaxWidth(),
            horizontalArrangement =
                Arrangement.SpaceBetween
        ) {
            TimeText(
                text =
                    if (
                        isSeeking &&
                        durationMs > 0L
                    ) {
                        formatTime(
                            (
                                    durationMs *
                                            sliderValue
                                    ).toLong()
                        )
                    } else {
                        formatTime(
                            positionMs
                        )
                    }
            )

            TimeText(
                text =
                    formatTime(
                        durationMs
                    )
            )
        }
    }
}

@Composable
private fun TimeText(
    text: String
) {
    Text(
        text = text,
        style =
            MaterialTheme.typography
                .labelSmall.copy(
                    fontFamily =
                        FontFamily.Monospace
                ),
        color =
            MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Composable
private fun PlayerWheel(
    isPlaying: Boolean,
    compactHeight: Boolean,
    onOpenPlaylist: () -> Unit,
    onPlayPause: () -> Unit,
    onPrevious: () -> Unit,
    onNext: () -> Unit
) {
    val openPlaylistDescription =
        stringResource(
            R.string.open_playlist
        )

    val previousDescription =
        stringResource(
            R.string.previous_track
        )

    val nextDescription =
        stringResource(
            R.string.next_track
        )

    val playDescription =
        if (isPlaying) {
            stringResource(
                R.string.pause_playback
            )
        } else {
            stringResource(
                R.string.start_playback
            )
        }

    val wheelFraction =
        if (compactHeight) {
            0.82f
        } else {
            0.76f
        }

    val wheelMaxSize =
        if (compactHeight) {
            182.dp
        } else {
            250.dp
        }

    val verticalPadding =
        if (compactHeight) {
            4.dp
        } else {
            8.dp
        }

    Box(
        modifier = Modifier
            .fillMaxWidth(wheelFraction)
            .widthIn(
                max = wheelMaxSize
            )
            .aspectRatio(1f)
            .clip(CircleShape)
            .background(
                MaterialTheme.colorScheme.surface
            )
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = CircleShape
            )
    ) {
        WheelButton(
            text =
                stringResource(
                    R.string.menu
                ),
            description =
                openPlaylistDescription,
            modifier = Modifier
                .align(
                    Alignment.TopCenter
                )
                .padding(
                    top = verticalPadding
                ),
            onClick =
                onOpenPlaylist
        )

        WheelButton(
            text =
                stringResource(
                    R.string.previous
                ),
            description =
                previousDescription,
            modifier = Modifier
                .align(
                    Alignment.CenterStart
                )
                .padding(
                    start = 4.dp
                ),
            onClick =
                onPrevious
        )

        WheelButton(
            text =
                stringResource(
                    R.string.next
                ),
            description =
                nextDescription,
            modifier = Modifier
                .align(
                    Alignment.CenterEnd
                )
                .padding(
                    end = 4.dp
                ),
            onClick =
                onNext
        )

        WheelButton(
            text =
                if (isPlaying) {
                    stringResource(
                        R.string.pause
                    )
                } else {
                    stringResource(
                        R.string.play
                    )
                },
            description =
                playDescription,
            modifier = Modifier
                .align(
                    Alignment.BottomCenter
                )
                .padding(
                    bottom = verticalPadding
                ),
            onClick =
                onPlayPause
        )

        Box(
            modifier = Modifier
                .fillMaxSize(0.38f)
                .align(
                    Alignment.Center
                )
                .clip(CircleShape)
                .background(
                    MaterialTheme.colorScheme.surfaceVariant
                )
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = CircleShape
                )
                .semantics {
                    role = Role.Button

                    contentDescription =
                        playDescription
                }
                .clickable(
                    role = Role.Button,
                    onClick =
                        onPlayPause
                ),
            contentAlignment =
                Alignment.Center
        ) {
            BowMark()
        }
    }
}

@Composable
private fun WheelButton(
    text: String,
    description: String,
    modifier: Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .sizeIn(
                minWidth = 48.dp,
                minHeight = 48.dp
            )
            .clip(
                RoundedCornerShape(8.dp)
            )
            .semantics {
                role = Role.Button

                contentDescription =
                    description
            }
            .clickable(
                role = Role.Button,
                onClick = onClick
            ),
        contentAlignment =
            Alignment.Center
    ) {
        Text(
            text = text,
            style =
                MaterialTheme.typography
                    .labelSmall.copy(
                        fontFamily =
                            FontFamily.Monospace
                    ),
            fontWeight =
                FontWeight.Bold,
            color =
                MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun BowMark() {
    val color =
        MaterialTheme.colorScheme.primary

    Canvas(
        modifier = Modifier.size(34.dp)
    ) {
        drawOval(
            color = color,
            topLeft = Offset(
                x = size.width * 0.05f,
                y = size.height * 0.23f
            ),
            size = Size(
                width = size.width * 0.40f,
                height = size.height * 0.54f
            )
        )

        drawOval(
            color = color,
            topLeft = Offset(
                x = size.width * 0.55f,
                y = size.height * 0.23f
            ),
            size = Size(
                width = size.width * 0.40f,
                height = size.height * 0.54f
            )
        )

        drawCircle(
            color = color,
            radius = size.width * 0.16f,
            center = Offset(
                x = size.width / 2f,
                y = size.height / 2f
            )
        )
    }
}

@Composable
private fun SecondaryControls(
    shuffle: Boolean,
    repeat: Boolean,
    onShuffle: () -> Unit,
    onRepeat: () -> Unit
) {
    Row(
        horizontalArrangement =
            Arrangement.spacedBy(8.dp)
    ) {
        SmallControl(
            text =
                stringResource(
                    R.string.shuffle
                ),
            description =
                stringResource(
                    R.string.shuffle_description
                ),
            selected = shuffle,
            onClick = onShuffle
        )

        SmallControl(
            text =
                stringResource(
                    R.string.repeat
                ),
            description =
                stringResource(
                    R.string.repeat_description
                ),
            selected = repeat,
            onClick = onRepeat
        )
    }
}

@Composable
private fun SmallControl(
    text: String,
    description: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val enabledText =
        stringResource(
            R.string.enabled
        )

    val disabledText =
        stringResource(
            R.string.disabled
        )

    val onText =
        stringResource(
            R.string.on
        )

    val offText =
        stringResource(
            R.string.off
        )

    Box(
        modifier = Modifier
            .sizeIn(
                minWidth = 88.dp,
                minHeight = 48.dp
            )
            .clip(
                RoundedCornerShape(9.dp)
            )
            .background(
                if (selected) {
                    MaterialTheme.colorScheme.primary.copy(
                        alpha = 0.12f
                    )
                } else {
                    MaterialTheme.colorScheme.surface
                }
            )
            .border(
                width =
                    if (selected) {
                        2.dp
                    } else {
                        1.dp
                    },
                color =
                    if (selected) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.outline
                    },
                shape =
                    RoundedCornerShape(9.dp)
            )
            .semantics {
                role = Role.Button

                contentDescription =
                    description

                stateDescription =
                    if (selected) {
                        enabledText
                    } else {
                        disabledText
                    }
            }
            .clickable(
                role = Role.Button,
                onClick = onClick
            ),
        contentAlignment =
            Alignment.Center
    ) {
        Text(
            text =
                "$text ${
                    if (selected) {
                        onText
                    } else {
                        offText
                    }
                }",
            style =
                MaterialTheme.typography
                    .labelSmall.copy(
                        fontFamily =
                            FontFamily.Monospace
                    ),
            fontWeight =
                FontWeight.Bold,
            color =
                if (selected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                }
        )
    }
}

@Composable
private fun PlayerLoading(
    windowWidthSizeClass: WindowWidthSizeClass,
    windowHeightSizeClass: WindowHeightSizeClass
) {
    val transition =
        rememberInfiniteTransition(
            label = "loadingPulse"
        )

    val pulse by
    transition.animateFloat(
        initialValue = 0.45f,
        targetValue = 0.9f,
        animationSpec =
            infiniteRepeatable(
                animation =
                    tween(900),
                repeatMode =
                    RepeatMode.Reverse
            ),
        label = "loadingAlpha"
    )

    if (
        windowHeightSizeClass ==
        WindowHeightSizeClass.Compact
    ) {
        LandscapeLoadingPlayer(
            pulse = pulse
        )

        return
    }

    Box(
        modifier =
            Modifier.fillMaxSize(),
        contentAlignment =
            Alignment.Center
    ) {
        when (windowWidthSizeClass) {
            WindowWidthSizeClass.Compact -> {
                CompactLoadingPlayer(
                    pulse = pulse,
                    maxWidthDp = 390
                )
            }

            WindowWidthSizeClass.Medium -> {
                CompactLoadingPlayer(
                    pulse = pulse,
                    maxWidthDp = 520
                )
            }

            WindowWidthSizeClass.Expanded -> {
                WideLoadingPlayer(
                    pulse = pulse
                )
            }

            else -> {
                CompactLoadingPlayer(
                    pulse = pulse,
                    maxWidthDp = 390
                )
            }
        }
    }
}

@Composable
private fun CompactLoadingPlayer(
    pulse: Float,
    maxWidthDp: Int
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .widthIn(
                max = maxWidthDp.dp
            )
            .fillMaxHeight()
            .clip(
                RoundedCornerShape(28.dp)
            )
            .background(
                MaterialTheme.colorScheme.surfaceVariant
            )
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.outline,
                shape =
                    RoundedCornerShape(28.dp)
            )
            .padding(16.dp),
        horizontalAlignment =
            Alignment.CenterHorizontally
    ) {
        LoadingDisplay(
            pulse = pulse,
            modifier =
                Modifier.fillMaxWidth()
        )

        Spacer(
            modifier =
                Modifier.weight(1f)
        )

        LoadingWheel(
            pulse = pulse,
            compactHeight = false
        )

        Spacer(
            modifier =
                Modifier.height(10.dp)
        )

        LoadingSecondaryControls(
            pulse = pulse
        )

        Spacer(
            modifier =
                Modifier.height(8.dp)
        )

        SkeletonBlock(
            modifier = Modifier
                .width(110.dp)
                .height(10.dp),
            pulse = pulse
        )
    }
}

@Composable
private fun LandscapeLoadingPlayer(
    pulse: Float
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .widthIn(max = 900.dp)
            .clip(
                RoundedCornerShape(24.dp)
            )
            .background(
                MaterialTheme.colorScheme.surfaceVariant
            )
            .border(
                width = 2.dp,
                color =
                    MaterialTheme.colorScheme.outline,
                shape =
                    RoundedCornerShape(24.dp)
            )
            .padding(12.dp),
        verticalAlignment =
            Alignment.CenterVertically,
        horizontalArrangement =
            Arrangement.spacedBy(18.dp)
    ) {
        CompactLandscapeLoadingDisplay(
            pulse = pulse,
            modifier =
                Modifier.weight(1.15f)
        )

        Column(
            modifier = Modifier
                .weight(0.85f)
                .fillMaxHeight(),
            horizontalAlignment =
                Alignment.CenterHorizontally,
            verticalArrangement =
                Arrangement.Center
        ) {
            LoadingWheel(
                pulse = pulse,
                compactHeight = true
            )

            Spacer(
                modifier =
                    Modifier.height(6.dp)
            )

            LoadingSecondaryControls(
                pulse = pulse
            )
        }
    }
}

@Composable
private fun WideLoadingPlayer(
    pulse: Float
) {
    Row(
        modifier = Modifier
            .widthIn(
                max = 850.dp
            )
            .fillMaxHeight(0.86f)
            .clip(
                RoundedCornerShape(30.dp)
            )
            .background(
                MaterialTheme.colorScheme.surfaceVariant
            )
            .border(
                width = 2.dp,
                color =
                    MaterialTheme.colorScheme.outline,
                shape =
                    RoundedCornerShape(30.dp)
            )
            .padding(24.dp),
        verticalAlignment =
            Alignment.CenterVertically,
        horizontalArrangement =
            Arrangement.spacedBy(36.dp)
    ) {
        LoadingDisplay(
            pulse = pulse,
            modifier =
                Modifier.weight(1.1f)
        )

        Column(
            modifier =
                Modifier.weight(0.9f),
            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {
            LoadingWheel(
                pulse = pulse,
                compactHeight = false
            )

            Spacer(
                modifier =
                    Modifier.height(18.dp)
            )

            LoadingSecondaryControls(
                pulse = pulse
            )
        }
    }
}

@Composable
private fun LoadingDisplay(
    pulse: Float,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(
                RoundedCornerShape(16.dp)
            )
            .background(
                MaterialTheme.colorScheme.surface
            )
            .border(
                width = 3.dp,
                color =
                    MaterialTheme.colorScheme.outline,
                shape =
                    RoundedCornerShape(16.dp)
            )
            .padding(14.dp),
        horizontalAlignment =
            Alignment.CenterHorizontally
    ) {
        LoadingStatusBar(
            pulse = pulse
        )

        Spacer(
            modifier =
                Modifier.height(8.dp)
        )

        SkeletonBlock(
            modifier =
                Modifier.size(145.dp),
            pulse = pulse,
            cornerRadius = 12
        )

        Spacer(
            modifier =
                Modifier.height(14.dp)
        )

        SkeletonBlock(
            modifier = Modifier
                .width(170.dp)
                .height(18.dp),
            pulse = pulse
        )

        Spacer(
            modifier =
                Modifier.height(7.dp)
        )

        SkeletonBlock(
            modifier = Modifier
                .width(100.dp)
                .height(12.dp),
            pulse = pulse
        )

        Spacer(
            modifier =
                Modifier.height(12.dp)
        )

        SkeletonBlock(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp),
            pulse = pulse
        )

        Spacer(
            modifier =
                Modifier.height(8.dp)
        )

        Row(
            modifier =
                Modifier.fillMaxWidth(),
            horizontalArrangement =
                Arrangement.SpaceBetween
        ) {
            SkeletonBlock(
                modifier = Modifier
                    .width(36.dp)
                    .height(10.dp),
                pulse = pulse
            )

            SkeletonBlock(
                modifier = Modifier
                    .width(36.dp)
                    .height(10.dp),
                pulse = pulse
            )
        }
    }
}

@Composable
private fun CompactLandscapeLoadingDisplay(
    pulse: Float,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(
                RoundedCornerShape(16.dp)
            )
            .background(
                MaterialTheme.colorScheme.surface
            )
            .border(
                width = 3.dp,
                color =
                    MaterialTheme.colorScheme.outline,
                shape =
                    RoundedCornerShape(16.dp)
            )
            .padding(8.dp),
        horizontalAlignment =
            Alignment.CenterHorizontally
    ) {
        LoadingStatusBar(
            pulse = pulse
        )

        Spacer(
            modifier =
                Modifier.height(2.dp)
        )

        SkeletonBlock(
            modifier =
                Modifier.size(88.dp),
            pulse = pulse,
            cornerRadius = 10
        )

        Spacer(
            modifier =
                Modifier.height(5.dp)
        )

        SkeletonBlock(
            modifier = Modifier
                .width(150.dp)
                .height(16.dp),
            pulse = pulse
        )

        Spacer(
            modifier =
                Modifier.height(4.dp)
        )

        SkeletonBlock(
            modifier = Modifier
                .width(90.dp)
                .height(10.dp),
            pulse = pulse
        )

        Spacer(
            modifier =
                Modifier.height(5.dp)
        )

        SkeletonBlock(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp),
            pulse = pulse
        )

        Spacer(
            modifier =
                Modifier.height(4.dp)
        )

        Row(
            modifier =
                Modifier.fillMaxWidth(),
            horizontalArrangement =
                Arrangement.SpaceBetween
        ) {
            SkeletonBlock(
                modifier = Modifier
                    .width(34.dp)
                    .height(9.dp),
                pulse = pulse
            )

            SkeletonBlock(
                modifier = Modifier
                    .width(34.dp)
                    .height(9.dp),
                pulse = pulse
            )
        }
    }
}

@Composable
private fun LoadingStatusBar(
    pulse: Float
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp),
        verticalAlignment =
            Alignment.CenterVertically
    ) {
        SkeletonBlock(
            modifier = Modifier
                .width(42.dp)
                .height(10.dp),
            pulse = pulse
        )

        Spacer(
            modifier =
                Modifier.weight(1f)
        )

        SkeletonBlock(
            modifier = Modifier
                .width(40.dp)
                .height(28.dp),
            pulse = pulse
        )

        Spacer(
            modifier =
                Modifier.width(2.dp)
        )

        SkeletonBlock(
            modifier = Modifier
                .width(52.dp)
                .height(28.dp),
            pulse = pulse
        )

        Spacer(
            modifier =
                Modifier.width(5.dp)
        )

        SkeletonBlock(
            modifier = Modifier
                .width(26.dp)
                .height(12.dp),
            pulse = pulse
        )
    }
}

@Composable
private fun LoadingWheel(
    pulse: Float,
    compactHeight: Boolean
) {
    val maxSize =
        if (compactHeight) {
            182.dp
        } else {
            250.dp
        }

    val fraction =
        if (compactHeight) {
            0.82f
        } else {
            0.76f
        }

    Box(
        modifier = Modifier
            .fillMaxWidth(fraction)
            .widthIn(
                max = maxSize
            )
            .aspectRatio(1f)
            .clip(CircleShape)
            .background(
                MaterialTheme.colorScheme.surface
            )
            .border(
                width = 2.dp,
                color =
                    MaterialTheme.colorScheme.outline,
                shape =
                    CircleShape
            ),
        contentAlignment =
            Alignment.Center
    ) {
        SkeletonBlock(
            modifier = Modifier
                .align(
                    Alignment.TopCenter
                )
                .padding(top = 18.dp)
                .width(48.dp)
                .height(10.dp),
            pulse = pulse
        )

        SkeletonBlock(
            modifier = Modifier
                .align(
                    Alignment.CenterStart
                )
                .padding(start = 14.dp)
                .width(38.dp)
                .height(10.dp),
            pulse = pulse
        )

        SkeletonBlock(
            modifier = Modifier
                .align(
                    Alignment.CenterEnd
                )
                .padding(end = 14.dp)
                .width(38.dp)
                .height(10.dp),
            pulse = pulse
        )

        SkeletonBlock(
            modifier = Modifier
                .align(
                    Alignment.BottomCenter
                )
                .padding(bottom = 18.dp)
                .width(48.dp)
                .height(10.dp),
            pulse = pulse
        )

        Box(
            modifier = Modifier
                .fillMaxSize(0.38f)
                .clip(CircleShape)
                .background(
                    MaterialTheme.colorScheme.surfaceVariant
                )
                .border(
                    width = 2.dp,
                    color =
                        MaterialTheme.colorScheme.outline,
                    shape =
                        CircleShape
                )
                .alpha(pulse)
        )
    }
}

@Composable
private fun LoadingSecondaryControls(
    pulse: Float
) {
    Row(
        horizontalArrangement =
            Arrangement.spacedBy(8.dp)
    ) {
        SkeletonBlock(
            modifier = Modifier
                .width(88.dp)
                .height(48.dp),
            pulse = pulse,
            cornerRadius = 9
        )

        SkeletonBlock(
            modifier = Modifier
                .width(88.dp)
                .height(48.dp),
            pulse = pulse,
            cornerRadius = 9
        )
    }
}

@Composable
private fun SkeletonBlock(
    modifier: Modifier,
    pulse: Float,
    cornerRadius: Int = 5
) {
    Box(
        modifier = modifier
            .alpha(pulse)
            .clip(
                RoundedCornerShape(
                    cornerRadius.dp
                )
            )
            .background(
                MaterialTheme.colorScheme.onSurface.copy(
                    alpha = 0.16f
                )
            )
    )
}

@Composable
private fun PlayerEmpty(
    windowWidthSizeClass: WindowWidthSizeClass,
    windowHeightSizeClass: WindowHeightSizeClass,
    onOpenPlaylist: () -> Unit
) {
    if (
        windowHeightSizeClass ==
        WindowHeightSizeClass.Compact
    ) {
        LandscapeEmptyPlayer(
            onOpenPlaylist =
                onOpenPlaylist
        )

        return
    }

    Box(
        modifier =
            Modifier.fillMaxSize(),
        contentAlignment =
            Alignment.Center
    ) {
        when (windowWidthSizeClass) {
            WindowWidthSizeClass.Compact -> {
                CompactEmptyPlayer(
                    maxWidthDp = 390,
                    onOpenPlaylist =
                        onOpenPlaylist
                )
            }

            WindowWidthSizeClass.Medium -> {
                CompactEmptyPlayer(
                    maxWidthDp = 520,
                    onOpenPlaylist =
                        onOpenPlaylist
                )
            }

            WindowWidthSizeClass.Expanded -> {
                WideEmptyPlayer(
                    onOpenPlaylist =
                        onOpenPlaylist
                )
            }

            else -> {
                CompactEmptyPlayer(
                    maxWidthDp = 390,
                    onOpenPlaylist =
                        onOpenPlaylist
                )
            }
        }
    }
}

@Composable
private fun CompactEmptyPlayer(
    maxWidthDp: Int,
    onOpenPlaylist: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .widthIn(
                max = maxWidthDp.dp
            )
            .fillMaxHeight()
            .clip(
                RoundedCornerShape(28.dp)
            )
            .background(
                MaterialTheme.colorScheme.surfaceVariant
            )
            .border(
                width = 2.dp,
                color =
                    MaterialTheme.colorScheme.outline,
                shape =
                    RoundedCornerShape(28.dp)
            )
            .padding(16.dp),
        horizontalAlignment =
            Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(
                    RoundedCornerShape(16.dp)
                )
                .background(
                    MaterialTheme.colorScheme.surface
                )
                .border(
                    width = 3.dp,
                    color =
                        MaterialTheme.colorScheme.outline,
                    shape =
                        RoundedCornerShape(16.dp)
                ),
            contentAlignment =
                Alignment.Center
        ) {
            EmptyPlayerContent(
                onOpenPlaylist =
                    onOpenPlaylist
            )
        }
    }
}

@Composable
private fun LandscapeEmptyPlayer(
    onOpenPlaylist: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .widthIn(max = 900.dp)
            .clip(
                RoundedCornerShape(24.dp)
            )
            .background(
                MaterialTheme.colorScheme.surfaceVariant
            )
            .border(
                width = 2.dp,
                color =
                    MaterialTheme.colorScheme.outline,
                shape =
                    RoundedCornerShape(24.dp)
            )
            .padding(16.dp),
        verticalAlignment =
            Alignment.CenterVertically,
        horizontalArrangement =
            Arrangement.spacedBy(24.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clip(
                    RoundedCornerShape(16.dp)
                )
                .background(
                    MaterialTheme.colorScheme.surface
                )
                .border(
                    width = 3.dp,
                    color =
                        MaterialTheme.colorScheme.outline,
                    shape =
                        RoundedCornerShape(16.dp)
                ),
            contentAlignment =
                Alignment.Center
        ) {
            KittyFace(
                size = 110.dp
            )
        }

        Column(
            modifier =
                Modifier.weight(1f),
            verticalArrangement =
                Arrangement.Center,
            horizontalAlignment =
                Alignment.Start
        ) {
            Text(
                text =
                    stringResource(
                        R.string.nothing_playing
                    ),
                style =
                    MaterialTheme.typography
                        .headlineMedium.copy(
                            fontFamily =
                                FontFamily.Monospace
                        ),
                color =
                    MaterialTheme.colorScheme.onSurface
            )

            Spacer(
                modifier =
                    Modifier.height(6.dp)
            )

            Text(
                text =
                    stringResource(
                        R.string.choose_track_long
                    ),
                style =
                    MaterialTheme.typography
                        .bodyMedium.copy(
                            fontFamily =
                                FontFamily.Monospace
                        ),
                color =
                    MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(
                modifier =
                    Modifier.height(14.dp)
            )

            EmptyPlaylistButton(
                onOpenPlaylist =
                    onOpenPlaylist
            )
        }
    }
}

@Composable
private fun WideEmptyPlayer(
    onOpenPlaylist: () -> Unit
) {
    Row(
        modifier = Modifier
            .widthIn(
                max = 850.dp
            )
            .fillMaxHeight(0.72f)
            .clip(
                RoundedCornerShape(30.dp)
            )
            .background(
                MaterialTheme.colorScheme.surfaceVariant
            )
            .border(
                width = 2.dp,
                color =
                    MaterialTheme.colorScheme.outline,
                shape =
                    RoundedCornerShape(30.dp)
            )
            .padding(24.dp),
        verticalAlignment =
            Alignment.CenterVertically,
        horizontalArrangement =
            Arrangement.spacedBy(32.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clip(
                    RoundedCornerShape(16.dp)
                )
                .background(
                    MaterialTheme.colorScheme.surface
                )
                .border(
                    width = 3.dp,
                    color =
                        MaterialTheme.colorScheme.outline,
                    shape =
                        RoundedCornerShape(16.dp)
                ),
            contentAlignment =
                Alignment.Center
        ) {
            KittyFace()
        }

        Column(
            modifier =
                Modifier.weight(1f),
            horizontalAlignment =
                Alignment.Start,
            verticalArrangement =
                Arrangement.Center
        ) {
            Text(
                text =
                    stringResource(
                        R.string.nothing_playing
                    ),
                style =
                    MaterialTheme.typography
                        .headlineMedium.copy(
                            fontFamily =
                                FontFamily.Monospace
                        ),
                color =
                    MaterialTheme.colorScheme.onSurface
            )

            Spacer(
                modifier =
                    Modifier.height(8.dp)
            )

            Text(
                text =
                    stringResource(
                        R.string.choose_track_long
                    ),
                style =
                    MaterialTheme.typography
                        .bodyLarge.copy(
                            fontFamily =
                                FontFamily.Monospace
                        ),
                color =
                    MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(
                modifier =
                    Modifier.height(24.dp)
            )

            EmptyPlaylistButton(
                onOpenPlaylist =
                    onOpenPlaylist
            )
        }
    }
}

@Composable
private fun EmptyPlayerContent(
    onOpenPlaylist: () -> Unit
) {
    Column(
        modifier =
            Modifier.padding(24.dp),
        horizontalAlignment =
            Alignment.CenterHorizontally
    ) {
        KittyFace()

        Spacer(
            modifier =
                Modifier.height(14.dp)
        )

        Text(
            text =
                stringResource(
                    R.string.nothing_playing
                ),
            style =
                MaterialTheme.typography
                    .titleMedium.copy(
                        fontFamily =
                            FontFamily.Monospace
                    ),
            color =
                MaterialTheme.colorScheme.onSurface,
            textAlign =
                TextAlign.Center
        )

        Spacer(
            modifier =
                Modifier.height(6.dp)
        )

        Text(
            text =
                stringResource(
                    R.string.choose_track
                ),
            style =
                MaterialTheme.typography
                    .bodyMedium.copy(
                        fontFamily =
                            FontFamily.Monospace
                    ),
            color =
                MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign =
                TextAlign.Center
        )

        Spacer(
            modifier =
                Modifier.height(20.dp)
        )

        EmptyPlaylistButton(
            onOpenPlaylist =
                onOpenPlaylist
        )
    }
}

@Composable
private fun EmptyPlaylistButton(
    onOpenPlaylist: () -> Unit
) {
    val description =
        stringResource(
            R.string.open_playlist
        )

    Box(
        modifier = Modifier
            .sizeIn(
                minWidth = 150.dp,
                minHeight = 48.dp
            )
            .clip(
                RoundedCornerShape(8.dp)
            )
            .background(
                MaterialTheme.colorScheme.primary
            )
            .semantics {
                role = Role.Button

                contentDescription =
                    description
            }
            .clickable(
                role = Role.Button,
                onClick =
                    onOpenPlaylist
            ),
        contentAlignment =
            Alignment.Center
    ) {
        Text(
            text =
                stringResource(
                    R.string.playlist_action
                ),
            style =
                MaterialTheme.typography
                    .labelLarge.copy(
                        fontFamily =
                            FontFamily.Monospace
                    ),
            color =
                MaterialTheme.colorScheme.onPrimary
        )
    }
}

private fun formatTime(
    milliseconds: Long
): String {
    if (milliseconds <= 0L) {
        return "00:00"
    }

    val totalSeconds =
        milliseconds / 1000

    val minutes =
        totalSeconds / 60

    val seconds =
        totalSeconds % 60

    return "%02d:%02d".format(
        minutes,
        seconds
    )
}

private val previewTrack =
    Track(
        id = 1,
        title = "Lonely Day",
        artist = "System of a Down",
        duration = "02:47",
        audioResId = 0,
        coverResId = 0
    )

@Preview(
    name = "Phone Portrait",
    showBackground = true,
    locale = "ru",
    widthDp = 390,
    heightDp = 800
)
@Composable
private fun PhonePortraitPreview() {
    MegaX3PlayerTheme(
        darkTheme = false
    ) {
        PlayerScreen(
            state =
                PlayerUiState.Content(
                    track = previewTrack,
                    isPlaying = true,
                    positionMs = 80000L,
                    durationMs = 167000L,
                    shuffle = true,
                    repeat = false
                ),
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

@Preview(
    name = "Phone Landscape",
    showBackground = true,
    locale = "ru",
    widthDp = 800,
    heightDp = 360
)
@Composable
private fun PhoneLandscapePreview() {
    MegaX3PlayerTheme(
        darkTheme = false
    ) {
        PlayerScreen(
            state =
                PlayerUiState.Content(
                    track = previewTrack,
                    isPlaying = true,
                    positionMs = 80000L,
                    durationMs = 167000L,
                    shuffle = true,
                    repeat = false
                ),
            darkTheme = false,
            currentLanguage = "ru",
            windowWidthSizeClass =
                WindowWidthSizeClass.Medium,
            windowHeightSizeClass =
                WindowHeightSizeClass.Compact,
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
    name = "Phone Landscape Dark",
    showBackground = true,
    locale = "en",
    uiMode =
        Configuration.UI_MODE_NIGHT_YES,
    widthDp = 800,
    heightDp = 360
)
@Composable
private fun PhoneLandscapeDarkPreview() {
    MegaX3PlayerTheme(
        darkTheme = true
    ) {
        PlayerScreen(
            state =
                PlayerUiState.Content(
                    track = previewTrack,
                    isPlaying = false,
                    positionMs = 60000L,
                    durationMs = 167000L,
                    shuffle = false,
                    repeat = true
                ),
            darkTheme = true,
            currentLanguage = "en",
            windowWidthSizeClass =
                WindowWidthSizeClass.Medium,
            windowHeightSizeClass =
                WindowHeightSizeClass.Compact,
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
    name = "Loading Landscape",
    showBackground = true,
    widthDp = 800,
    heightDp = 360
)
@Composable
private fun LoadingLandscapePreview() {
    MegaX3PlayerTheme(
        darkTheme = false
    ) {
        PlayerScreen(
            state =
                PlayerUiState.Loading,
            darkTheme = false,
            currentLanguage = "en",
            windowWidthSizeClass =
                WindowWidthSizeClass.Medium,
            windowHeightSizeClass =
                WindowHeightSizeClass.Compact,
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
    name = "Empty Landscape",
    showBackground = true,
    locale = "ru",
    widthDp = 800,
    heightDp = 360
)
@Composable
private fun EmptyLandscapePreview() {
    MegaX3PlayerTheme(
        darkTheme = false
    ) {
        PlayerScreen(
            state =
                PlayerUiState.Empty,
            darkTheme = false,
            currentLanguage = "ru",
            windowWidthSizeClass =
                WindowWidthSizeClass.Medium,
            windowHeightSizeClass =
                WindowHeightSizeClass.Compact,
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
    name = "Tablet",
    showBackground = true,
    locale = "en",
    widthDp = 900,
    heightDp = 600
)
@Composable
private fun TabletPreview() {
    MegaX3PlayerTheme(
        darkTheme = false
    ) {
        PlayerScreen(
            state =
                PlayerUiState.Content(
                    track = previewTrack,
                    isPlaying = true,
                    positionMs = 80000L,
                    durationMs = 167000L,
                    shuffle = true,
                    repeat = true
                ),
            darkTheme = false,
            currentLanguage = "en",
            windowWidthSizeClass =
                WindowWidthSizeClass.Expanded,
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