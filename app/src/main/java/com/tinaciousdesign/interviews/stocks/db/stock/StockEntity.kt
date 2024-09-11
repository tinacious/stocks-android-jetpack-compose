package com.tinaciousdesign.interviews.stocks.db.stock

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.tinaciousdesign.interviews.stocks.models.Stock

@Entity(
    tableName = "stocks",
    indices = [
        Index(value = ["ticker"], unique = true)
    ]
)
data class StockEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "ticker") val ticker: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "price") val price: Double,
) {
    companion object {
        fun fromStock(stock: Stock): StockEntity =
            StockEntity(
                ticker = stock.ticker,
                name = stock.name,
                price = stock.price,
            )
    }
}
