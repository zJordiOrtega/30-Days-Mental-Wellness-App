package com.example.a30days.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Mental(
    @StringRes val dayNumber: Int,
    @StringRes val title: Int,
    @DrawableRes val image: Int,
    @StringRes val description: Int,
)
