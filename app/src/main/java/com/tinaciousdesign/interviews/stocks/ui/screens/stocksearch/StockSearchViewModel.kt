package com.tinaciousdesign.interviews.stocks.ui.screens.stocksearch

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tinaciousdesign.interviews.stocks.R
import com.tinaciousdesign.interviews.stocks.models.Stock
import com.tinaciousdesign.interviews.stocks.repositories.StocksRepository
import com.tinaciousdesign.interviews.stocks.ui.snackbar.SnackBarController
import com.tinaciousdesign.interviews.stocks.ui.snackbar.SnackBarEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

interface StockSearchVM {
    val stocks: StateFlow<List<Stock>>
    val searchQuery: StateFlow<String>
    val isSearching: Flow<Boolean>

    fun loadStocks()
    fun onSearch(query: String)
}

@HiltViewModel
class StockSearchViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val stocksRepository: StocksRepository,
) : StockSearchVM, ViewModel() {

    override val searchQuery: StateFlow<String> = savedStateHandle.getStateFlow("searchQuery", "")

    override val stocks: StateFlow<List<Stock>> =
        searchQuery.flatMapLatest { query ->
            stocksRepository.findStocksFlow(query)
        }
        .combine(searchQuery, ::Pair)
        .map { (list, query) ->
            list.sortedWith(Stock.compareQuery(query))
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList(),
        )

    override val isSearching = searchQuery.map { it.isNotBlank() }

    override fun loadStocks() {
        viewModelScope.launch {
            val result = stocksRepository.fetchStocks()
            if (result.failed) {
                SnackBarController.sendEvent(
                    SnackBarEvent({ resources ->
                        resources.getString(R.string.stock_search_error_stocks_fetch_failed)
                    })
                )
            }
        }
    }

    override fun onSearch(query: String) {
        savedStateHandle["searchQuery"] = query
    }
}
