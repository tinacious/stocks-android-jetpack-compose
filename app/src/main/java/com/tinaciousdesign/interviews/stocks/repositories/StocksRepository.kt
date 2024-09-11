package com.tinaciousdesign.interviews.stocks.repositories

import com.tinaciousdesign.interviews.stocks.db.stock.StockDao
import com.tinaciousdesign.interviews.stocks.db.stock.StockEntity
import com.tinaciousdesign.interviews.stocks.logging.Logger
import com.tinaciousdesign.interviews.stocks.models.Stock
import com.tinaciousdesign.interviews.stocks.networking.ApiError
import com.tinaciousdesign.interviews.stocks.networking.ApiResult
import com.tinaciousdesign.interviews.stocks.networking.api.StocksApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface StocksRepository {
    class GetStocksError(): ApiError()

    fun findStocksFlow(query: String): Flow<List<Stock>>

    suspend fun fetchStocks(forceRefresh: Boolean = false): ApiResult<List<Stock>, GetStocksError>
}

class StocksRepositoryImpl @Inject constructor(
    private val stocksApi: StocksApi,
    private val stockDao: StockDao,
) : StocksRepository {
    private var cachedStocks = listOf<Stock>()

    override fun findStocksFlow(query: String): Flow<List<Stock>> {
        return stockDao.findStocksFlow(query).flowOn(Dispatchers.IO).map { stocks ->
            stocks.map { stockEntity ->
                Stock(
                    ticker = stockEntity.ticker,
                    name = stockEntity.name,
                    price = stockEntity.price
                )
            }
        }
    }

    override suspend fun fetchStocks(forceRefresh: Boolean): ApiResult<List<Stock>, StocksRepository.GetStocksError> {
        if (cachedStocks.isNotEmpty() && !forceRefresh) {
            return ApiResult.Success(cachedStocks)
        }

        return try {
            val response = stocksApi.getStocks()

            cachedStocks = response

            val stockEntities = response.map(StockEntity::fromStock)
            stockDao.deleteAll()
            stockDao.insertAll(stockEntities)

            ApiResult.Success(response)
        } catch (e: Exception) {
            Logger.e(e)
            ApiResult.Failed(StocksRepository.GetStocksError())
        }
    }
}
