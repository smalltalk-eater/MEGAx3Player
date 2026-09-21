package com.example.megax3player.ui.player

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.megax3player.R

@Composable
internal fun PlayerLoading(
    windowWidthSizeClass: WindowWidthSizeClass,
    windowHeightSizeClass: WindowHeightSizeClass
) {
    val transition =
        rememberInfiniteTransition(
            label = "loadingPulse"
        )

    val pulse by transition.animateFloat(
        initialValue = 0.45f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(900),
            repeatMode = RepeatMode.Reverse
        ),
        label = "loadingAlpha"
    )

    if (
        windowHeightSizeClass ==
        WindowHeightSizeClass.Compact
    ) {
        LandscapeLoadingPlayer(pulse)
        return
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (windowWidthSizeClass) {
            WindowWidthSizeClass.Compact ->
                CompactLoadingPlayer(
                    pulse,
                    390
                )

            WindowWidthSizeClass.Medium ->
                CompactLoadingPlayer(
                    pulse,
                    520
                )

            WindowWidthSizeClass.Expanded ->
                WideLoadingPlayer(pulse)

            else ->
                CompactLoadingPlayer(
                    pulse,
                    390
                )
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
            .background(
                MaterialTheme.colorScheme.surfaceVariant
            )
            .border(
                2.dp,
                MaterialTheme.colorScheme.outline,
                RoundedCornerShape(28.dp)
            )
            .padding(16.dp),
        horizontalAlignment =
            Alignment.CenterHorizontally
    ) {
        LoadingDisplay(
            pulse = pulse,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.weight(1f))

        LoadingWheel(
            pulse = pulse,
            compactHeight = false
        )

        Spacer(Modifier.height(10.dp))

        LoadingSecondaryControls(
            pulse = pulse,
            compact = false
        )

        Spacer(Modifier.height(8.dp))

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
        verticalAlignment =
            Alignment.CenterVertically,
        horizontalArrangement =
            Arrangement.spacedBy(18.dp)
    ) {
        CompactLandscapeLoadingDisplay(
            pulse = pulse,
            modifier = Modifier.weight(1.18f)
        )

        Column(
            modifier = Modifier
                .weight(0.82f)
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

            Spacer(Modifier.height(6.dp))

            LoadingSecondaryControls(
                pulse = pulse,
                compact = true
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
        verticalAlignment =
            Alignment.CenterVertically,
        horizontalArrangement =
            Arrangement.spacedBy(36.dp)
    ) {
        LoadingDisplay(
            pulse = pulse,
            modifier = Modifier.weight(1.1f)
        )

        Column(
            modifier = Modifier.weight(0.9f),
            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {
            LoadingWheel(
                pulse = pulse,
                compactHeight = false
            )

            Spacer(Modifier.height(18.dp))

            LoadingSecondaryControls(
                pulse = pulse,
                compact = false
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
            .background(
                MaterialTheme.colorScheme.surface
            )
            .border(
                3.dp,
                MaterialTheme.colorScheme.outline,
                RoundedCornerShape(16.dp)
            )
            .padding(14.dp),
        horizontalAlignment =
            Alignment.CenterHorizontally
    ) {
        LoadingStatusBar(pulse)

        Spacer(Modifier.height(8.dp))

        SkeletonBlock(
            modifier = Modifier.size(145.dp),
            pulse = pulse,
            cornerRadius = 12
        )

        Spacer(Modifier.height(14.dp))

        SkeletonBlock(
            modifier = Modifier
                .width(170.dp)
                .height(18.dp),
            pulse = pulse
        )

        Spacer(Modifier.height(7.dp))

        SkeletonBlock(
            modifier = Modifier
                .width(100.dp)
                .height(12.dp),
            pulse = pulse
        )

        Spacer(Modifier.height(12.dp))

        SkeletonBlock(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp),
            pulse = pulse
        )

        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
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
            .clip(RoundedCornerShape(16.dp))
            .background(
                MaterialTheme.colorScheme.surface
            )
            .border(
                3.dp,
                MaterialTheme.colorScheme.outline,
                RoundedCornerShape(16.dp)
            )
            .padding(8.dp),
        horizontalAlignment =
            Alignment.CenterHorizontally
    ) {
        LoadingStatusBar(pulse)

        Spacer(Modifier.height(2.dp))

        SkeletonBlock(
            modifier = Modifier.size(88.dp),
            pulse = pulse,
            cornerRadius = 10
        )

        Spacer(Modifier.height(5.dp))

        SkeletonBlock(
            modifier = Modifier
                .width(150.dp)
                .height(16.dp),
            pulse = pulse
        )

        Spacer(Modifier.height(4.dp))

        SkeletonBlock(
            modifier = Modifier
                .width(90.dp)
                .height(10.dp),
            pulse = pulse
        )

        Spacer(Modifier.height(5.dp))

        SkeletonBlock(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp),
            pulse = pulse
        )

        Spacer(Modifier.height(4.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
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

        Spacer(Modifier.weight(1f))

        SkeletonBlock(
            modifier = Modifier
                .width(40.dp)
                .height(28.dp),
            pulse = pulse
        )

        Spacer(Modifier.width(2.dp))

        SkeletonBlock(
            modifier = Modifier
                .width(52.dp)
                .height(28.dp),
            pulse = pulse
        )

        Spacer(Modifier.width(5.dp))

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
        if (compactHeight) 160.dp else 250.dp

    val fraction =
        if (compactHeight) 0.72f else 0.76f

    Box(
        modifier = Modifier
            .fillMaxWidth(fraction)
            .widthIn(max = maxSize)
            .aspectRatio(1f)
            .clip(CircleShape)
            .background(
                MaterialTheme.colorScheme.surface
            )
            .border(
                2.dp,
                MaterialTheme.colorScheme.outline,
                CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        SkeletonBlock(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 16.dp)
                .width(48.dp)
                .height(10.dp),
            pulse = pulse
        )

        SkeletonBlock(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 12.dp)
                .width(38.dp)
                .height(10.dp),
            pulse = pulse
        )

        SkeletonBlock(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 12.dp)
                .width(38.dp)
                .height(10.dp),
            pulse = pulse
        )

        SkeletonBlock(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
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
                    2.dp,
                    MaterialTheme.colorScheme.outline,
                    CircleShape
                )
                .alpha(pulse)
        )
    }
}

@Composable
private fun LoadingSecondaryControls(
    pulse: Float,
    compact: Boolean
) {
    Row(
        horizontalArrangement =
            Arrangement.spacedBy(
                if (compact) 6.dp else 8.dp
            )
    ) {
        repeat(2) {
            SkeletonBlock(
                modifier = Modifier
                    .width(
                        if (compact) {
                            104.dp
                        } else {
                            118.dp
                        }
                    )
                    .height(48.dp),
                pulse = pulse,
                cornerRadius = 9
            )
        }
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
internal fun PlayerEmpty(
    windowWidthSizeClass: WindowWidthSizeClass,
    windowHeightSizeClass: WindowHeightSizeClass,
    onOpenPlaylist: () -> Unit
) {
    if (
        windowHeightSizeClass ==
        WindowHeightSizeClass.Compact
    ) {
        LandscapeEmptyPlayer(onOpenPlaylist)
        return
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (windowWidthSizeClass) {
            WindowWidthSizeClass.Compact ->
                CompactEmptyPlayer(
                    390,
                    onOpenPlaylist
                )

            WindowWidthSizeClass.Medium ->
                CompactEmptyPlayer(
                    520,
                    onOpenPlaylist
                )

            WindowWidthSizeClass.Expanded ->
                WideEmptyPlayer(
                    onOpenPlaylist
                )

            else ->
                CompactEmptyPlayer(
                    390,
                    onOpenPlaylist
                )
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
            .background(
                MaterialTheme.colorScheme.surfaceVariant
            )
            .border(
                2.dp,
                MaterialTheme.colorScheme.outline,
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
                .clip(RoundedCornerShape(16.dp))
                .background(
                    MaterialTheme.colorScheme.surface
                )
                .border(
                    3.dp,
                    MaterialTheme.colorScheme.outline,
                    RoundedCornerShape(16.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            EmptyPlayerContent(
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
            .clip(RoundedCornerShape(24.dp))
            .background(
                MaterialTheme.colorScheme.surfaceVariant
            )
            .border(
                2.dp,
                MaterialTheme.colorScheme.outline,
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
                .clip(RoundedCornerShape(16.dp))
                .background(
                    MaterialTheme.colorScheme.surface
                )
                .border(
                    3.dp,
                    MaterialTheme.colorScheme.outline,
                    RoundedCornerShape(16.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            KittyFace(110.dp)
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement =
                Arrangement.Center,
            horizontalAlignment =
                Alignment.Start
        ) {
            Text(
                text = stringResource(
                    R.string.nothing_playing
                ),
                style =
                    MaterialTheme.typography
                        .headlineMedium
                        .copy(
                            fontFamily =
                                FontFamily.Monospace
                        ),
                color =
                    MaterialTheme.colorScheme.onSurface
            )

            Spacer(Modifier.height(6.dp))

            Text(
                text = stringResource(
                    R.string.choose_track_long
                ),
                style =
                    MaterialTheme.typography
                        .bodyMedium
                        .copy(
                            fontFamily =
                                FontFamily.Monospace
                        ),
                color =
                    MaterialTheme.colorScheme
                        .onSurfaceVariant
            )

            Spacer(Modifier.height(14.dp))

            EmptyPlaylistButton(
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
            .widthIn(max = 850.dp)
            .fillMaxHeight(0.72f)
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
        verticalAlignment =
            Alignment.CenterVertically,
        horizontalArrangement =
            Arrangement.spacedBy(32.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clip(RoundedCornerShape(16.dp))
                .background(
                    MaterialTheme.colorScheme.surface
                )
                .border(
                    3.dp,
                    MaterialTheme.colorScheme.outline,
                    RoundedCornerShape(16.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            KittyFace()
        }

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment =
                Alignment.Start,
            verticalArrangement =
                Arrangement.Center
        ) {
            Text(
                text = stringResource(
                    R.string.nothing_playing
                ),
                style =
                    MaterialTheme.typography
                        .headlineMedium
                        .copy(
                            fontFamily =
                                FontFamily.Monospace
                        ),
                color =
                    MaterialTheme.colorScheme.onSurface
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = stringResource(
                    R.string.choose_track_long
                ),
                style =
                    MaterialTheme.typography
                        .bodyLarge
                        .copy(
                            fontFamily =
                                FontFamily.Monospace
                        ),
                color =
                    MaterialTheme.colorScheme
                        .onSurfaceVariant
            )

            Spacer(Modifier.height(24.dp))

            EmptyPlaylistButton(
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
        modifier = Modifier.padding(24.dp),
        horizontalAlignment =
            Alignment.CenterHorizontally
    ) {
        KittyFace()

        Spacer(Modifier.height(14.dp))

        Text(
            text = stringResource(
                R.string.nothing_playing
            ),
            style =
                MaterialTheme.typography
                    .titleMedium
                    .copy(
                        fontFamily =
                            FontFamily.Monospace
                    ),
            color =
                MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(6.dp))

        Text(
            text = stringResource(
                R.string.choose_track
            ),
            style =
                MaterialTheme.typography
                    .bodyMedium
                    .copy(
                        fontFamily =
                            FontFamily.Monospace
                    ),
            color =
                MaterialTheme.colorScheme
                    .onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(Modifier.height(20.dp))

        EmptyPlaylistButton(
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
            .clip(RoundedCornerShape(8.dp))
            .background(
                MaterialTheme.colorScheme.primary
            )
            .semantics {
                role = Role.Button
                contentDescription = description
            }
            .clickable(
                role = Role.Button,
                onClick = onOpenPlaylist
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(
                R.string.playlist_action
            ),
            style =
                MaterialTheme.typography
                    .labelLarge
                    .copy(
                        fontFamily =
                            FontFamily.Monospace
                    ),
            color =
                MaterialTheme.colorScheme.onPrimary
        )
    }
}