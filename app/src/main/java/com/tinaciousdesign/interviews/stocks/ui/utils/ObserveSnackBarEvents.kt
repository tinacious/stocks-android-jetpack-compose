package com.tinaciousdesign.interviews.stocks.ui.utils

import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.tinaciousdesign.interviews.stocks.events.ObserveAsEvents
import com.tinaciousdesign.interviews.stocks.ui.snackbar.SnackBarEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Composable
fun ObserveSnackBarEvents(
    snackBarFlow: Flow<SnackBarEvent>,
    snackBarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
) {
    val resources = LocalContext.current.resources

    ObserveAsEvents(snackBarFlow, snackBarHostState) { event ->
        coroutineScope.launch {
            snackBarHostState.currentSnackbarData?.dismiss()

            val result = snackBarHostState.showSnackbar(
                message = event.getLocalizedMessage(resources),
                actionLabel = event.action?.getLocalizedName?.invoke(resources),
                duration = event.duration,
            )

            if (result == SnackbarResult.ActionPerformed) {
                event.action?.action?.invoke()
            }
        }
    }
}
