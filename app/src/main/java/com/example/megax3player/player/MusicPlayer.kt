package com.example.megax3player.player

import android.content.Context
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.example.megax3player.model.Track

class MusicPlayer(
    context: Context
) {
    private val appContext = context.applicationContext

    val player: ExoPlayer = ExoPlayer.Builder(appContext).build()

    fun setTracks(
        tracks: List<Track>,
        startIndex: Int = 0
    ) {
        val items = tracks.map { track ->
            val uri = "android.resource://${appContext.packageName}/${track.audioResId}".toUri()

            MediaItem.Builder()
                .setMediaId(track.id.toString())
                .setUri(uri)
                .build()
        }

        player.setMediaItems(items, startIndex, 0L)
        player.prepare()
    }

    fun playPause() {
        if (player.isPlaying) {
            player.pause()
        } else {
            player.play()
        }
    }

    fun next() {
        if (player.hasNextMediaItem()) {
            player.seekToNextMediaItem()
            player.play()
        }
    }

    fun previous() {
        if (player.hasPreviousMediaItem()) {
            player.seekToPreviousMediaItem()
            player.play()
        } else {
            player.seekTo(0)
        }
    }

    fun seekTo(position: Long) {
        player.seekTo(position)
    }

    fun setShuffle(enabled: Boolean) {
        player.shuffleModeEnabled = enabled
    }

    fun setRepeat(enabled: Boolean) {
        player.repeatMode = if (enabled) {
            Player.REPEAT_MODE_ONE
        } else {
            Player.REPEAT_MODE_OFF
        }
    }

    fun release() {
        player.release()
    }
}