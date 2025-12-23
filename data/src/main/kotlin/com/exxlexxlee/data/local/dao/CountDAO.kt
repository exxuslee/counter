package com.exxlexxlee.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.exxlexxlee.data.local.entities.CountEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CountDAO {

    @Query("SELECT * FROM Counts WHERE id = :id")
    fun count(id: Int): CountEntity?

    @Query("SELECT * FROM Counts")
    suspend fun counts(): List<CountEntity>

    @Query("SELECT * FROM Counts")
    fun countsFlow(): Flow<List<CountEntity>>

    @Query("SELECT * FROM Counts WHERE active = 1")
    fun activeCountsFlow(): Flow<List<CountEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(count: CountEntity)

    @Query("SELECT MAX (id) FROM Counts")
    suspend fun lastId(): Int?

    @Query("DELETE FROM Counts WHERE id = :id")
    suspend fun delete(id: Int)
}
