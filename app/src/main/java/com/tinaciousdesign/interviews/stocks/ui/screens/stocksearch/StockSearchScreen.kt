package com.tinaciousdesign.interviews.stocks.ui.screens.stocksearch

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tinaciousdesign.interviews.stocks.R
import com.tinaciousdesign.interviews.stocks.ui.components.Divider
import com.tinaciousdesign.interviews.stocks.ui.components.EmptyState
import com.tinaciousdesign.interviews.stocks.ui.components.SearchInputView
import com.tinaciousdesign.interviews.stocks.ui.components.StockSearchResults

@Composable
fun StockSearchScreen(
    viewModel: StockSearchViewModel
) {
    val context = LocalContext.current

    val stocks by viewModel.stocks.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val isSearching by viewModel.isSearching.collectAsStateWithLifecycle(false)

    LifecycleEventEffect(Lifecycle.Event.ON_START) {
        viewModel.loadStocks()
    }

    Column {
        SearchInputView(searchQuery) { newValue ->
            viewModel.onSearch(newValue)
        }

        Divider(MaterialTheme.colorScheme.secondary)

        Box(modifier = Modifier.weight(1.0f)) {
            if (isSearching) {
                StockSearchResults(stocks)
            } else {
                EmptyState(
                    title = context.getString(R.string.stock_search_empty_heading),
                    message = context.getString(R.string.stock_search_empty_message),
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
        }
    }
}
