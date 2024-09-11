package com.tinaciousdesign.interviews.stocks.repositories

import com.tinaciousdesign.interviews.stocks.db.stock.StockDao
import com.tinaciousdesign.interviews.stocks.db.stock.StockEntity
import com.tinaciousdesign.interviews.stocks.models.Stock
import com.tinaciousdesign.interviews.stocks.networking.ApiResult
import com.tinaciousdesign.interviews.stocks.networking.api.StocksApi
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class StocksRepositoryTest {
    @MockK
    lateinit var stocksApi: StocksApi

    @MockK
    lateinit var stockDao: StockDao

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @Test
    fun `fetch stocks fetches the stocks`(): Unit = runBlocking {
        val fetchedStocks = listOf(
            Stock("FFF", "FFF Co", 100.0),
            Stock("DDD", "DDD Co", 100.0),
            Stock("CCC", "CCC Co", 100.0),
            Stock("AB", "Absolute", 100.0),
            Stock("ABC", "ABC Industries", 100.0),
            Stock("XYZ", "XYZ and ABC", 100.0),
        )

        coEvery { stocksApi.getStocks() } answers { fetchedStocks }

        val subject = StocksRepositoryImpl(stocksApi, stockDao)
        val result = subject.fetchStocks()

        assertTrue(result is ApiResult.Success)
        assertEquals(fetchedStocks, result.data)
    }

    @Test
    fun `fetch stocks caches the stocks in Room database`(): Unit = runBlocking {
        val fetchedStocks = listOf(
            Stock("FFF", "FFF Co", 100.0),
            Stock("DDD", "DDD Co", 100.0),
            Stock("CCC", "CCC Co", 100.0),
            Stock("AB", "Absolute", 100.0),
            Stock("ABC", "ABC Industries", 100.0),
            Stock("XYZ", "XYZ and ABC", 100.0),
        )

        coEvery { stocksApi.getStocks() } answers { fetchedStocks }

        val subject = StocksRepositoryImpl(stocksApi, stockDao)
        subject.fetchStocks()

        coVerify(exactly = 1) {
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
        }
    }
}
