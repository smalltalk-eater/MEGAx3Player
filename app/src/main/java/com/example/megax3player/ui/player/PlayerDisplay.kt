package com.example.megax3player.ui.player

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
    } else {
        0f
    }

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
    val languageDescription =
        stringResource(R.string.switch_language)

    val languageState =
        stringResource(R.string.language_state)

    val themeDescription =
        stringResource(R.string.switch_theme)

    val themeState =
        if (darkTheme) {
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
                .sizeIn(
                    minWidth = 48.dp,
                    minHeight = 48.dp
                )
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
                text =
                    if (currentLanguage == "ru") {
                        "RU"
                    } else {
                        "EN"
                    },
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
                .sizeIn(
                    minWidth = 56.dp,
                    minHeight = 48.dp
                )
                .clip(RoundedCornerShape(8.dp))
                .semantics {
                    role = Role.Button
                    contentDescription = themeDescription
                    stateDescription = themeState
                }
                .clickable(
                    role = Role.Button
                ) {
                    onDarkThemeChange(!darkTheme)
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text =
                    if (darkTheme) {
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
    }
}

@Composable
private fun TrackCover(
    coverResId: Int,
    title: String,
    size: Dp
) {
    val description =
        stringResource(
            R.string.track_cover,
            title
        )

    Box(
        modifier = Modifier
            .size(size)
            .clip(RoundedCornerShape(12.dp))
            .background(
                MaterialTheme.colorScheme.surfaceVariant
            )
            .semantics {
                contentDescription = description
            },
        contentAlignment = Alignment.Center
    ) {
        if (coverResId != 0) {
            Image(
                painter = painterResource(
                    coverResId
                ),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        } else {
            Text(
                text = "MEGA",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontFamily = FontFamily.Monospace
                ),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
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
                progress.coerceIn(0f, 1f)
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth()
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
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement =
                Arrangement.SpaceBetween
        ) {
            TimeText(
                text =
                    if (
                        isSeeking &&
                        durationMs > 0
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
            MaterialTheme.typography.labelSmall.copy(
                fontFamily =
                    FontFamily.Monospace
            ),
        color =
            MaterialTheme.colorScheme.onSurfaceVariant
    )
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