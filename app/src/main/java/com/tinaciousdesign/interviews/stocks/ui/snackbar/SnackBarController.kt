package com.tinaciousdesign.interviews.stocks.ui.snackbar

import android.content.res.Resources
import androidx.compose.material3.SnackbarDuration
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

data class SnackBarEvent(
    val getLocalizedMessage: (Resources) -> String,
    val duration: SnackbarDuration = SnackbarDuration.Long,
    val action: SnackBarAction? = null
)

data class SnackBarAction(
    val getLocalizedName: (Resources) -> String,
    val action: suspend () -> Unit
)

object SnackBarController {
    private val _events = Channel<SnackBarEvent>()
    val events = _events.receiveAsFlow()

    suspend fun sendEvent(event: SnackBarEvent) {
        _events.send(event)
    }
}
