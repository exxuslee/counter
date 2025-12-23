package com.exxlexxlee.data.mapper

import com.exxlexxlee.data.local.entities.CountEntity
import com.exxlexxlee.domain.model.Count

fun Count.toData(): CountEntity {
    return CountEntity(
        id = this.id,
        name = this.name,
        start = this.start,
        current = this.current,
        increment = this.increment,
        operator = this.operator,
        icon = this.icon,
        color = this.color,
        photos = this.photos,
        active = this.active,
    )
}

fun CountEntity.toDomain(): Count {
    return Count(
        id = this.id,
        name = this.name,
        start = this.start,
        current = this.current,
        increment = this.increment,
        operator = this.operator,
        icon = this.icon,
        color = this.color,
        photos = this.photos,
        active = this.active,
    )
}