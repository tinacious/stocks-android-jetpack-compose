package com.tinaciousdesign.interviews.stocks

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.tinaciousdesign.interviews.stocks.events.AppEvent
import com.tinaciousdesign.interviews.stocks.events.EventBus
import com.tinaciousdesign.interviews.stocks.navigation.BottomNavigationBar
import com.tinaciousdesign.interviews.stocks.navigation.NavigationRouter
import com.tinaciousdesign.interviews.stocks.ui.snackbar.SnackBarController
import com.tinaciousdesign.interviews.stocks.ui.theme.StocksTheme
import com.tinaciousdesign.interviews.stocks.ui.utils.KeyboardState
import com.tinaciousdesign.interviews.stocks.ui.utils.ObserveInternetConnectionState
import com.tinaciousdesign.interviews.stocks.ui.utils.ObserveSnackBarEvents
import com.tinaciousdesign.interviews.stocks.ui.utils.keyboardVisibleState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var eventBus: EventBus

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        registerNetworkListener()

        setContent {
            StocksTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val scope = rememberCoroutineScope()
                    val navController = rememberNavController()
                    val snackBarHostState = remember { SnackbarHostState() }
                    val keyboardState by keyboardVisibleState()

                    ObserveInternetConnectionState(eventBus, scope)
                    ObserveSnackBarEvents(SnackBarController.events, snackBarHostState, scope)

                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        snackbarHost = {
                            SnackbarHost(hostState = snackBarHostState)
                        },
                        bottomBar = {
                            if (keyboardState == KeyboardState.Closed) {
                                BottomAppBar {
                                    BottomNavigationBar(navController = navController)
                                }
                            }
                        }
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


    // region Network Listener

    private fun registerNetworkListener() {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerDefaultNetworkCallback(networkListener)
    }

    private var hasDisconnected = false

    private val networkListener = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)

            if (hasDisconnected) {
                handleNetworkConnectionRestored()
            }
        }

        override fun onLost(network: Network) {
            super.onLost(network)

            hasDisconnected = true
            handleNetworkConnectionLost()
        }
    }

    private fun handleNetworkConnectionLost() {
        lifecycleScope.launch {
            eventBus.emitEvent(AppEvent.ConnectionLost)
        }
    }

    private fun handleNetworkConnectionRestored() {
        lifecycleScope.launch {
            eventBus.emitEvent(AppEvent.ConnectionRestored)
        }
    }

    // endregion Network Listener
}
