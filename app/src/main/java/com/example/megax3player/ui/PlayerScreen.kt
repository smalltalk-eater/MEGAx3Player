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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
    windowWidthSizeClass: WindowWidthSizeClass,
    onDarkThemeChange: (Boolean) -> Unit,
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
                        windowWidthSizeClass = windowWidthSizeClass
                    )
                }

                PlayerScreenMode.EMPTY -> {
                    PlayerEmpty(
                        windowWidthSizeClass = windowWidthSizeClass,
                        onOpenPlaylist = onOpenPlaylist
                    )
                }

                PlayerScreenMode.CONTENT -> {
                    val content = state as? PlayerUiState.Content

                    if (content != null) {
                        PlayerDevice(
                            state = content,
                            darkTheme = darkTheme,
                            windowWidthSizeClass = windowWidthSizeClass,
                            onDarkThemeChange = onDarkThemeChange,
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
    windowWidthSizeClass: WindowWidthSizeClass,
    onDarkThemeChange: (Boolean) -> Unit,
    onOpenPlaylist: () -> Unit,
    onPlayPause: () -> Unit,
    onNext: () -> Unit,
    onPrevious: () -> Unit,
    onSeek: (Float) -> Unit,
    onShuffle: () -> Unit,
    onRepeat: () -> Unit
) {
    when (windowWidthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            CompactPlayer(
                state = state,
                darkTheme = darkTheme,
                maxWidthDp = 390,
                onDarkThemeChange = onDarkThemeChange,
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
                maxWidthDp = 520,
                onDarkThemeChange = onDarkThemeChange,
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
                onDarkThemeChange = onDarkThemeChange,
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
                maxWidthDp = 390,
                onDarkThemeChange = onDarkThemeChange,
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
    maxWidthDp: Int,
    onDarkThemeChange: (Boolean) -> Unit,
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
            .clip(RoundedCornerShape(28.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(28.dp)
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PlayerDisplay(
            state = state,
            darkTheme = darkTheme,
            onDarkThemeChange = onDarkThemeChange,
            onSeek = onSeek
        )

        Spacer(
            modifier = Modifier.weight(1f)
        )

        PlayerWheel(
            isPlaying = state.isPlaying,
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
            style = MaterialTheme.typography.labelSmall.copy(
                fontFamily = FontFamily.Monospace,
                letterSpacing = 2.sp
            ),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun WidePlayer(
    state: PlayerUiState.Content,
    darkTheme: Boolean,
    onDarkThemeChange: (Boolean) -> Unit,
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
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(30.dp)
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
                onDarkThemeChange = onDarkThemeChange,
                onSeek = onSeek
            )
        }

        Column(
            modifier = Modifier.weight(0.9f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PlayerWheel(
                isPlaying = state.isPlaying,
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
    onDarkThemeChange: (Boolean) -> Unit,
    onSeek: (Float) -> Unit
) {
    val progress = if (state.durationMs > 0L) {
        state.positionMs.toFloat() / state.durationMs.toFloat()
    } else {
        0f
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface)
            .border(
                width = 3.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(14.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PlayerStatusBar(
            darkTheme = darkTheme,
            onDarkThemeChange = onDarkThemeChange
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        TrackCover(
            coverResId = state.track.coverResId,
            title = state.track.title
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        Text(
            text = state.track.title,
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge.copy(
                fontFamily = FontFamily.Monospace
            ),
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(
            modifier = Modifier.height(3.dp)
        )

        Text(
            text = state.track.artist,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontFamily = FontFamily.Monospace
            ),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(
            modifier = Modifier.height(8.dp)
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
    onDarkThemeChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "MEGA",
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.labelSmall.copy(
                fontFamily = FontFamily.Monospace
            ),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Box(
            modifier = Modifier
                .sizeIn(
                    minWidth = 56.dp,
                    minHeight = 48.dp
                )
                .clip(RoundedCornerShape(8.dp))
                .semantics {
                    role = Role.Button
                    contentDescription = "Переключить тему"
                    stateDescription = if (darkTheme) {
                        "Тёмная тема включена"
                    } else {
                        "Светлая тема включена"
                    }
                }
                .clickable(
                    role = Role.Button
                ) {
                    onDarkThemeChange(!darkTheme)
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (darkTheme) {
                    "DARK"
                } else {
                    "LIGHT"
                },
                style = MaterialTheme.typography.labelSmall.copy(
                    fontFamily = FontFamily.Monospace
                ),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(
            modifier = Modifier.width(6.dp)
        )

        BatteryIndicator()
    }
}

@Composable
private fun BatteryIndicator() {
    Box(
        modifier = Modifier
            .width(26.dp)
            .height(12.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                shape = RoundedCornerShape(2.dp)
            )
            .padding(2.dp)
            .semantics {
                contentDescription = "Индикатор батареи, 72 процента"
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
    title: String
) {
    Box(
        modifier = Modifier
            .size(145.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .semantics {
                contentDescription = "Обложка композиции $title"
            },
        contentAlignment = Alignment.Center
    ) {
        if (coverResId != 0) {
            Image(
                painter = painterResource(coverResId),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        } else {
            KittyFace()
        }
    }
}

@Composable
private fun KittyFace() {
    val faceColor = MaterialTheme.colorScheme.surfaceVariant
    val outlineColor = MaterialTheme.colorScheme.onSurface
    val bowColor = MaterialTheme.colorScheme.primary
    val noseColor = MaterialTheme.colorScheme.secondary

    Canvas(
        modifier = Modifier.size(105.dp)
    ) {
        val w = size.width
        val h = size.height

        val leftEar = Path().apply {
            moveTo(w * 0.22f, h * 0.37f)
            lineTo(w * 0.29f, h * 0.12f)
            lineTo(w * 0.43f, h * 0.30f)
            close()
        }

        val rightEar = Path().apply {
            moveTo(w * 0.57f, h * 0.30f)
            lineTo(w * 0.72f, h * 0.12f)
            lineTo(w * 0.79f, h * 0.37f)
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
            cornerRadius = CornerRadius(
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

    LaunchedEffect(
        progress,
        isSeeking
    ) {
        if (!isSeeking) {
            sliderValue = progress
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Slider(
            value = sliderValue.coerceIn(
                minimumValue = 0f,
                maximumValue = 1f
            ),
            onValueChange = {
                isSeeking = true
                sliderValue = it
            },
            onValueChangeFinished = {
                onProgressChange(sliderValue)
                isSeeking = false
            },
            valueRange = 0f..1f,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .semantics {
                    contentDescription = "Позиция воспроизведения"
                },
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.primary,
                activeTrackColor = MaterialTheme.colorScheme.primary,
                inactiveTrackColor = MaterialTheme.colorScheme.outline
            )
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TimeText(
                text = if (
                    isSeeking &&
                    durationMs > 0L
                ) {
                    formatTime(
                        milliseconds =
                            (durationMs * sliderValue).toLong()
                    )
                } else {
                    formatTime(
                        milliseconds = positionMs
                    )
                }
            )

            TimeText(
                text = formatTime(
                    milliseconds = durationMs
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
        style = MaterialTheme.typography.labelSmall.copy(
            fontFamily = FontFamily.Monospace
        ),
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Composable
private fun PlayerWheel(
    isPlaying: Boolean,
    onOpenPlaylist: () -> Unit,
    onPlayPause: () -> Unit,
    onPrevious: () -> Unit,
    onNext: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(0.76f)
            .widthIn(max = 250.dp)
            .aspectRatio(1f)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surface)
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = CircleShape
            )
    ) {
        WheelButton(
            text = "MENU",
            description = "Открыть плейлист",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 8.dp),
            onClick = onOpenPlaylist
        )

        WheelButton(
            text = "PREV",
            description = "Предыдущий трек",
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 4.dp),
            onClick = onPrevious
        )

        WheelButton(
            text = "NEXT",
            description = "Следующий трек",
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 4.dp),
            onClick = onNext
        )

        WheelButton(
            text = if (isPlaying) {
                "PAUSE"
            } else {
                "PLAY"
            },
            description = if (isPlaying) {
                "Поставить воспроизведение на паузу"
            } else {
                "Начать воспроизведение"
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp),
            onClick = onPlayPause
        )

        Box(
            modifier = Modifier
                .fillMaxSize(0.38f)
                .align(Alignment.Center)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = CircleShape
                )
                .semantics {
                    role = Role.Button
                    contentDescription = if (isPlaying) {
                        "Поставить воспроизведение на паузу"
                    } else {
                        "Начать воспроизведение"
                    }
                }
                .clickable(
                    role = Role.Button,
                    onClick = onPlayPause
                ),
            contentAlignment = Alignment.Center
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
            .clip(RoundedCornerShape(8.dp))
            .semantics {
                role = Role.Button
                contentDescription = description
            }
            .clickable(
                role = Role.Button,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall.copy(
                fontFamily = FontFamily.Monospace
            ),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun BowMark() {
    val color = MaterialTheme.colorScheme.primary

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
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SmallControl(
            text = "SHUFFLE",
            description = "Случайный порядок воспроизведения",
            selected = shuffle,
            onClick = onShuffle
        )

        SmallControl(
            text = "REPEAT",
            description = "Повтор текущей композиции",
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
    Box(
        modifier = Modifier
            .sizeIn(
                minWidth = 88.dp,
                minHeight = 48.dp
            )
            .clip(RoundedCornerShape(9.dp))
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
                width = if (selected) {
                    2.dp
                } else {
                    1.dp
                },
                color = if (selected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.outline
                },
                shape = RoundedCornerShape(9.dp)
            )
            .semantics {
                role = Role.Button
                contentDescription = description
                stateDescription = if (selected) {
                    "Включено"
                } else {
                    "Выключено"
                }
            }
            .clickable(
                role = Role.Button,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (selected) {
                "$text ON"
            } else {
                "$text OFF"
            },
            style = MaterialTheme.typography.labelSmall.copy(
                fontFamily = FontFamily.Monospace
            ),
            fontWeight = FontWeight.Bold,
            color = if (selected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.onSurfaceVariant
            }
        )
    }
}

@Composable
private fun PlayerLoading(
    windowWidthSizeClass: WindowWidthSizeClass
) {
    val transition = rememberInfiniteTransition(
        label = "loadingPulse"
    )

    val pulse by transition.animateFloat(
        initialValue = 0.45f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 900
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "loadingAlpha"
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
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
            .widthIn(max = maxWidthDp.dp)
            .fillMaxHeight()
            .clip(RoundedCornerShape(28.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(28.dp)
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LoadingDisplay(
            pulse = pulse,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.weight(1f)
        )

        LoadingWheel(
            pulse = pulse
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        LoadingSecondaryControls(
            pulse = pulse
        )

        Spacer(
            modifier = Modifier.height(8.dp)
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
private fun WideLoadingPlayer(
    pulse: Float
) {
    Row(
        modifier = Modifier
            .widthIn(max = 850.dp)
            .fillMaxHeight(0.86f)
            .clip(RoundedCornerShape(30.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(30.dp)
            )
            .padding(24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(36.dp)
    ) {
        LoadingDisplay(
            pulse = pulse,
            modifier = Modifier.weight(1.1f)
        )

        Column(
            modifier = Modifier.weight(0.9f),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoadingWheel(
                pulse = pulse
            )

            Spacer(
                modifier = Modifier.height(18.dp)
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
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface)
            .border(
                width = 3.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(14.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SkeletonBlock(
                modifier = Modifier
                    .width(42.dp)
                    .height(10.dp),
                pulse = pulse
            )

            Spacer(
                modifier = Modifier.weight(1f)
            )

            SkeletonBlock(
                modifier = Modifier
                    .width(56.dp)
                    .height(32.dp),
                pulse = pulse
            )

            Spacer(
                modifier = Modifier.width(8.dp)
            )

            SkeletonBlock(
                modifier = Modifier
                    .width(26.dp)
                    .height(12.dp),
                pulse = pulse
            )
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        SkeletonBlock(
            modifier = Modifier.size(145.dp),
            pulse = pulse,
            cornerRadius = 12
        )

        Spacer(
            modifier = Modifier.height(14.dp)
        )

        SkeletonBlock(
            modifier = Modifier
                .width(170.dp)
                .height(18.dp),
            pulse = pulse
        )

        Spacer(
            modifier = Modifier.height(7.dp)
        )

        SkeletonBlock(
            modifier = Modifier
                .width(100.dp)
                .height(12.dp),
            pulse = pulse
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        SkeletonBlock(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp),
            pulse = pulse
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
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
private fun LoadingWheel(
    pulse: Float
) {
    Box(
        modifier = Modifier
            .fillMaxWidth(0.76f)
            .widthIn(max = 250.dp)
            .aspectRatio(1f)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.surface)
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        SkeletonBlock(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 20.dp)
                .width(48.dp)
                .height(10.dp),
            pulse = pulse
        )

        SkeletonBlock(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 16.dp)
                .width(38.dp)
                .height(10.dp),
            pulse = pulse
        )

        SkeletonBlock(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp)
                .width(38.dp)
                .height(10.dp),
            pulse = pulse
        )

        SkeletonBlock(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp)
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
                    color = MaterialTheme.colorScheme.outline,
                    shape = CircleShape
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
        horizontalArrangement = Arrangement.spacedBy(8.dp)
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
    onOpenPlaylist: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (windowWidthSizeClass) {
            WindowWidthSizeClass.Compact -> {
                CompactEmptyPlayer(
                    maxWidthDp = 390,
                    onOpenPlaylist = onOpenPlaylist
                )
            }

            WindowWidthSizeClass.Medium -> {
                CompactEmptyPlayer(
                    maxWidthDp = 520,
                    onOpenPlaylist = onOpenPlaylist
                )
            }

            WindowWidthSizeClass.Expanded -> {
                WideEmptyPlayer(
                    onOpenPlaylist = onOpenPlaylist
                )
            }

            else -> {
                CompactEmptyPlayer(
                    maxWidthDp = 390,
                    onOpenPlaylist = onOpenPlaylist
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
            .widthIn(max = maxWidthDp.dp)
            .fillMaxHeight()
            .clip(RoundedCornerShape(28.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(28.dp)
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.surface)
                .border(
                    width = 3.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = RoundedCornerShape(16.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            EmptyPlayerContent(
                onOpenPlaylist = onOpenPlaylist
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
            .widthIn(max = 850.dp)
            .fillMaxHeight(0.72f)
            .clip(RoundedCornerShape(30.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(30.dp)
            )
            .padding(24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.surface)
                .border(
                    width = 3.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = RoundedCornerShape(16.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            KittyFace()
        }

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Ничего не играет",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontFamily = FontFamily.Monospace
                ),
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Выберите композицию в плейлисте, чтобы начать воспроизведение.",
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontFamily = FontFamily.Monospace
                ),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(
                modifier = Modifier.height(24.dp)
            )

            EmptyPlaylistButton(
                onOpenPlaylist = onOpenPlaylist
            )
        }
    }
}

@Composable
private fun EmptyPlayerContent(
    onOpenPlaylist: () -> Unit
) {
    Column(
        modifier = Modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        KittyFace()

        Spacer(
            modifier = Modifier.height(14.dp)
        )

        Text(
            text = "Ничего не играет",
            style = MaterialTheme.typography.titleMedium.copy(
                fontFamily = FontFamily.Monospace
            ),
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )

        Spacer(
            modifier = Modifier.height(6.dp)
        )

        Text(
            text = "Выберите композицию",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontFamily = FontFamily.Monospace
            ),
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        EmptyPlaylistButton(
            onOpenPlaylist = onOpenPlaylist
        )
    }
}

@Composable
private fun EmptyPlaylistButton(
    onOpenPlaylist: () -> Unit
) {
    Box(
        modifier = Modifier
            .sizeIn(
                minWidth = 150.dp,
                minHeight = 48.dp
            )
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.primary)
            .semantics {
                role = Role.Button
                contentDescription = "Открыть плейлист"
            }
            .clickable(
                role = Role.Button,
                onClick = onOpenPlaylist
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "PLAYLIST",
            style = MaterialTheme.typography.labelLarge.copy(
                fontFamily = FontFamily.Monospace
            ),
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

private fun formatTime(
    milliseconds: Long
): String {
    if (milliseconds <= 0L) {
        return "00:00"
    }

    val totalSeconds = milliseconds / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60

    return "%02d:%02d".format(
        minutes,
        seconds
    )
}

private val previewTrack = Track(
    id = 1,
    title = "Lonely Day",
    artist = "System of a Down",
    duration = "02:47",
    audioResId = 0,
    coverResId = 0
)

@Preview(
    name = "Player Light",
    showBackground = true,
    widthDp = 390,
    heightDp = 800
)
@Composable
private fun PlayerLightPreview() {
    MegaX3PlayerTheme(
        darkTheme = false
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
            darkTheme = false,
            windowWidthSizeClass = WindowWidthSizeClass.Compact,
            onDarkThemeChange = {},
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
    name = "Player Dark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    widthDp = 390,
    heightDp = 800
)
@Composable
private fun PlayerDarkPreview() {
    MegaX3PlayerTheme(
        darkTheme = true
    ) {
        PlayerScreen(
            state = PlayerUiState.Content(
                track = previewTrack,
                isPlaying = false,
                positionMs = 80000L,
                durationMs = 167000L,
                shuffle = false,
                repeat = true
            ),
            darkTheme = true,
            windowWidthSizeClass = WindowWidthSizeClass.Compact,
            onDarkThemeChange = {},
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
            windowWidthSizeClass = WindowWidthSizeClass.Compact,
            onDarkThemeChange = {},
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
    name = "Player Loading Dark",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    widthDp = 390,
    heightDp = 800
)
@Composable
private fun PlayerLoadingDarkPreview() {
    MegaX3PlayerTheme(
        darkTheme = true
    ) {
        PlayerScreen(
            state = PlayerUiState.Loading,
            darkTheme = true,
            windowWidthSizeClass = WindowWidthSizeClass.Compact,
            onDarkThemeChange = {},
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
            windowWidthSizeClass = WindowWidthSizeClass.Compact,
            onDarkThemeChange = {},
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
    name = "Player Medium",
    showBackground = true,
    widthDp = 700,
    heightDp = 900
)
@Composable
private fun PlayerMediumPreview() {
    MegaX3PlayerTheme(
        darkTheme = false
    ) {
        PlayerScreen(
            state = PlayerUiState.Content(
                track = previewTrack,
                isPlaying = true,
                positionMs = 80000L,
                durationMs = 167000L
            ),
            darkTheme = false,
            windowWidthSizeClass = WindowWidthSizeClass.Medium,
            onDarkThemeChange = {},
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
    name = "Player Tablet",
    showBackground = true,
    widthDp = 900,
    heightDp = 600
)
@Composable
private fun PlayerTabletPreview() {
    MegaX3PlayerTheme(
        darkTheme = false
    ) {
        PlayerScreen(
            state = PlayerUiState.Content(
                track = previewTrack,
                isPlaying = true,
                positionMs = 80000L,
                durationMs = 167000L,
                shuffle = true,
                repeat = true
            ),
            darkTheme = false,
            windowWidthSizeClass = WindowWidthSizeClass.Expanded,
            onDarkThemeChange = {},
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
    name = "Player Empty Tablet",
    showBackground = true,
    widthDp = 900,
    heightDp = 600
)
@Composable
private fun PlayerEmptyTabletPreview() {
    MegaX3PlayerTheme(
        darkTheme = false
    ) {
        PlayerScreen(
            state = PlayerUiState.Empty,
            darkTheme = false,
            windowWidthSizeClass = WindowWidthSizeClass.Expanded,
            onDarkThemeChange = {},
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