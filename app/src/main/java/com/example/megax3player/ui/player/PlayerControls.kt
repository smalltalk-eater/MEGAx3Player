package com.example.megax3player.ui.player

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.megax3player.R

@Composable
internal fun PlayerWheel(
    isPlaying: Boolean,
    compactHeight: Boolean,
    onOpenPlaylist: () -> Unit,
    onPlayPause: () -> Unit,
    onPrevious: () -> Unit,
    onNext: () -> Unit
) {
    val openPlaylistDescription =
        stringResource(R.string.open_playlist)

    val previousDescription =
        stringResource(R.string.previous_track)

    val nextDescription =
        stringResource(R.string.next_track)

    val playDescription =
        if (isPlaying) {
            stringResource(R.string.pause_playback)
        } else {
            stringResource(R.string.start_playback)
        }

    val wheelFraction =
        if (compactHeight) 0.72f else 0.76f

    val wheelMaxSize =
        if (compactHeight) 160.dp else 250.dp

    val edgePadding =
        if (compactHeight) 4.dp else 8.dp

    Box(
        modifier = Modifier
            .fillMaxWidth(wheelFraction)
            .widthIn(max = wheelMaxSize)
            .aspectRatio(1f)
            .clip(CircleShape)
            .background(
                MaterialTheme.colorScheme.surface
            )
            .border(
                2.dp,
                MaterialTheme.colorScheme.outline,
                CircleShape
            )
    ) {
        WheelButton(
            text =
                stringResource(R.string.menu),
            description =
                openPlaylistDescription,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = edgePadding),
            onClick = onOpenPlaylist
        )

        WheelButton(
            text =
                stringResource(R.string.previous),
            description =
                previousDescription,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = edgePadding),
            onClick = onPrevious
        )

        WheelButton(
            text =
                stringResource(R.string.next),
            description =
                nextDescription,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = edgePadding),
            onClick = onNext
        )

        Box(
            modifier = Modifier
                .fillMaxSize(0.40f)
                .align(Alignment.Center)
                .clip(CircleShape)
                .background(
                    MaterialTheme.colorScheme.surfaceVariant
                )
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.outline,
                    CircleShape
                )
                .semantics {
                    role = Role.Button
                    contentDescription =
                        playDescription
                }
                .clickable(
                    role = Role.Button,
                    onClick = onPlayPause
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text =
                    if (isPlaying) {
                        stringResource(R.string.pause)
                    } else {
                        stringResource(R.string.play)
                    },
                style =
                    MaterialTheme.typography.labelMedium.copy(
                        fontFamily =
                            FontFamily.Monospace
                    ),
                fontWeight =
                    FontWeight.Bold,
                color =
                    MaterialTheme.colorScheme.primary
            )
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
            maxLines = 1,
            style =
                MaterialTheme.typography.labelSmall.copy(
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
internal fun SecondaryControls(
    shuffle: Boolean,
    repeat: Boolean,
    onShuffle: () -> Unit,
    onRepeat: () -> Unit,
    compact: Boolean = false
) {
    Row(
        horizontalArrangement =
            Arrangement.spacedBy(
                if (compact) {
                    6.dp
                } else {
                    8.dp
                }
            )
    ) {
        SmallControl(
            text =
                stringResource(R.string.shuffle),
            description =
                stringResource(
                    R.string.shuffle_description
                ),
            selected = shuffle,
            compact = compact,
            onClick = onShuffle
        )

        SmallControl(
            text =
                stringResource(R.string.repeat),
            description =
                stringResource(
                    R.string.repeat_description
                ),
            selected = repeat,
            compact = compact,
            onClick = onRepeat
        )
    }
}

@Composable
private fun SmallControl(
    text: String,
    description: String,
    selected: Boolean,
    compact: Boolean,
    onClick: () -> Unit
) {
    val enabledText =
        stringResource(R.string.enabled)

    val disabledText =
        stringResource(R.string.disabled)

    val onText =
        stringResource(R.string.on)

    val offText =
        stringResource(R.string.off)

    Box(
        modifier = Modifier
            .sizeIn(
                minWidth =
                    if (compact) {
                        104.dp
                    } else {
                        118.dp
                    },
                minHeight = 48.dp
            )
            .clip(
                RoundedCornerShape(9.dp)
            )
            .background(
                if (selected) {
                    MaterialTheme.colorScheme.primary
                        .copy(alpha = 0.12f)
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
            )
            .padding(
                horizontal =
                    if (compact) {
                        5.dp
                    } else {
                        8.dp
                    }
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
            maxLines = 1,
            textAlign =
                TextAlign.Center,
            style =
                MaterialTheme.typography.labelSmall.copy(
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