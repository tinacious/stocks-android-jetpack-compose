package com.tinaciousdesign.interviews.stocks.navigation

import androidx.annotation.Keep
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import com.tinaciousdesign.interviews.stocks.R
import com.tinaciousdesign.interviews.stocks.ui.icons.TinaciousDesignLogoIcon
import com.tinaciousdesign.interviews.stocks.ui.icons.TintedIconDrawable
import kotlinx.serialization.Serializable

@Serializable @Keep
sealed class Route {
    abstract val icon: @Composable () -> Unit

    @get:StringRes
    abstract val titleRes: Int

    val routeName: String? get() = javaClass.simpleName

    @Serializable @Keep
    data object StockSearch : Route() {
        override val titleRes: Int get() = R.string.route_stock_search

        override val icon: @Composable () -> Unit = {
            TintedIconDrawable(
                R.drawable.ic_dollar,
                R.string.route_stock_search
            )
        }
    }

    @Serializable @Keep
    data object About : Route() {
        override val titleRes: Int get() = R.string.route_about

        override val icon: @Composable () -> Unit = {
            TinaciousDesignLogoIcon()
        }
    }
}
