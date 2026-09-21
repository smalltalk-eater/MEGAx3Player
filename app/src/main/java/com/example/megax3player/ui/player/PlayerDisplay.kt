package com.example.megax3player.ui.player

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.megax3player.R
import com.example.megax3player.ui.PlayerUiState

@Composable
internal fun PlayerDisplay(
    state: PlayerUiState.Content,
    darkTheme: Boolean,
    currentLanguage: String,
    compactHeight: Boolean,
    onDarkThemeChange: (Boolean) -> Unit,
    onLanguageChange: () -> Unit,
    onSeek: (Float) -> Unit
) {
    val progress = if (state.durationMs > 0L) {
        state.positionMs.toFloat() / state.durationMs.toFloat()
    } else 0f

    val displayPadding = if (compactHeight) 8.dp else 14.dp
    val coverSize = if (compactHeight) 88.dp else 145.dp
    val statusGap = if (compactHeight) 2.dp else 8.dp
    val coverGap = if (compactHeight) 4.dp else 10.dp
    val progressGap = if (compactHeight) 2.dp else 8.dp

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface)
            .border(
                3.dp,
                MaterialTheme.colorScheme.outline,
                RoundedCornerShape(16.dp)
            )
            .padding(displayPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PlayerStatusBar(
            darkTheme = darkTheme,
            currentLanguage = currentLanguage,
            onDarkThemeChange = onDarkThemeChange,
            onLanguageChange = onLanguageChange
        )

        Spacer(Modifier.height(statusGap))

        TrackCover(
            coverResId = state.track.coverResId,
            title = state.track.title,
            size = coverSize
        )

        Spacer(Modifier.height(coverGap))

        Text(
            text = state.track.title,
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center,
            style = if (compactHeight) {
                MaterialTheme.typography.titleMedium.copy(
                    fontFamily = FontFamily.Monospace
                )
            } else {
                MaterialTheme.typography.titleLarge.copy(
                    fontFamily = FontFamily.Monospace
                )
            },
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(
            Modifier.height(
                if (compactHeight) 1.dp else 3.dp
            )
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

        Spacer(Modifier.height(progressGap))

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
    val languageDescription = stringResource(R.string.switch_language)
    val languageState = stringResource(R.string.language_state)
    val themeDescription = stringResource(R.string.switch_theme)

    val themeState = if (darkTheme) {
        stringResource(R.string.dark_theme_enabled)
    } else {
        stringResource(R.string.light_theme_enabled)
    }

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
                .sizeIn(minWidth = 48.dp, minHeight = 48.dp)
                .clip(RoundedCornerShape(8.dp))
                .semantics {
                    role = Role.Button
                    contentDescription = languageDescription
                    stateDescription = languageState
                }
                .clickable(
                    role = Role.Button,
                    onClick = onLanguageChange
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (currentLanguage == "ru") "RU" else "EN",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontFamily = FontFamily.Monospace
                ),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(Modifier.width(2.dp))

        Box(
            modifier = Modifier
                .sizeIn(minWidth = 56.dp, minHeight = 48.dp)
                .clip(RoundedCornerShape(8.dp))
                .semantics {
                    role = Role.Button
                    contentDescription = themeDescription
                    stateDescription = themeState
                }
                .clickable(role = Role.Button) {
                    onDarkThemeChange(!darkTheme)
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (darkTheme) {
                    stringResource(R.string.theme_dark)
                } else {
                    stringResource(R.string.theme_light)
                },
                maxLines = 1,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontFamily = FontFamily.Monospace
                ),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(Modifier.width(4.dp))

        BatteryIndicator()
    }
}

@Composable
private fun BatteryIndicator() {
    val description = stringResource(R.string.battery_72)

    Box(
        modifier = Modifier
            .width(26.dp)
            .height(12.dp)
            .border(
                1.dp,
                MaterialTheme.colorScheme.onSurfaceVariant,
                RoundedCornerShape(2.dp)
            )
            .padding(2.dp)
            .semantics {
                contentDescription = description
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
    val description = stringResource(
        R.string.track_cover,
        title
    )

    Box(
        modifier = Modifier
            .size(size)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .semantics {
                contentDescription = description
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
            KittyFace(
                size = size * 0.72f
            )
        }
    }
}

@Composable
internal fun KittyFace(
    size: Dp = 105.dp
) {
    val faceColor = MaterialTheme.colorScheme.surfaceVariant
    val outlineColor = MaterialTheme.colorScheme.onSurface
    val bowColor = MaterialTheme.colorScheme.primary
    val noseColor = MaterialTheme.colorScheme.secondary

    Canvas(
        modifier = Modifier.size(size)
    ) {
        val w = this.size.width
        val h = this.size.height

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

        drawPath(leftEar, faceColor)
        drawPath(rightEar, faceColor)

        drawRoundRect(
            color = faceColor,
            topLeft = Offset(w * 0.17f, h * 0.25f),
            size = Size(w * 0.66f, h * 0.57f),
            cornerRadius = CornerRadius(
                w * 0.20f,
                w * 0.20f
            )
        )

        drawCircle(
            color = outlineColor,
            radius = w * 0.025f,
            center = Offset(w * 0.38f, h * 0.52f)
        )

        drawCircle(
            color = outlineColor,
            radius = w * 0.025f,
            center = Offset(w * 0.62f, h * 0.52f)
        )

        drawOval(
            color = noseColor,
            topLeft = Offset(w * 0.47f, h * 0.58f),
            size = Size(w * 0.06f, h * 0.045f)
        )

        drawLine(
            outlineColor,
            Offset(w * 0.29f, h * 0.57f),
            Offset(w * 0.08f, h * 0.52f),
            3f
        )

        drawLine(
            outlineColor,
            Offset(w * 0.29f, h * 0.63f),
            Offset(w * 0.07f, h * 0.65f),
            3f
        )

        drawLine(
            outlineColor,
            Offset(w * 0.71f, h * 0.57f),
            Offset(w * 0.92f, h * 0.52f),
            3f
        )

        drawLine(
            outlineColor,
            Offset(w * 0.71f, h * 0.63f),
            Offset(w * 0.93f, h * 0.65f),
            3f
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

    val progressDescription =
        stringResource(R.string.playback_position)

    LaunchedEffect(
        progress,
        isSeeking
    ) {
        if (!isSeeking) {
            sliderValue =
                progress.coerceIn(0f, 1f)
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
                .height(48.dp)
                .semantics {
                    contentDescription =
                        progressDescription
                },
            colors = SliderDefaults.colors(
                thumbColor =
                    MaterialTheme.colorScheme.primary,
                activeTrackColor =
                    MaterialTheme.colorScheme.primary,
                inactiveTrackColor =
                    MaterialTheme.colorScheme.outline
            )
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement =
                Arrangement.SpaceBetween
        ) {
            TimeText(
                text = if (
                    isSeeking &&
                    durationMs > 0
                ) {
                    formatTime(
                        (durationMs * sliderValue)
                            .toLong()
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
        style = MaterialTheme.typography.labelSmall.copy(
            fontFamily = FontFamily.Monospace
        ),
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
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