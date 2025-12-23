package com.exxlexxlee.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.exxlexxlee.data.local.entities.TokenEntity

@Dao
interface TokensDAO {
    @Query("SELECT * FROM Tokens")
    suspend fun tokens(): List<TokenEntity>

    @Query("SELECT * FROM Tokens WHERE symbol = :symbol")
    suspend fun token(symbol: String): List<TokenEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(prices: List<TokenEntity>)
}
