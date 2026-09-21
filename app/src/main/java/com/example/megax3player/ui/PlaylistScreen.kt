package com.example.megax3player.ui

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.megax3player.R
import com.example.megax3player.model.Track
import com.example.megax3player.ui.theme.MegaX3PlayerTheme

@Composable
fun PlaylistScreen(
    tracks: List<Track>,
    playerState: PlayerUiState,
    windowWidthSizeClass: WindowWidthSizeClass,
    windowHeightSizeClass: WindowHeightSizeClass,
    onBack: () -> Unit,
    onTrackClick: (Track) -> Unit,
    onPlayPause: () -> Unit,
    onNext: () -> Unit,
    onOpenPlayer: () -> Unit
) {
    if (
        windowHeightSizeClass ==
        WindowHeightSizeClass.Compact
    ) {
        LandscapePlaylistScreen(
            tracks = tracks,
            playerState = playerState,
            onBack = onBack,
            onTrackClick = onTrackClick,
            onPlayPause = onPlayPause,
            onNext = onNext,
            onOpenPlayer = onOpenPlayer
        )

        return
    }

    when (windowWidthSizeClass) {
        WindowWidthSizeClass.Expanded -> {
            WidePlaylistScreen(
                tracks = tracks,
                playerState = playerState,
                onBack = onBack,
                onTrackClick = onTrackClick,
                onPlayPause = onPlayPause,
                onNext = onNext,
                onOpenPlayer = onOpenPlayer
            )
        }

        else -> {
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
    val content =
        playerState as? PlayerUiState.Content

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.background
            )
            .padding(
                horizontal = 18.dp,
                vertical = 14.dp
            )
    ) {
        PlaylistHeader(
            onBack = onBack
        )

        Spacer(
            modifier = Modifier.height(18.dp)
        )

        PlaylistTitle(
            trackCount = tracks.size
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        if (tracks.isEmpty()) {
            EmptyPlaylist(
                modifier = Modifier.weight(1f)
            )
        } else {
            TrackList(
                tracks = tracks,
                currentTrackId =
                    content?.track?.id,
                onTrackClick =
                    onTrackClick,
                modifier =
                    Modifier.weight(1f)
            )
        }

        if (content != null) {
            Spacer(
                modifier = Modifier.height(10.dp)
            )

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
private fun LandscapePlaylistScreen(
    tracks: List<Track>,
    playerState: PlayerUiState,
    onBack: () -> Unit,
    onTrackClick: (Track) -> Unit,
    onPlayPause: () -> Unit,
    onNext: () -> Unit,
    onOpenPlayer: () -> Unit
) {
    val content =
        playerState as? PlayerUiState.Content

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.background
            )
            .padding(12.dp),
        horizontalArrangement =
            Arrangement.spacedBy(16.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1.4f)
                .fillMaxHeight()
        ) {
            PlaylistHeader(
                onBack = onBack
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            PlaylistTitle(
                trackCount = tracks.size,
                compact = true
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            if (tracks.isEmpty()) {
                EmptyPlaylist(
                    modifier =
                        Modifier.weight(1f)
                )
            } else {
                TrackList(
                    tracks = tracks,
                    currentTrackId =
                        content?.track?.id,
                    onTrackClick =
                        onTrackClick,
                    modifier =
                        Modifier.weight(1f)
                )
            }
        }

        if (content != null) {
            Column(
                modifier = Modifier
                    .weight(0.8f)
                    .fillMaxHeight(),
                verticalArrangement =
                    Arrangement.Center
            ) {
                LandscapeMiniPlayer(
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
private fun WidePlaylistScreen(
    tracks: List<Track>,
    playerState: PlayerUiState,
    onBack: () -> Unit,
    onTrackClick: (Track) -> Unit,
    onPlayPause: () -> Unit,
    onNext: () -> Unit,
    onOpenPlayer: () -> Unit
) {
    val content =
        playerState as? PlayerUiState.Content

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.background
            )
            .padding(24.dp),
        horizontalArrangement =
            Arrangement.spacedBy(24.dp)
    ) {
        Column(
            modifier =
                Modifier.weight(1.4f)
        ) {
            PlaylistHeader(
                onBack = onBack
            )

            Spacer(
                modifier = Modifier.height(18.dp)
            )

            PlaylistTitle(
                trackCount = tracks.size
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            if (tracks.isEmpty()) {
                EmptyPlaylist(
                    modifier =
                        Modifier.weight(1f)
                )
            } else {
                TrackList(
                    tracks = tracks,
                    currentTrackId =
                        content?.track?.id,
                    onTrackClick =
                        onTrackClick,
                    modifier =
                        Modifier.weight(1f)
                )
            }
        }

        if (content != null) {
            Column(
                modifier = Modifier
                    .widthIn(
                        min = 280.dp,
                        max = 340.dp
                    )
                    .fillMaxHeight(),
                verticalArrangement =
                    Arrangement.Center
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
    val backDescription =
        stringResource(
            R.string.back_to_player
        )

    Row(
        modifier =
            Modifier.fillMaxWidth(),
        verticalAlignment =
            Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(
                    RoundedCornerShape(8.dp)
                )
                .semantics {
                    role = Role.Button

                    contentDescription =
                        backDescription
                }
                .clickable(
                    role = Role.Button,
                    onClick = onBack
                ),
            contentAlignment =
                Alignment.Center
        ) {
            Text(
                text = "<",
                style =
                    MaterialTheme.typography.titleLarge,
                fontWeight =
                    FontWeight.Bold,
                color =
                    MaterialTheme.colorScheme.onBackground
            )
        }

        Spacer(
            modifier = Modifier.width(4.dp)
        )

        Text(
            text =
                stringResource(
                    R.string.playlist
                ),
            style =
                MaterialTheme.typography.titleLarge,
            color =
                MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
private fun PlaylistTitle(
    trackCount: Int,
    compact: Boolean = false
) {
    Text(
        text =
            stringResource(
                R.string.my_music
            ),
        style =
            if (compact) {
                MaterialTheme.typography.titleLarge
            } else {
                MaterialTheme.typography.headlineMedium
            },
        color =
            MaterialTheme.colorScheme.onBackground
    )

    Spacer(
        modifier = Modifier.height(
            if (compact) {
                1.dp
            } else {
                4.dp
            }
        )
    )

    Text(
        text =
            pluralStringResource(
                id = R.plurals.track_count,
                count = trackCount,
                trackCount
            ),
        style =
            MaterialTheme.typography.bodyMedium,
        color =
            MaterialTheme.colorScheme.onBackground
    )
}

@Composable
private fun TrackList(
    tracks: List<Track>,
    currentTrackId: Int?,
    onTrackClick: (Track) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier =
            modifier.fillMaxWidth(),
        verticalArrangement =
            Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(
            items = tracks,
            key = { _, track ->
                track.id
            }
        ) { index, track ->

            TrackItem(
                number = index + 1,
                track = track,
                selected =
                    track.id == currentTrackId,
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
    val trackDescription =
        stringResource(
            R.string.track_description,
            track.title,
            track.artist,
            track.duration
        )

    val selectedDescription =
        if (selected) {
            stringResource(
                R.string.currently_playing
            )
        } else {
            stringResource(
                R.string.not_selected
            )
        }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(8.dp)
            )
            .background(
                if (selected) {
                    MaterialTheme
                        .colorScheme
                        .primary
                        .copy(
                            alpha = 0.12f
                        )
                } else {
                    MaterialTheme
                        .colorScheme
                        .surface
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
                        MaterialTheme
                            .colorScheme
                            .primary
                    } else {
                        MaterialTheme
                            .colorScheme
                            .outline
                    },
                shape =
                    RoundedCornerShape(8.dp)
            )
            .semantics(
                mergeDescendants = true
            ) {
                role = Role.Button

                contentDescription =
                    trackDescription

                stateDescription =
                    selectedDescription
            }
            .clickable(
                role = Role.Button,
                onClick = onClick
            )
            .padding(10.dp),
        verticalAlignment =
            Alignment.CenterVertically
    ) {
        Text(
            text = number
                .toString()
                .padStart(
                    length = 2,
                    padChar = '0'
                ),
            modifier =
                Modifier.width(32.dp),
            style =
                MaterialTheme.typography.labelSmall,
            fontWeight =
                FontWeight.Bold,
            color =
                if (selected) {
                    MaterialTheme
                        .colorScheme
                        .primary
                } else {
                    MaterialTheme
                        .colorScheme
                        .onSurfaceVariant
                }
        )

        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(
                    RoundedCornerShape(7.dp)
                )
                .background(
                    MaterialTheme
                        .colorScheme
                        .primary
                        .copy(
                            alpha = 0.12f
                        )
                ),
            contentAlignment =
                Alignment.Center
        ) {
            Text(
                text =
                    number.toString(),
                style =
                    MaterialTheme
                        .typography
                        .labelMedium,
                fontWeight =
                    FontWeight.Bold,
                color =
                    MaterialTheme
                        .colorScheme
                        .primary
            )
        }

        Spacer(
            modifier = Modifier.width(10.dp)
        )

        Column(
            modifier =
                Modifier.weight(1f)
        ) {
            Text(
                text = track.title,
                maxLines = 1,
                overflow =
                    TextOverflow.Ellipsis,
                style =
                    MaterialTheme
                        .typography
                        .bodyLarge,
                fontWeight =
                    if (selected) {
                        FontWeight.Bold
                    } else {
                        FontWeight.Medium
                    },
                color =
                    MaterialTheme
                        .colorScheme
                        .onSurface
            )

            Spacer(
                modifier =
                    Modifier.height(2.dp)
            )

            Text(
                text = track.artist,
                maxLines = 1,
                overflow =
                    TextOverflow.Ellipsis,
                style =
                    MaterialTheme
                        .typography
                        .bodyMedium,
                color =
                    MaterialTheme
                        .colorScheme
                        .onSurfaceVariant
            )
        }

        if (selected) {
            Text(
                text =
                    stringResource(
                        R.string.playing
                    ),
                modifier =
                    Modifier.padding(
                        horizontal = 8.dp
                    ),
                style =
                    MaterialTheme
                        .typography
                        .labelSmall,
                fontWeight =
                    FontWeight.Bold,
                color =
                    MaterialTheme
                        .colorScheme
                        .primary
            )
        }

        Text(
            text = track.duration,
            style =
                MaterialTheme
                    .typography
                    .labelSmall,
            color =
                MaterialTheme
                    .colorScheme
                    .onSurfaceVariant
        )
    }
}

@Composable
private fun EmptyPlaylist(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(12.dp)
            )
            .background(
                MaterialTheme.colorScheme.surface
            )
            .border(
                width = 1.dp,
                color =
                    MaterialTheme.colorScheme.outline,
                shape =
                    RoundedCornerShape(12.dp)
            )
            .padding(24.dp),
        contentAlignment =
            Alignment.Center
    ) {
        Column(
            horizontalAlignment =
                Alignment.CenterHorizontally
        ) {
            Text(
                text =
                    stringResource(
                        R.string.empty_playlist
                    ),
                style =
                    MaterialTheme
                        .typography
                        .titleMedium,
                color =
                    MaterialTheme
                        .colorScheme
                        .onSurface
            )

            Spacer(
                modifier =
                    Modifier.height(6.dp)
            )

            Text(
                text =
                    stringResource(
                        R.string.empty_playlist_description
                    ),
                style =
                    MaterialTheme
                        .typography
                        .bodyMedium,
                color =
                    MaterialTheme
                        .colorScheme
                        .onSurfaceVariant
            )
        }
    }
}

@Composable
private fun MiniPlayer(
    state: PlayerUiState.Content,
    onPlayPause: () -> Unit,
    onNext: () -> Unit,
    onOpenPlayer: () -> Unit
) {
    val openPlayerDescription =
        stringResource(
            R.string.open_full_player
        )

    val playPauseDescription =
        if (state.isPlaying) {
            stringResource(
                R.string.pause_playback
            )
        } else {
            stringResource(
                R.string.start_playback
            )
        }

    val nextDescription =
        stringResource(
            R.string.next_track
        )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(10.dp)
            )
            .background(
                MaterialTheme.colorScheme.surface
            )
            .border(
                width = 1.dp,
                color =
                    MaterialTheme.colorScheme.outline,
                shape =
                    RoundedCornerShape(10.dp)
            )
            .padding(10.dp),
        verticalAlignment =
            Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(
                    RoundedCornerShape(7.dp)
                )
                .background(
                    MaterialTheme
                        .colorScheme
                        .primary
                        .copy(
                            alpha = 0.12f
                        )
                ),
            contentAlignment =
                Alignment.Center
        ) {
            Text(
                text = "MEGA",
                style =
                    MaterialTheme
                        .typography
                        .labelSmall,
                fontWeight =
                    FontWeight.Bold,
                color =
                    MaterialTheme
                        .colorScheme
                        .primary
            )
        }

        Spacer(
            modifier = Modifier.width(10.dp)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .sizeIn(
                    minHeight = 48.dp
                )
                .clip(
                    RoundedCornerShape(7.dp)
                )
                .semantics {
                    role = Role.Button

                    contentDescription =
                        openPlayerDescription
                }
                .clickable(
                    role = Role.Button,
                    onClick = onOpenPlayer
                ),
            verticalArrangement =
                Arrangement.Center
        ) {
            Text(
                text = state.track.title,
                maxLines = 1,
                overflow =
                    TextOverflow.Ellipsis,
                style =
                    MaterialTheme
                        .typography
                        .bodyLarge,
                fontWeight =
                    FontWeight.Bold,
                color =
                    MaterialTheme
                        .colorScheme
                        .onSurface
            )

            Text(
                text = state.track.artist,
                maxLines = 1,
                overflow =
                    TextOverflow.Ellipsis,
                style =
                    MaterialTheme
                        .typography
                        .bodyMedium,
                color =
                    MaterialTheme
                        .colorScheme
                        .onSurfaceVariant
            )
        }

        Spacer(
            modifier = Modifier.width(6.dp)
        )

        MiniPlayerButton(
            text =
                if (state.isPlaying) {
                    stringResource(
                        R.string.pause
                    )
                } else {
                    stringResource(
                        R.string.play
                    )
                },
            description =
                playPauseDescription,
            onClick =
                onPlayPause
        )

        Spacer(
            modifier = Modifier.width(6.dp)
        )

        MiniPlayerButton(
            text =
                stringResource(
                    R.string.next
                ),
            description =
                nextDescription,
            onClick =
                onNext
        )
    }
}

@Composable
private fun LandscapeMiniPlayer(
    state: PlayerUiState.Content,
    onPlayPause: () -> Unit,
    onNext: () -> Unit,
    onOpenPlayer: () -> Unit
) {
    val playPauseDescription =
        if (state.isPlaying) {
            stringResource(
                R.string.pause_playback
            )
        } else {
            stringResource(
                R.string.start_playback
            )
        }

    val nextDescription =
        stringResource(
            R.string.next_track
        )

    val openDescription =
        stringResource(
            R.string.open_full_player
        )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(12.dp)
            )
            .background(
                MaterialTheme.colorScheme.surface
            )
            .border(
                width = 1.dp,
                color =
                    MaterialTheme.colorScheme.outline,
                shape =
                    RoundedCornerShape(12.dp)
            )
            .padding(12.dp),
        horizontalAlignment =
            Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(
                    RoundedCornerShape(10.dp)
                )
                .background(
                    MaterialTheme
                        .colorScheme
                        .primary
                        .copy(
                            alpha = 0.12f
                        )
                ),
            contentAlignment =
                Alignment.Center
        ) {
            Text(
                text = "MEGA",
                style =
                    MaterialTheme
                        .typography
                        .labelLarge,
                fontWeight =
                    FontWeight.Bold,
                color =
                    MaterialTheme
                        .colorScheme
                        .primary
            )
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = state.track.title,
            maxLines = 1,
            overflow =
                TextOverflow.Ellipsis,
            style =
                MaterialTheme
                    .typography
                    .titleSmall,
            fontWeight =
                FontWeight.Bold,
            color =
                MaterialTheme
                    .colorScheme
                    .onSurface
        )

        Text(
            text = state.track.artist,
            maxLines = 1,
            overflow =
                TextOverflow.Ellipsis,
            style =
                MaterialTheme
                    .typography
                    .bodySmall,
            color =
                MaterialTheme
                    .colorScheme
                    .onSurfaceVariant
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        Row(
            horizontalArrangement =
                Arrangement.spacedBy(8.dp)
        ) {
            MiniPlayerButton(
                text =
                    if (state.isPlaying) {
                        stringResource(
                            R.string.pause
                        )
                    } else {
                        stringResource(
                            R.string.play
                        )
                    },
                description =
                    playPauseDescription,
                onClick =
                    onPlayPause
            )

            MiniPlayerButton(
                text =
                    stringResource(
                        R.string.next
                    ),
                description =
                    nextDescription,
                onClick =
                    onNext
            )
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .sizeIn(
                    minHeight = 48.dp
                )
                .clip(
                    RoundedCornerShape(8.dp)
                )
                .border(
                    width = 1.dp,
                    color =
                        MaterialTheme
                            .colorScheme
                            .primary,
                    shape =
                        RoundedCornerShape(8.dp)
                )
                .semantics {
                    role = Role.Button

                    contentDescription =
                        openDescription
                }
                .clickable(
                    role = Role.Button,
                    onClick = onOpenPlayer
                ),
            contentAlignment =
                Alignment.Center
        ) {
            Text(
                text =
                    stringResource(
                        R.string.open_player
                    ),
                style =
                    MaterialTheme
                        .typography
                        .labelMedium,
                fontWeight =
                    FontWeight.Bold,
                color =
                    MaterialTheme
                        .colorScheme
                        .primary
            )
        }
    }
}

@Composable
private fun MiniPlayerButton(
    text: String,
    description: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .sizeIn(
                minWidth = 56.dp,
                minHeight = 48.dp
            )
            .clip(
                RoundedCornerShape(7.dp)
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
                horizontal = 8.dp
            ),
        contentAlignment =
            Alignment.Center
    ) {
        Text(
            text = text,
            style =
                MaterialTheme
                    .typography
                    .labelSmall,
            fontWeight =
                FontWeight.Bold,
            color =
                MaterialTheme
                    .colorScheme
                    .onPrimary
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
    val playPauseDescription =
        if (state.isPlaying) {
            stringResource(
                R.string.pause_playback
            )
        } else {
            stringResource(
                R.string.start_playback
            )
        }

    val nextDescription =
        stringResource(
            R.string.next_track
        )

    val openDescription =
        stringResource(
            R.string.open_full_player
        )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(14.dp)
            )
            .background(
                MaterialTheme.colorScheme.surface
            )
            .border(
                width = 1.dp,
                color =
                    MaterialTheme.colorScheme.outline,
                shape =
                    RoundedCornerShape(14.dp)
            )
            .padding(18.dp),
        horizontalAlignment =
            Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(150.dp)
                .clip(
                    RoundedCornerShape(12.dp)
                )
                .background(
                    MaterialTheme
                        .colorScheme
                        .primary
                        .copy(
                            alpha = 0.12f
                        )
                ),
            contentAlignment =
                Alignment.Center
        ) {
            Text(
                text = "MEGA",
                style =
                    MaterialTheme
                        .typography
                        .headlineMedium,
                fontWeight =
                    FontWeight.Bold,
                color =
                    MaterialTheme
                        .colorScheme
                        .primary
            )
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(
            text = state.track.title,
            maxLines = 1,
            overflow =
                TextOverflow.Ellipsis,
            style =
                MaterialTheme
                    .typography
                    .titleMedium,
            fontWeight =
                FontWeight.Bold,
            color =
                MaterialTheme
                    .colorScheme
                    .onSurface
        )

        Spacer(
            modifier = Modifier.height(4.dp)
        )

        Text(
            text = state.track.artist,
            maxLines = 1,
            overflow =
                TextOverflow.Ellipsis,
            style =
                MaterialTheme
                    .typography
                    .bodyMedium,
            color =
                MaterialTheme
                    .colorScheme
                    .onSurfaceVariant
        )

        Spacer(
            modifier = Modifier.height(18.dp)
        )

        Row(
            horizontalArrangement =
                Arrangement.spacedBy(8.dp)
        ) {
            MiniPlayerButton(
                text =
                    if (state.isPlaying) {
                        stringResource(
                            R.string.pause
                        )
                    } else {
                        stringResource(
                            R.string.play
                        )
                    },
                description =
                    playPauseDescription,
                onClick =
                    onPlayPause
            )

            MiniPlayerButton(
                text =
                    stringResource(
                        R.string.next
                    ),
                description =
                    nextDescription,
                onClick =
                    onNext
            )
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .sizeIn(
                    minHeight = 48.dp
                )
                .clip(
                    RoundedCornerShape(8.dp)
                )
                .border(
                    width = 2.dp,
                    color =
                        MaterialTheme
                            .colorScheme
                            .primary,
                    shape =
                        RoundedCornerShape(8.dp)
                )
                .semantics {
                    role = Role.Button

                    contentDescription =
                        openDescription
                }
                .clickable(
                    role = Role.Button,
                    onClick = onOpenPlayer
                ),
            contentAlignment =
                Alignment.Center
        ) {
            Text(
                text =
                    stringResource(
                        R.string.open_player
                    ),
                style =
                    MaterialTheme
                        .typography
                        .labelLarge,
                fontWeight =
                    FontWeight.Bold,
                color =
                    MaterialTheme
                        .colorScheme
                        .primary
            )
        }
    }
}

private val previewTracks =
    listOf(
        Track(
            id = 1,
            title = "Windows XP",
            artist = "window",
            duration = "00:04",
            audioResId = 0
        ),
        Track(
            id = 2,
            title = "Lonely Day",
            artist = "System of a Down",
            duration = "02:47",
            audioResId = 0
        ),
        Track(
            id = 3,
            title = "Other People",
            artist = "LP",
            duration = "03:48",
            audioResId = 0
        )
    )

private val previewPlaylistState =
    PlayerUiState.Content(
        track =
            previewTracks.first(),
        isPlaying = true,
        positionMs = 2000L,
        durationMs = 4000L
    )

@Preview(
    name = "Playlist Russian Portrait",
    showBackground = true,
    locale = "ru",
    widthDp = 390,
    heightDp = 800
)
@Composable
private fun PlaylistRussianPortraitPreview() {
    MegaX3PlayerTheme(
        darkTheme = false
    ) {
        PlaylistScreen(
            tracks =
                previewTracks,
            playerState =
                previewPlaylistState,
            windowWidthSizeClass =
                WindowWidthSizeClass.Compact,
            windowHeightSizeClass =
                WindowHeightSizeClass.Medium,
            onBack = {},
            onTrackClick = {},
            onPlayPause = {},
            onNext = {},
            onOpenPlayer = {}
        )
    }
}

@Preview(
    name = "Playlist English Portrait",
    showBackground = true,
    locale = "en",
    widthDp = 390,
    heightDp = 800
)
@Composable
private fun PlaylistEnglishPortraitPreview() {
    MegaX3PlayerTheme(
        darkTheme = false
    ) {
        PlaylistScreen(
            tracks =
                previewTracks,
            playerState =
                previewPlaylistState,
            windowWidthSizeClass =
                WindowWidthSizeClass.Compact,
            windowHeightSizeClass =
                WindowHeightSizeClass.Medium,
            onBack = {},
            onTrackClick = {},
            onPlayPause = {},
            onNext = {},
            onOpenPlayer = {}
        )
    }
}

@Preview(
    name = "Playlist Landscape",
    showBackground = true,
    locale = "ru",
    widthDp = 800,
    heightDp = 360
)
@Composable
private fun PlaylistLandscapePreview() {
    MegaX3PlayerTheme(
        darkTheme = false
    ) {
        PlaylistScreen(
            tracks =
                previewTracks,
            playerState =
                previewPlaylistState,
            windowWidthSizeClass =
                WindowWidthSizeClass.Medium,
            windowHeightSizeClass =
                WindowHeightSizeClass.Compact,
            onBack = {},
            onTrackClick = {},
            onPlayPause = {},
            onNext = {},
            onOpenPlayer = {}
        )
    }
}

@Preview(
    name = "Playlist Landscape Dark",
    showBackground = true,
    locale = "en",
    uiMode =
        Configuration.UI_MODE_NIGHT_YES,
    widthDp = 800,
    heightDp = 360
)
@Composable
private fun PlaylistLandscapeDarkPreview() {
    MegaX3PlayerTheme(
        darkTheme = true
    ) {
        PlaylistScreen(
            tracks =
                previewTracks,
            playerState =
                previewPlaylistState,
            windowWidthSizeClass =
                WindowWidthSizeClass.Medium,
            windowHeightSizeClass =
                WindowHeightSizeClass.Compact,
            onBack = {},
            onTrackClick = {},
            onPlayPause = {},
            onNext = {},
            onOpenPlayer = {}
        )
    }
}

@Preview(
    name = "Playlist Tablet",
    showBackground = true,
    locale = "en",
    widthDp = 900,
    heightDp = 600
)
@Composable
private fun PlaylistTabletPreview() {
    MegaX3PlayerTheme(
        darkTheme = false
    ) {
        PlaylistScreen(
            tracks =
                previewTracks,
            playerState =
                previewPlaylistState,
            windowWidthSizeClass =
                WindowWidthSizeClass.Expanded,
            windowHeightSizeClass =
                WindowHeightSizeClass.Medium,
            onBack = {},
            onTrackClick = {},
            onPlayPause = {},
            onNext = {},
            onOpenPlayer = {}
        )
    }
}

@Preview(
    name = "Playlist Empty",
    showBackground = true,
    locale = "ru",
    widthDp = 390,
    heightDp = 800
)
@Composable
private fun PlaylistEmptyPreview() {
    MegaX3PlayerTheme(
        darkTheme = false
    ) {
        PlaylistScreen(
            tracks =
                emptyList(),
            playerState =
                PlayerUiState.Empty,
            windowWidthSizeClass =
                WindowWidthSizeClass.Compact,
            windowHeightSizeClass =
                WindowHeightSizeClass.Medium,
            onBack = {},
            onTrackClick = {},
            onPlayPause = {},
            onNext = {},
            onOpenPlayer = {}
        )
    }
}