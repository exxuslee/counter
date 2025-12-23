package com.exxlexxlee.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.exxlexxlee.data.local.dao.CountDAO
import com.exxlexxlee.data.local.dao.TokensDAO
import com.exxlexxlee.data.local.entities.CountEntity
import com.exxlexxlee.data.local.entities.TokenEntity


@Database(entities = [CountEntity::class, TokenEntity::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val countDAO: CountDAO
    abstract val tokensDAO: TokensDAO
}