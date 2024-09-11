package com.tinaciousdesign.interviews.stocks.navigation

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.tinaciousdesign.interviews.stocks.utils.lastSegment

@Composable
fun BottomNavigationBar(navController: NavController) {
    val context = LocalContext.current

    val navItems = listOf<Route>(
        Route.StockSearch,
        Route.About
    )
    var selectedItem by remember { mutableStateOf<Route>(Route.StockSearch) }

    // Update the active item's highlighted state and navigate to the desired screen
    fun handleRouteClicked(route: Route) {
        selectedItem = route

        navController.navigate(route) {
            navController.graph.startDestinationRoute?.let { startRoute ->
                popUpTo(startRoute) {
                    saveState = true
                }
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    // Update the active item's highlighted state
    LaunchedEffect(0) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            navItems.forEach { navItem ->
                val current = destination.route?.lastSegment(".")
                if (current == navItem.routeName) {
                    selectedItem = navItem
                }
            }
        }
    }

    // Render the tabs with icons and localized titles
    NavigationBar {
        navItems.forEach { route ->
            NavigationBarItem(
                selected = route == selectedItem,
                label = { Text(context.getString(route.titleRes)) },
                icon = route.icon,
                onClick = { handleRouteClicked(route) },
            )
        }
    }
}
