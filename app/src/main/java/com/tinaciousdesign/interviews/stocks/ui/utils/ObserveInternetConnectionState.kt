package com.tinaciousdesign.interviews.stocks.ui.utils

import androidx.compose.runtime.Composable
import com.tinaciousdesign.interviews.stocks.R
import com.tinaciousdesign.interviews.stocks.events.AppEvent
import com.tinaciousdesign.interviews.stocks.events.EventBus
import com.tinaciousdesign.interviews.stocks.events.ObserveAsEvents
import com.tinaciousdesign.interviews.stocks.ui.snackbar.SnackBarController
import com.tinaciousdesign.interviews.stocks.ui.snackbar.SnackBarEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ObserveInternetConnectionState(
    eventBus: EventBus,
    coroutineScope: CoroutineScope,
) {
    ObserveAsEvents(eventBus.events) { appEvent ->
        coroutineScope.launch {
            when (appEvent) {
                AppEvent.ConnectionLost -> {
                    SnackBarController.sendEvent(
                        SnackBarEvent({ resources ->
                            resources.getString(R.string.connection_lost_message)
                        })
                    )
                }
                AppEvent.ConnectionRestored -> {
                    SnackBarController.sendEvent(
                        SnackBarEvent({ resources ->
                            resources.getString(R.string.connection_restored_message)
                        })
                    )
                }
                else -> {}
            }
        }
    }
}
