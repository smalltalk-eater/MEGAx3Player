package com.example.megax3player.model

import androidx.annotation.DrawableRes
import androidx.annotation.RawRes

data class Track(
    val id: Int,
    val title: String,
    val artist: String,
    val duration: String,
    @RawRes val audioResId: Int,
    @DrawableRes val coverResId: Int = 0
)