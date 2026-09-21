package com.example.megax3player.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.megax3player.model.Track

@Composable
fun PlaylistScreen(
    tracks: List<Track>,
    playerState: PlayerUiState,
    windowWidthSizeClass: WindowWidthSizeClass,
    onBack: () -> Unit,
    onTrackClick: (Track) -> Unit,
    onPlayPause: () -> Unit,
    onNext: () -> Unit,
    onOpenPlayer: () -> Unit
) {
    if (windowWidthSizeClass == WindowWidthSizeClass.Expanded) {
        WidePlaylistScreen(
            tracks = tracks,
            playerState = playerState,
            onBack = onBack,
            onTrackClick = onTrackClick,
            onPlayPause = onPlayPause,
            onNext = onNext,
            onOpenPlayer = onOpenPlayer
        )
    } else {
        CompactPlaylistScreen(
            tracks = tracks,
            playerState = playerState,
            onBack = onBack,
            onTrackClick = onTrackClick,
            onPlayPause = onPlayPause,
            onNext = onNext,
            onOpenPlayer = onOpenPlayer
        )
    }
}

@Composable
private fun CompactPlaylistScreen(
    tracks: List<Track>,
    playerState: PlayerUiState,
    onBack: () -> Unit,
    onTrackClick: (Track) -> Unit,
    onPlayPause: () -> Unit,
    onNext: () -> Unit,
    onOpenPlayer: () -> Unit
) {
    val content = playerState as? PlayerUiState.Content

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 18.dp, vertical = 14.dp)
    ) {
        PlaylistHeader(onBack)

        Spacer(Modifier.height(18.dp))

        Text(
            text = "Моя музыка",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(Modifier.height(3.dp))

        Text(
            text = "${tracks.size} треков",
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
        )

        Spacer(Modifier.height(16.dp))

        TrackList(
            tracks = tracks,
            currentTrackId = content?.track?.id,
            onTrackClick = onTrackClick,
            modifier = Modifier.weight(1f)
        )

        if (content != null) {
            Spacer(Modifier.height(10.dp))

            MiniPlayer(
                state = content,
                onPlayPause = onPlayPause,
                onNext = onNext,
                onOpenPlayer = onOpenPlayer
            )
        }
    }
}

@Composable
private fun WidePlaylistScreen(
    tracks: List<Track>,
    playerState: PlayerUiState,
    onBack: () -> Unit,
    onTrackClick: (Track) -> Unit,
    onPlayPause: () -> Unit,
    onNext: () -> Unit,
    onOpenPlayer: () -> Unit
) {
    val content = playerState as? PlayerUiState.Content

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Column(
            modifier = Modifier.weight(1.4f)
        ) {
            PlaylistHeader(onBack)

            Spacer(Modifier.height(18.dp))

            Text(
                text = "Моя музыка",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(Modifier.height(4.dp))

            Text(
                text = "${tracks.size} треков",
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )

            Spacer(Modifier.height(16.dp))

            TrackList(
                tracks = tracks,
                currentTrackId = content?.track?.id,
                onTrackClick = onTrackClick,
                modifier = Modifier.weight(1f)
            )
        }

        if (content != null) {
            Column(
                modifier = Modifier
                    .widthIn(min = 260.dp, max = 330.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                NowPlayingPanel(
                    state = content,
                    onPlayPause = onPlayPause,
                    onNext = onNext,
                    onOpenPlayer = onOpenPlayer
                )
            }
        }
    }
}

@Composable
private fun PlaylistHeader(
    onBack: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "<",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .clickable(onClick = onBack)
                .padding(horizontal = 9.dp, vertical = 5.dp)
        )

        Spacer(Modifier.width(6.dp))

        Text(
            text = "Плейлист",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
private fun TrackList(
    tracks: List<Track>,
    currentTrackId: Int?,
    onTrackClick: (Track) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(7.dp)
    ) {
        itemsIndexed(
            items = tracks,
            key = { _, track -> track.id }
        ) { index, track ->
            TrackItem(
                number = index + 1,
                track = track,
                selected = track.id == currentTrackId,
                onClick = {
                    onTrackClick(track)
                }
            )
        }
    }
}

@Composable
private fun TrackItem(
    number: Int,
    track: Track,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(7.dp))
            .background(
                if (selected) {
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
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
                shape = RoundedCornerShape(7.dp)
            )
            .clickable(onClick = onClick)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = number.toString().padStart(2, '0'),
            modifier = Modifier.width(32.dp),
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            color = if (selected) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.onSurfaceVariant
            }
        )

        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = number.toString(),
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(Modifier.width(10.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = track.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 13.sp,
                fontWeight = if (selected) {
                    FontWeight.Bold
                } else {
                    FontWeight.Medium
                },
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(Modifier.height(2.dp))

            Text(
                text = track.artist,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 10.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        if (selected) {
            Text(
                text = "Играет",
                modifier = Modifier.padding(end = 10.dp),
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Text(
            text = track.duration,
            fontSize = 10.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun MiniPlayer(
    state: PlayerUiState.Content,
    onPlayPause: () -> Unit,
    onNext: () -> Unit,
    onOpenPlayer: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.surface)
            .border(
                1.dp,
                MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
                RoundedCornerShape(10.dp)
            )
            .clickable(onClick = onOpenPlayer)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(7.dp))
                .background(
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.13f)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "MEGA",
                fontSize = 8.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(Modifier.width(10.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = state.track.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = state.track.artist,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 9.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        MiniPlayerButton(
            text = if (state.isPlaying) "PAUSE" else "PLAY",
            onClick = onPlayPause
        )

        Spacer(Modifier.width(6.dp))

        MiniPlayerButton(
            text = "NEXT",
            onClick = onNext
        )
    }
}

@Composable
private fun MiniPlayerButton(
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .height(34.dp)
            .clip(RoundedCornerShape(7.dp))
            .background(MaterialTheme.colorScheme.primary)
            .clickable(onClick = onClick)
            .padding(horizontal = 9.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 8.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
private fun NowPlayingPanel(
    state: PlayerUiState.Content,
    onPlayPause: () -> Unit,
    onNext: () -> Unit,
    onOpenPlayer: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .background(MaterialTheme.colorScheme.surface)
            .border(
                1.dp,
                MaterialTheme.colorScheme.outline.copy(alpha = 0.4f),
                RoundedCornerShape(14.dp)
            )
            .padding(18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(150.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "MEGA",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Spacer(Modifier.height(16.dp))

        Text(
            text = state.track.title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(Modifier.height(3.dp))

        Text(
            text = state.track.artist,
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(Modifier.height(18.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MiniPlayerButton(
                text = if (state.isPlaying) "PAUSE" else "PLAY",
                onClick = onPlayPause
            )

            MiniPlayerButton(
                text = "NEXT",
                onClick = onNext
            )
        }

        Spacer(Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(36.dp)
                .clip(RoundedCornerShape(8.dp))
                .border(
                    1.dp,
                    MaterialTheme.colorScheme.primary,
                    RoundedCornerShape(8.dp)
                )
                .clickable(onClick = onOpenPlayer),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Открыть плеер",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}