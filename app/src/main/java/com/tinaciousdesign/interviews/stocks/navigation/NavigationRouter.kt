package com.tinaciousdesign.interviews.stocks.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.tinaciousdesign.interviews.stocks.ui.screens.about.AboutScreen
import com.tinaciousdesign.interviews.stocks.ui.screens.stocksearch.StockSearchScreen
import com.tinaciousdesign.interviews.stocks.ui.screens.stocksearch.StockSearchViewModel

@Composable
fun NavigationRouter(
    navHostController: NavHostController
) {
    NavHost(navController = navHostController, startDestination = Route.StockSearch) {
        composable<Route.StockSearch> { backStackEntry ->
            val viewModel = hiltViewModel<StockSearchViewModel>()

            StockSearchScreen(viewModel)
        }

        composable<Route.About> { backStackEntry ->
            AboutScreen()
        }
    }
}
