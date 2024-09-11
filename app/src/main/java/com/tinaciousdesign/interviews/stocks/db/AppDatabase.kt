package com.tinaciousdesign.interviews.stocks.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tinaciousdesign.interviews.stocks.db.stock.StockDao
import com.tinaciousdesign.interviews.stocks.db.stock.StockEntity

@Database(
    entities = [
        StockEntity::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun stocks(): StockDao
}
