package com.tinaciousdesign.interviews.stocks.db.stock

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.tinaciousdesign.interviews.stocks.db.AppDatabase
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class StockDaoTest {

    private lateinit var stockDao: StockDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        stockDao = db.stocks()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertStocksAndGetAllStocks(): Unit = runTest {
        assertEquals(emptyList<StockEntity>(), stockDao.getAll())

        stockDao.insertAll(
            listOf(
                StockEntity(ticker ="FFF", name = "FFF Co", price = 100.0),
                StockEntity(ticker ="DDD", name = "DDD Co", price = 100.0),
                StockEntity(ticker ="CCC", name = "CCC Co", price = 100.0),
                StockEntity(ticker ="AB", name = "Absolute", price = 100.0),
                StockEntity(ticker ="ABC", name = "ABC Industries", price = 100.0),
                StockEntity(ticker ="XYZ", name = "XYZ and ABC", price = 100.0),
            )
        )

        val result = stockDao.getAll()

        assertEquals(
            listOf(
                StockEntity(id = 1, ticker ="FFF", name = "FFF Co", price = 100.0),
                StockEntity(id = 2, ticker ="DDD", name = "DDD Co", price = 100.0),
                StockEntity(id = 3, ticker ="CCC", name = "CCC Co", price = 100.0),
                StockEntity(id = 4, ticker ="AB", name = "Absolute", price = 100.0),
                StockEntity(id = 5, ticker ="ABC", name = "ABC Industries", price = 100.0),
                StockEntity(id = 6, ticker ="XYZ", name = "XYZ and ABC", price = 100.0),
            ),
            result,
        )
    }

    @Test
    @Throws(Exception::class)
    fun searchStocks(): Unit = runTest {
        assertEquals(emptyList<StockEntity>(), stockDao.getAll())

        stockDao.insertAll(
            listOf(
                StockEntity(ticker = "FFF", name = "FFF Co", price = 100.0),
                StockEntity(ticker = "DDD", name = "DDD Co", price = 100.0),
                StockEntity(ticker = "CCC", name = "CCC Co", price = 100.0),
                StockEntity(ticker = "AB", name = "Absolute", price = 100.0),
                StockEntity(ticker = "ABC", name = "ABC Industries", price = 100.0),
                StockEntity(ticker = "XYZ", name = "XYZ and ABC", price = 100.0),
            )
        )

        val result = stockDao.find("ab")

        assertEquals(
            listOf(
                StockEntity(id = 4, ticker ="AB", name = "Absolute", price = 100.0),
                StockEntity(id = 5, ticker ="ABC", name = "ABC Industries", price = 100.0),
                StockEntity(id = 6, ticker ="XYZ", name = "XYZ and ABC", price = 100.0),
            ),
            result,
        )
    }

    @Test
    @Throws(Exception::class)
    fun searchStocksWithFlow(): Unit = runTest {
        assertEquals(emptyList<StockEntity>(), stockDao.getAll())

        stockDao.insertAll(
            listOf(
                StockEntity(ticker = "FFF", name = "FFF Co", price = 100.0),
                StockEntity(ticker = "DDD", name = "DDD Co", price = 100.0),
                StockEntity(ticker = "CCC", name = "CCC Co", price = 100.0),
                StockEntity(ticker = "AB", name = "Absolute", price = 100.0),
                StockEntity(ticker = "ABC", name = "ABC Industries", price = 100.0),
                StockEntity(ticker = "XYZ", name = "XYZ and ABC", price = 100.0),
            )
        )

        stockDao.findStocksFlow("abc").test {
            val result = awaitItem()

            assertEquals(
                listOf(
                    StockEntity(id = 5, ticker ="ABC", name = "ABC Industries", price = 100.0),
                    StockEntity(id = 6, ticker ="XYZ", name = "XYZ and ABC", price = 100.0),
                ),
                result,
            )
        }
    }
}
