package com.example.megax3player.ui.player

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.megax3player.R

@Composable
internal fun PlayerLoading(
    windowWidthSizeClass: WindowWidthSizeClass,
    windowHeightSizeClass: WindowHeightSizeClass
) {
    val maxWidth = when (windowWidthSizeClass) {
        WindowWidthSizeClass.Expanded -> 560.dp
        WindowWidthSizeClass.Medium -> 480.dp
        else -> 390.dp
    }

    val compactHeight =
        windowHeightSizeClass == WindowHeightSizeClass.Compact

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = maxWidth)
                .heightIn(
                    min = if (compactHeight) 130.dp else 220.dp
                )
                .clip(RoundedCornerShape(24.dp))
                .background(
                    MaterialTheme.colorScheme.surfaceVariant
                )
                .border(
                    2.dp,
                    MaterialTheme.colorScheme.outline,
                    RoundedCornerShape(24.dp)
                )
                .padding(24.dp),
            horizontalAlignment =
                Alignment.CenterHorizontally,
            verticalArrangement =
                Arrangement.Center
        ) {
            CircularProgressIndicator(
                color =
                    MaterialTheme.colorScheme.primary
            )

            if (!compactHeight) {
                Spacer(
                    modifier = Modifier.height(20.dp)
                )

                Text(
                    text = "MEGAx3Player",
                    style =
                        MaterialTheme.typography.titleMedium.copy(
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
    }
}

@Composable
internal fun PlayerEmpty(
    windowWidthSizeClass: WindowWidthSizeClass,
    windowHeightSizeClass: WindowHeightSizeClass,
    onOpenPlaylist: () -> Unit
) {
    val maxWidth = when (windowWidthSizeClass) {
        WindowWidthSizeClass.Expanded -> 560.dp
        WindowWidthSizeClass.Medium -> 480.dp
        else -> 390.dp
    }

    val compactHeight =
        windowHeightSizeClass == WindowHeightSizeClass.Compact

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (compactHeight) {
            LandscapeEmptyContent(
                maxWidth = maxWidth,
                onOpenPlaylist = onOpenPlaylist
            )
        } else {
            PortraitEmptyContent(
                maxWidth = maxWidth,
                onOpenPlaylist = onOpenPlaylist
            )
        }
    }
}

@Composable
private fun PortraitEmptyContent(
    maxWidth: androidx.compose.ui.unit.Dp,
    onOpenPlaylist: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .widthIn(max = maxWidth)
            .clip(RoundedCornerShape(24.dp))
            .background(
                MaterialTheme.colorScheme.surfaceVariant
            )
            .border(
                2.dp,
                MaterialTheme.colorScheme.outline,
                RoundedCornerShape(24.dp)
            )
            .padding(28.dp),
        horizontalAlignment =
            Alignment.CenterHorizontally
    ) {
        EmptyTitle()

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text =
                stringResource(
                    R.string.choose_track_long
                ),
            style =
                MaterialTheme.typography.bodyMedium,
            textAlign =
                TextAlign.Center,
            color =
                MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        PlaylistButton(
            onClick = onOpenPlaylist
        )
    }
}

@Composable
private fun LandscapeEmptyContent(
    maxWidth: androidx.compose.ui.unit.Dp,
    onOpenPlaylist: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .widthIn(max = maxWidth)
            .clip(RoundedCornerShape(20.dp))
            .background(
                MaterialTheme.colorScheme.surfaceVariant
            )
            .border(
                2.dp,
                MaterialTheme.colorScheme.outline,
                RoundedCornerShape(20.dp)
            )
            .padding(20.dp),
        verticalAlignment =
            Alignment.CenterVertically,
        horizontalArrangement =
            Arrangement.spacedBy(24.dp)
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            EmptyTitle()

            Spacer(
                modifier = Modifier.height(6.dp)
            )

            Text(
                text =
                    stringResource(
                        R.string.choose_track
                    ),
                style =
                    MaterialTheme.typography.bodyMedium,
                color =
                    MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        PlaylistButton(
            onClick = onOpenPlaylist
        )
    }
}

@Composable
private fun EmptyTitle() {
    Text(
        text =
            stringResource(
                R.string.nothing_playing
            ),
        style =
            MaterialTheme.typography.titleLarge.copy(
                fontFamily =
                    FontFamily.Monospace
            ),
        fontWeight =
            FontWeight.Bold,
        textAlign =
            TextAlign.Center,
        color =
            MaterialTheme.colorScheme.onSurface
    )
}

@Composable
private fun PlaylistButton(
    onClick: () -> Unit
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
                onClick = onClick
            )
            .padding(
                horizontal = 20.dp,
                vertical = 10.dp
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
                MaterialTheme.typography.labelLarge,
            fontWeight =
                FontWeight.Bold,
            color =
                MaterialTheme.colorScheme.onPrimary
        )
    }
}