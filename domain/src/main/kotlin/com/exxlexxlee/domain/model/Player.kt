package com.exxlexxlee.domain.model

data class Player(
    val id: Int = 0,
    val name: String = "Player",
    val level: Int = 1,
    val bonus: Int = 0,
    val icon: Int = 0,
    val playing: Boolean = true,
    val reverseSex: Boolean = false,
    val startSex: Boolean = false,
)