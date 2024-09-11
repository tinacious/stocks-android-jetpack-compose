package com.tinaciousdesign.interviews.stocks.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.tinaciousdesign.interviews.stocks.R
import com.tinaciousdesign.interviews.stocks.models.Stock

@Composable
fun StockSearchResults(
    stocks: List<Stock>,
) {
    val context = LocalContext.current

    LazyColumn {
        itemsIndexed(stocks) { idx, stock ->
            StockSearchResultListItem(stock)

            if (idx < stocks.lastIndex) {
                Divider(MaterialTheme.colorScheme.surface)
            }
        }
    }
    if (stocks.isEmpty()) {
        EmptyState(
            title = context.getString(R.string.stock_search_no_results_heading),
            message = context.getString(R.string.stock_search_no_results_message),
            modifier = Modifier
                .fillMaxSize()
        )
    }
}
