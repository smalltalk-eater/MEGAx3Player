package com.example.megax3player.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModelProvider
import com.example.megax3player.data.ThemePreferences
import com.example.megax3player.player.PlayerViewModel
import com.example.megax3player.ui.PlayerScreen
import com.example.megax3player.ui.PlaylistScreen
import com.example.megax3player.ui.theme.MegaX3PlayerTheme
import kotlinx.coroutines.launch

private enum class Screen {
    PLAYER,
    PLAYLIST
}

class MainActivity : AppCompatActivity() {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val playerViewModel =
            ViewModelProvider(this)[PlayerViewModel::class.java]

        val themePreferences =
            ThemePreferences(applicationContext)

        setContent {
            val windowSizeClass =
                calculateWindowSizeClass(this)

            val systemDarkTheme =
                isSystemInDarkTheme()

            val savedDarkTheme by
            themePreferences.darkTheme.collectAsState(
                initial = null
            )

            val darkTheme =
                savedDarkTheme ?: systemDarkTheme

            val coroutineScope =
                rememberCoroutineScope()

            var screen by remember {
                mutableStateOf(Screen.PLAYER)
            }

            val playerState by
            playerViewModel.uiState.collectAsState()

            MegaX3PlayerTheme(
                darkTheme = darkTheme
            ) {
                AnimatedContent(
                    targetState = screen,
                    label = "screenTransition"
                ) { currentScreen ->

                    when (currentScreen) {
                        Screen.PLAYER -> {
                            PlayerScreen(
                                state = playerState,
                                darkTheme = darkTheme,
                                windowWidthSizeClass =
                                    windowSizeClass.widthSizeClass,

                                onDarkThemeChange = { newDarkTheme ->
                                    coroutineScope.launch {
                                        themePreferences.saveDarkTheme(
                                            darkTheme = newDarkTheme
                                        )
                                    }
                                },

                                onOpenPlaylist = {
                                    screen = Screen.PLAYLIST
                                },

                                onPlayPause = {
                                    playerViewModel.playPause()
                                },

                                onNext = {
                                    playerViewModel.next()
                                },

                                onPrevious = {
                                    playerViewModel.previous()
                                },

                                onSeek = {
                                    playerViewModel.seekTo(it)
                                },

                                onShuffle = {
                                    playerViewModel.toggleShuffle()
                                },

                                onRepeat = {
                                    playerViewModel.toggleRepeat()
                                }
                            )
                        }

                        Screen.PLAYLIST -> {
                            PlaylistScreen(
                                tracks =
                                    playerViewModel.getTracks(),

                                playerState =
                                    playerState,

                                windowWidthSizeClass =
                                    windowSizeClass.widthSizeClass,

                                onBack = {
                                    screen = Screen.PLAYER
                                },

                                onTrackClick = { track ->
                                    playerViewModel.playTrack(track)
                                },

                                onPlayPause = {
                                    playerViewModel.playPause()
                                },

                                onNext = {
                                    playerViewModel.next()
                                },

                                onOpenPlayer = {
                                    screen = Screen.PLAYER
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}