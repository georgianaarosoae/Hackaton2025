package com.example.hackaton2025.data.fakeData

import androidx.annotation.DrawableRes

data class WishlistData(
    val name: String,
    val price: Float,
    @DrawableRes val imageRes: Int,
    val priority: Int // setare prioritate
)