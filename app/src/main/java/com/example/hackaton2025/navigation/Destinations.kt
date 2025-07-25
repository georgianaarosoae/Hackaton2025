package com.example.hackaton2025.navigation

import kotlinx.serialization.Serializable

sealed class Destinations {

    @Serializable
    object OverviewDestination : Destinations()

    @Serializable
    object WishlistDestination : Destinations()

    @Serializable
    object TasksDestination : Destinations()

    @Serializable
    object GamesDestination : Destinations()

    @Serializable
    object HangManDestionation : Destinations()

    @Serializable
    object MemoryGameDestination : Destinations()

}