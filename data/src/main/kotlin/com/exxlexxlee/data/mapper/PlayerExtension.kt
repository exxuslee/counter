package com.exxlexxlee.data.mapper

import com.exxlexxlee.data.local.entities.PlayerEntity
import com.exxlexxlee.domain.model.Player

fun Player.toData(): PlayerEntity{
    return PlayerEntity(
        id = this.id,
        name = this.name,
        level = this.level,
        bonus = this.bonus,
        icon = this.icon,
        active = this.playing,
        reverseSex = this.reverseSex,
        startSex =  this.startSex
    )
}

fun PlayerEntity.toDomain(): Player{
    return Player(
        id = this.id,
        name = this.name,
        level = this.level,
        bonus = this.bonus,
        icon = this.icon,
        playing = this.active,
        reverseSex = this.reverseSex,
        startSex = this.startSex
    )
}