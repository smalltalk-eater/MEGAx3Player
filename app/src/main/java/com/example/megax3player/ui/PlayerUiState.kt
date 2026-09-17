package com.example.megax3player.ui

import com.example.megax3player.model.Track

sealed interface PlayerUiState {

    data object Loading : PlayerUiState

    data object Empty : PlayerUiState

    data class Content(
        val track: Track,
        val isPlaying: Boolean = false,
        val positionMs: Long = 0L,
        val durationMs: Long = 0L,
        val shuffle: Boolean = false,
        val repeat: Boolean = false
    ) : PlayerUiState
}