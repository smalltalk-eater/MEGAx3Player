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
import androidx.compose.ui.unit.dp
import com.example.megax3player.R
import com.example.megax3player.model.Track

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
    if (windowHeightSizeClass == WindowHeightSizeClass.Compact) {
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
        return
    }

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
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .padding(horizontal = 18.dp, vertical = 14.dp)
    ) {
        PlaylistHeader(onBack)

        Spacer(Modifier.height(18.dp))

        PlaylistTitle(tracks.size)

        Spacer(Modifier.height(16.dp))

        PlaylistContent(
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
private fun LandscapePlaylistScreen(
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
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1.4f)
                .fillMaxHeight()
        ) {
            PlaylistHeader(onBack)

            Spacer(Modifier.height(4.dp))

            PlaylistTitle(
                trackCount = tracks.size,
                compact = true
            )

            Spacer(Modifier.height(8.dp))

            PlaylistContent(
                tracks = tracks,
                currentTrackId = content?.track?.id,
                onTrackClick = onTrackClick,
                modifier = Modifier.weight(1f)
            )
        }

        if (content != null) {
            Box(
                modifier = Modifier
                    .weight(0.8f)
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                CurrentTrackPanel(
                    state = content,
                    compact = true,
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
    val content = playerState as? PlayerUiState.Content

    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .padding(24.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1.4f)
                .fillMaxHeight()
        ) {
            PlaylistHeader(onBack)

            Spacer(Modifier.height(18.dp))

            PlaylistTitle(tracks.size)

            Spacer(Modifier.height(16.dp))

            PlaylistContent(
                tracks = tracks,
                currentTrackId = content?.track?.id,
                onTrackClick = onTrackClick,
                modifier = Modifier.weight(1f)
            )
        }

        if (content != null) {
            Box(
                modifier = Modifier
                    .widthIn(
                        min = 280.dp,
                        max = 340.dp
                    )
                    .fillMaxHeight(),
                contentAlignment = Alignment.Center
            ) {
                CurrentTrackPanel(
                    state = content,
                    compact = false,
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
    val description =
        stringResource(R.string.back_to_player)

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(8.dp))
                .semantics {
                    role = Role.Button
                    contentDescription = description
                }
                .clickable(
                    role = Role.Button,
                    onClick = onBack
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "<",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Spacer(Modifier.width(4.dp))

        Text(
            text = stringResource(R.string.playlist),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
private fun PlaylistTitle(
    trackCount: Int,
    compact: Boolean = false
) {
    Text(
        text = stringResource(R.string.my_music),
        style =
            if (compact) {
                MaterialTheme.typography.titleLarge
            } else {
                MaterialTheme.typography.headlineMedium
            },
        color = MaterialTheme.colorScheme.onBackground
    )

    Spacer(
        Modifier.height(
            if (compact) 1.dp else 4.dp
        )
    )

    Text(
        text = pluralStringResource(
            id = R.plurals.track_count,
            count = trackCount,
            trackCount
        ),
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onBackground
    )
}

@Composable
private fun PlaylistContent(
    tracks: List<Track>,
    currentTrackId: Int?,
    onTrackClick: (Track) -> Unit,
    modifier: Modifier = Modifier
) {
    if (tracks.isEmpty()) {
        EmptyPlaylist(
            modifier = modifier
        )
    } else {
        TrackList(
            tracks = tracks,
            currentTrackId = currentTrackId,
            onTrackClick = onTrackClick,
            modifier = modifier
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
            .clip(RoundedCornerShape(8.dp))
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
                    if (selected) 2.dp else 1.dp,
                color =
                    if (selected) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.outline
                    },
                shape = RoundedCornerShape(8.dp)
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
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(7.dp))
                .background(
                    MaterialTheme.colorScheme.primary
                        .copy(alpha = 0.12f)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text =
                    number.toString()
                        .padStart(2, '0'),
                style =
                    MaterialTheme.typography.labelMedium,
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
                style =
                    MaterialTheme.typography.bodyLarge,
                fontWeight =
                    if (selected) {
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
                style =
                    MaterialTheme.typography.bodyMedium,
                color =
                    MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        if (selected) {
            Text(
                text =
                    stringResource(R.string.playing),
                modifier =
                    Modifier.padding(horizontal = 8.dp),
                maxLines = 1,
                style =
                    MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Text(
            text = track.duration,
            style =
                MaterialTheme.typography.labelSmall,
            color =
                MaterialTheme.colorScheme.onSurfaceVariant
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
            .clip(RoundedCornerShape(12.dp))
            .background(
                MaterialTheme.colorScheme.surface
            )
            .border(
                1.dp,
                MaterialTheme.colorScheme.outline,
                RoundedCornerShape(12.dp)
            )
            .padding(24.dp),
        contentAlignment = Alignment.Center
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
                    MaterialTheme.typography.titleMedium,
                color =
                    MaterialTheme.colorScheme.onSurface
            )

            Spacer(Modifier.height(6.dp))

            Text(
                text =
                    stringResource(
                        R.string.empty_playlist_description
                    ),
                style =
                    MaterialTheme.typography.bodyMedium,
                color =
                    MaterialTheme.colorScheme.onSurfaceVariant
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
    val openDescription =
        stringResource(
            R.string.open_full_player
        )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(
                MaterialTheme.colorScheme.surface
            )
            .border(
                1.dp,
                MaterialTheme.colorScheme.outline,
                RoundedCornerShape(12.dp)
            )
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .sizeIn(minHeight = 48.dp)
                .clip(RoundedCornerShape(7.dp))
                .semantics {
                    role = Role.Button
                    contentDescription =
                        openDescription
                }
                .clickable(
                    role = Role.Button,
                    onClick = onOpenPlayer
                ),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = state.track.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style =
                    MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color =
                    MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = state.track.artist,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style =
                    MaterialTheme.typography.bodyMedium,
                color =
                    MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Spacer(Modifier.width(8.dp))

        PlayerActionButton(
            text =
                if (state.isPlaying) {
                    stringResource(R.string.pause)
                } else {
                    stringResource(R.string.play)
                },
            description =
                if (state.isPlaying) {
                    stringResource(
                        R.string.pause_playback
                    )
                } else {
                    stringResource(
                        R.string.start_playback
                    )
                },
            onClick = onPlayPause
        )

        Spacer(Modifier.width(6.dp))

        PlayerActionButton(
            text =
                stringResource(R.string.next),
            description =
                stringResource(R.string.next_track),
            onClick = onNext
        )
    }
}

@Composable
private fun CurrentTrackPanel(
    state: PlayerUiState.Content,
    compact: Boolean,
    onPlayPause: () -> Unit,
    onNext: () -> Unit,
    onOpenPlayer: () -> Unit
) {
    val openDescription =
        stringResource(
            R.string.open_full_player
        )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(
                MaterialTheme.colorScheme.surface
            )
            .border(
                1.dp,
                MaterialTheme.colorScheme.outline,
                RoundedCornerShape(12.dp)
            )
            .padding(
                if (compact) 12.dp else 20.dp
            ),
        horizontalAlignment =
            Alignment.CenterHorizontally
    ) {
        Text(
            text = state.track.title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style =
                if (compact) {
                    MaterialTheme.typography.titleMedium
                } else {
                    MaterialTheme.typography.titleLarge
                },
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(Modifier.height(4.dp))

        Text(
            text = state.track.artist,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style =
                MaterialTheme.typography.bodyMedium,
            color =
                MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(
            Modifier.height(
                if (compact) 10.dp else 18.dp
            )
        )

        Row(
            horizontalArrangement =
                Arrangement.spacedBy(8.dp)
        ) {
            PlayerActionButton(
                text =
                    if (state.isPlaying) {
                        stringResource(R.string.pause)
                    } else {
                        stringResource(R.string.play)
                    },
                description =
                    if (state.isPlaying) {
                        stringResource(
                            R.string.pause_playback
                        )
                    } else {
                        stringResource(
                            R.string.start_playback
                        )
                    },
                onClick = onPlayPause
            )

            PlayerActionButton(
                text =
                    stringResource(R.string.next),
                description =
                    stringResource(
                        R.string.next_track
                    ),
                onClick = onNext
            )
        }

        Spacer(Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .sizeIn(minHeight = 48.dp)
                .clip(RoundedCornerShape(8.dp))
                .border(
                    1.dp,
                    MaterialTheme.colorScheme.primary,
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
            contentAlignment = Alignment.Center
        ) {
            Text(
                text =
                    stringResource(
                        R.string.open_player
                    ),
                style =
                    MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color =
                    MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun PlayerActionButton(
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
            .clip(RoundedCornerShape(7.dp))
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
            .padding(horizontal = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            maxLines = 1,
            style =
                MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color =
                MaterialTheme.colorScheme.onPrimary
        )
    }
}