package com.tinaciousdesign.interviews.stocks

import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.tinaciousdesign.interviews.stocks.di.AppModule
import com.tinaciousdesign.interviews.stocks.navigation.BottomNavigationBar
import com.tinaciousdesign.interviews.stocks.navigation.NavigationRouter
import com.tinaciousdesign.interviews.stocks.ui.TestTags
import com.tinaciousdesign.interviews.stocks.ui.theme.StocksTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@HiltAndroidTest
@UninstallModules(AppModule::class)
class StockSearchBehaviourTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()

        composeRule.activity.setContent {
            StocksTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        bottomBar = { BottomNavigationBar(navController = navController) }
                    ) { innerPadding ->
                        Box(
                            modifier = Modifier.padding(
                                PaddingValues(
                                    0.dp,
                                    0.dp,
                                    0.dp,
                                    innerPadding.calculateBottomPadding()
                                )
                            )
                        ) {
                            NavigationRouter(navController)
                        }
                    }
                }
            }
        }
    }

    @Test
    fun userCanSearchForStocks() {
        with(composeRule) {
            composeRule.onNodeWithText("Use the search field above to find stocks by ticker or by name").assertIsDisplayed()

            composeRule
                .onNodeWithTag(TestTags.searchField)
                .performTextInput("ow")

            waitForText("POWL")

            textIsDisplayed("POWL")
            textIsDisplayed("Omni Resources")

            textIsDisplayed("FVOW")
            textIsDisplayed("Harmony Enterprises")
        }
    }
}
