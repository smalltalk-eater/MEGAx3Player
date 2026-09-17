package com.example.megax3player.player

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.Player
import com.example.megax3player.data.TrackRepository
import com.example.megax3player.model.Track
import com.example.megax3player.ui.PlayerUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class PlayerViewModel(application: Application) : AndroidViewModel(application) {

    private val tracks = TrackRepository.tracks
    private val musicPlayer = MusicPlayer(application)

    private val _uiState = MutableStateFlow<PlayerUiState>(
        if (tracks.isEmpty()) {
            PlayerUiState.Empty
        } else {
            PlayerUiState.Loading
        }
    )

    val uiState: StateFlow<PlayerUiState> = _uiState.asStateFlow()

    init {
        if (tracks.isNotEmpty()) {
            musicPlayer.setTracks(tracks)

            musicPlayer.player.addListener(
                object : Player.Listener {

                    override fun onPlaybackStateChanged(playbackState: Int) {
                        updateState()
                    }

                    override fun onIsPlayingChanged(isPlaying: Boolean) {
                        updateState()
                    }

                    override fun onMediaItemTransition(
                        mediaItem: androidx.media3.common.MediaItem?,
                        reason: Int
                    ) {
                        updateState()
                    }
                }
            )

            startProgressUpdates()
        }
    }

    private fun startProgressUpdates() {
        viewModelScope.launch {
            while (isActive) {
                updateState()
                delay(500)
            }
        }
    }

    private fun updateState() {
        if (tracks.isEmpty()) {
            _uiState.value = PlayerUiState.Empty
            return
        }

        val player = musicPlayer.player

        val index = player.currentMediaItemIndex
            .coerceIn(0, tracks.lastIndex)

        val track = tracks[index]

        val duration = if (player.duration > 0) {
            player.duration
        } else {
            0L
        }

        val position = player.currentPosition.coerceAtLeast(0L)

        _uiState.value = PlayerUiState.Content(
            track = track,
            isPlaying = player.isPlaying,
            positionMs = position,
            durationMs = duration,
            shuffle = player.shuffleModeEnabled,
            repeat = player.repeatMode == Player.REPEAT_MODE_ONE
        )
    }

    fun playPause() {
        musicPlayer.playPause()
        updateState()
    }

    fun next() {
        musicPlayer.next()
        updateState()
    }

    fun previous() {
        musicPlayer.previous()
        updateState()
    }

    fun seekTo(progress: Float) {
        val state = _uiState.value

        if (state !is PlayerUiState.Content) {
            return
        }

        if (state.durationMs <= 0) {
            return
        }

        val position = (state.durationMs * progress)
            .toLong()

        musicPlayer.seekTo(position)

        updateState()
    }

    fun toggleShuffle() {
        val enabled = !musicPlayer.player.shuffleModeEnabled

        musicPlayer.setShuffle(enabled)

        updateState()
    }

    fun toggleRepeat() {
        val enabled =
            musicPlayer.player.repeatMode != Player.REPEAT_MODE_ONE

        musicPlayer.setRepeat(enabled)

        updateState()
    }

    fun playTrack(track: Track) {
        val index = tracks.indexOfFirst {
            it.id == track.id
        }

        if (index == -1) {
            return
        }

        musicPlayer.player.seekToDefaultPosition(index)
        musicPlayer.player.play()

        updateState()
    }

    fun getTracks(): List<Track> {
        return tracks
    }

    override fun onCleared() {
        super.onCleared()

        musicPlayer.release()
    }
}