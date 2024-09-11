package com.tinaciousdesign.interviews.stocks.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Stock(
    @SerialName("ticker") val ticker: String,
    @SerialName("name") val name: String,
    @SerialName("currentPrice") val price: Double,
) {
    val formattedPrice: String get() = "%.2f".format(price)

    companion object {
        fun compareQuery(query: String): Comparator<Stock> =
            Comparator { a, b ->
                val queryLowered = query.lowercase()

                val aExactMatch = a.name.lowercase() == queryLowered ||
                    a.ticker.lowercase() == queryLowered
                val bExactMatch = b.name.lowercase() == queryLowered ||
                    b.ticker.lowercase() == queryLowered

                if (aExactMatch && bExactMatch) {
                    return@Comparator 0
                }

                if (aExactMatch) -1 else 1
            }
    }
}

fun List<Stock>.matches(query: String): List<Stock> =
    this.filter { stock ->
        stock.ticker.contains(query.trim(), ignoreCase = true) ||
            stock.name.contains(query.trim(), ignoreCase = true)
    }
