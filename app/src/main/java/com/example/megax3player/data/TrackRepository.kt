package com.example.megax3player.data

import com.example.megax3player.R
import com.example.megax3player.model.Track

object TrackRepository {

    val tracks = listOf(
        Track(
            id = 1,
            title = "Windows XP",
            artist = "window",
            duration = "00:04",
            audioResId = R.raw.windows_xp
        ),
        Track(
            id = 2,
            title = "Lonely Day",
            artist = "System of Down",
            duration = "02:47",
            audioResId = R.raw.lonely_day
        )
    )
}