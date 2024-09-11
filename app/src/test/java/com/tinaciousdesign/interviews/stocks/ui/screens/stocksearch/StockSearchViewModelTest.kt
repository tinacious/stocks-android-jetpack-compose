package com.tinaciousdesign.interviews.stocks.ui.screens.stocksearch

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.tinaciousdesign.interviews.stocks.models.Stock
import com.tinaciousdesign.interviews.stocks.networking.ApiResult
import com.tinaciousdesign.interviews.stocks.repositories.StocksRepository
import com.tinaciousdesign.interviews.stocks.testutils.TestCoroutineRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class StockSearchViewModelTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @MockK
    lateinit var  stocksRepository: StocksRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @Test
    fun `loads the stocks`(): Unit = runTest {
        val fetchedStocks = listOf(
            Stock("FFF", "FFF Co", 100.0),
            Stock("DDD", "DDD Co", 100.0),
            Stock("CCC", "CCC Co", 100.0),
            Stock("AB", "Absolute", 100.0),
            Stock("ABC", "ABC Industries", 100.0),
            Stock("XYZ", "XYZ and ABC", 100.0),
        )
        coEvery { stocksRepository.fetchStocks() } answers { ApiResult.Success(fetchedStocks) }

        val savedStateHandle = SavedStateHandle()
        savedStateHandle["searchQuery"] = ""
        val subject = StockSearchViewModel(savedStateHandle, stocksRepository)

        subject.loadStocks()

        coVerify(exactly = 1) {
            stocksRepository.fetchStocks()
        }
    }

    @Test
    fun `exposes a state flow of the stocks`(): Unit = runTest {
        val allStocks = listOf(
            Stock("FFF", "FFF Co", 100.0),
            Stock("DDD", "DDD Co", 100.0),
            Stock("CCC", "CCC Co", 100.0),
            Stock("AB", "Absolute", 100.0),
            Stock("ABC", "ABC Industries", 100.0),
            Stock("XYZ", "XYZ and ABC", 100.0),
        )
        val fakeStocksRepository = object : StocksRepository {
            override fun findStocksFlow(query: String): Flow<List<Stock>> = flowOf(allStocks)

            override suspend fun fetchStocks(forceRefresh: Boolean): ApiResult<List<Stock>, StocksRepository.GetStocksError> =
                ApiResult.Success(emptyList())
        }
        val savedStateHandle = SavedStateHandle()
        savedStateHandle["searchQuery"] = ""
        val subject = StockSearchViewModel(savedStateHandle, fakeStocksRepository)

        backgroundScope.launch {
            subject.stocks.collect()
        }

        subject.stocks.test {
            assertEquals(allStocks, awaitItem())
        }
    }

    @Test
    fun `supports searching for stocks by prioritizing exact matches`(): Unit = runTest {
        val allStocks = listOf(
            Stock("ABC", "ABC Industries", 100.0),
            Stock("AB", "Absolute", 100.0), // This is the one that matches exactly "ab"
            Stock("XYZ", "XYZ and ABC", 100.0),
        )
        val fakeStocksRepository = object : StocksRepository {
            override fun findStocksFlow(query: String): Flow<List<Stock>> = flowOf(allStocks)

            override suspend fun fetchStocks(forceRefresh: Boolean): ApiResult<List<Stock>, StocksRepository.GetStocksError> =
                ApiResult.Success(emptyList())
        }
        val savedStateHandle = SavedStateHandle()
        savedStateHandle["searchQuery"] = "ab"
        val subject = StockSearchViewModel(savedStateHandle, fakeStocksRepository)

        backgroundScope.launch {
            subject.stocks.collect()
        }

        subject.stocks.test {
            val searchedStocks = awaitItem()
            assertEquals(Stock("AB", "Absolute", 100.0), searchedStocks[0])
        }
    }
}
