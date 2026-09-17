package com.example.megax3player.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
                PlayerScreenMode.LOADING -> PlayerLoading()

                PlayerScreenMode.EMPTY -> PlayerEmpty(
                    onOpenPlaylist = onOpenPlaylist
                )

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
    if (windowWidthSizeClass == WindowWidthSizeClass.Compact) {
        CompactPlayer(
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
    } else {
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
}

@Composable
private fun CompactPlayer(
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
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .widthIn(max = 390.dp)
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

        Spacer(Modifier.weight(1f))

        PlayerWheel(
            isPlaying = state.isPlaying,
            onOpenPlaylist = onOpenPlaylist,
            onPlayPause = onPlayPause,
            onPrevious = onPrevious,
            onNext = onNext
        )

        Spacer(Modifier.height(14.dp))

        SecondaryControls(
            shuffle = state.shuffle,
            repeat = state.repeat,
            onShuffle = onShuffle,
            onRepeat = onRepeat
        )

        Spacer(Modifier.height(10.dp))

        Text(
            text = "MEGAx3Player",
            fontSize = 9.sp,
            fontFamily = FontFamily.Monospace,
            letterSpacing = 2.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
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

            Spacer(Modifier.height(18.dp))

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

        Spacer(Modifier.height(6.dp))

        TrackCover(
            coverResId = state.track.coverResId
        )

        Spacer(Modifier.height(8.dp))

        Text(
            text = state.track.title,
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            fontSize = 17.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(Modifier.height(2.dp))

        Text(
            text = state.track.artist,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 11.sp,
            fontFamily = FontFamily.Monospace,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )

        Spacer(Modifier.height(7.dp))

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
            fontSize = 9.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )

        Text(
            text = if (darkTheme) "DARK" else "LIGHT",
            modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .clickable {
                    onDarkThemeChange(!darkTheme)
                }
                .padding(horizontal = 7.dp, vertical = 4.dp),
            fontSize = 8.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(Modifier.width(8.dp))

        BatteryIndicator()
    }
}

@Composable
private fun BatteryIndicator() {
    Box(
        modifier = Modifier
            .width(24.dp)
            .height(11.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                shape = RoundedCornerShape(2.dp)
            )
            .padding(2.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.72f)
                .background(
                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                )
        )
    }
}

@Composable
private fun TrackCover(
    coverResId: Int
) {
    Box(
        modifier = Modifier
            .size(145.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.Center
    ) {
        if (coverResId != 0) {
            Image(
                painter = painterResource(coverResId),
                contentDescription = "Обложка трека",
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
            cornerRadius = CornerRadius(w * 0.20f)
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
            start = Offset(w * 0.29f, h * 0.57f),
            end = Offset(w * 0.08f, h * 0.52f),
            strokeWidth = 3f
        )

        drawLine(
            color = outlineColor,
            start = Offset(w * 0.29f, h * 0.63f),
            end = Offset(w * 0.07f, h * 0.65f),
            strokeWidth = 3f
        )

        drawLine(
            color = outlineColor,
            start = Offset(w * 0.71f, h * 0.57f),
            end = Offset(w * 0.92f, h * 0.52f),
            strokeWidth = 3f
        )

        drawLine(
            color = outlineColor,
            start = Offset(w * 0.71f, h * 0.63f),
            end = Offset(w * 0.93f, h * 0.65f),
            strokeWidth = 3f
        )

        drawOval(
            color = bowColor,
            topLeft = Offset(w * 0.60f, h * 0.14f),
            size = Size(w * 0.17f, h * 0.18f)
        )

        drawOval(
            color = bowColor,
            topLeft = Offset(w * 0.75f, h * 0.16f),
            size = Size(w * 0.17f, h * 0.18f)
        )

        drawCircle(
            color = bowColor,
            radius = w * 0.055f,
            center = Offset(w * 0.75f, h * 0.24f)
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

    LaunchedEffect(progress, isSeeking) {
        if (!isSeeking) {
            sliderValue = progress
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Slider(
            value = sliderValue.coerceIn(0f, 1f),
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
                .height(24.dp),
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.primary,
                activeTrackColor = MaterialTheme.colorScheme.primary,
                inactiveTrackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.18f)
            )
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TimeText(
                text = if (isSeeking && durationMs > 0L) {
                    formatTime(
                        (durationMs * sliderValue).toLong()
                    )
                } else {
                    formatTime(positionMs)
                }
            )

            TimeText(
                text = formatTime(durationMs)
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
        fontSize = 9.sp,
        fontFamily = FontFamily.Monospace,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.65f)
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
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                shape = CircleShape
            )
    ) {
        WheelButton(
            text = "MENU",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 17.dp),
            onClick = onOpenPlaylist
        )

        WheelButton(
            text = "PREV",
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 10.dp),
            onClick = onPrevious
        )

        WheelButton(
            text = "NEXT",
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 10.dp),
            onClick = onNext
        )

        WheelButton(
            text = if (isPlaying) "PAUSE" else "PLAY",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 17.dp),
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
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
                    shape = CircleShape
                )
                .clickable(onClick = onPlayPause),
            contentAlignment = Alignment.Center
        ) {
            BowMark()
        }
    }
}

@Composable
private fun WheelButton(
    text: String,
    modifier: Modifier,
    onClick: () -> Unit
) {
    Text(
        text = text,
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(7.dp),
        fontSize = 10.sp,
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.58f)
    )
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
                size.width * 0.05f,
                size.height * 0.23f
            ),
            size = Size(
                size.width * 0.40f,
                size.height * 0.54f
            )
        )

        drawOval(
            color = color,
            topLeft = Offset(
                size.width * 0.55f,
                size.height * 0.23f
            ),
            size = Size(
                size.width * 0.40f,
                size.height * 0.54f
            )
        )

        drawCircle(
            color = color,
            radius = size.width * 0.16f,
            center = Offset(
                size.width / 2f,
                size.height / 2f
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
            selected = shuffle,
            onClick = onShuffle
        )

        SmallControl(
            text = "REPEAT",
            selected = repeat,
            onClick = onRepeat
        )
    }
}

@Composable
private fun SmallControl(
    text: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(9.dp))
            .background(
                if (selected) {
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.18f)
                } else {
                    MaterialTheme.colorScheme.surface
                }
            )
            .border(
                width = 1.dp,
                color = if (selected) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.outline.copy(alpha = 0.35f)
                },
                shape = RoundedCornerShape(9.dp)
            )
            .clickable(onClick = onClick)
            .padding(
                horizontal = 12.dp,
                vertical = 6.dp
            )
    ) {
        Text(
            text = text,
            fontSize = 8.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = if (selected) {
                FontWeight.Bold
            } else {
                FontWeight.Normal
            },
            color = if (selected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.onSurfaceVariant
            }
        )
    }
}

@Composable
private fun PlayerLoading() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .widthIn(max = 390.dp)
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
                .height(300.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.surface)
                .border(
                    width = 3.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = RoundedCornerShape(16.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "LOADING",
                fontSize = 13.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun PlayerEmpty(
    onOpenPlaylist: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .widthIn(max = 390.dp)
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
                .height(320.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.surface)
                .border(
                    width = 3.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = RoundedCornerShape(16.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                KittyFace()

                Spacer(Modifier.height(10.dp))

                Text(
                    text = "Ничего не играет",
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(Modifier.height(5.dp))

                Text(
                    text = "Выберите композицию",
                    fontSize = 9.sp,
                    fontFamily = FontFamily.Monospace,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.55f)
                )

                Spacer(Modifier.height(18.dp))

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.primary)
                        .clickable(onClick = onOpenPlaylist)
                        .padding(
                            horizontal = 18.dp,
                            vertical = 9.dp
                        )
                ) {
                    Text(
                        text = "PLAYLIST",
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
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