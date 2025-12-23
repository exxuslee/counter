package com.exxlexxlee.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.exxlexxlee.data.local.dao.PlayerDAO
import com.exxlexxlee.data.local.dao.TokensDAO
import com.exxlexxlee.data.local.entities.PlayerEntity
import com.exxlexxlee.data.local.entities.TokenEntity


@Database(entities = [PlayerEntity::class, TokenEntity::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val playerDAO: PlayerDAO
    abstract val tokensDAO: TokensDAO
}