package com.tinaciousdesign.interviews.stocks.db.stock

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface StockDao {

    @Query("SELECT * FROM stocks")
    suspend fun getAll(): List<StockEntity>

    @Query("SELECT * FROM stocks")
    fun stocksFlow(): Flow<List<StockEntity>>

    @Query("SELECT * FROM stocks WHERE LOWER(ticker) LIKE '%' || :query || '%' OR LOWER(name) LIKE '%' || :query || '%'")
    suspend fun find(query: String): List<StockEntity>

    @Query("SELECT * FROM stocks WHERE LOWER(ticker) LIKE '%' || :query || '%' OR LOWER(name) LIKE '%' || :query || '%'")
    fun findStocksFlow(query: String): Flow<List<StockEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(stock: StockEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(stocks: List<StockEntity>)

    @Query("DELETE FROM stocks")
    suspend fun deleteAll()
}
