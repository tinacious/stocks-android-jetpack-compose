package com.tinaciousdesign.interviews.stocks.events

import com.tinaciousdesign.interviews.stocks.logging.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

class EventBus {
    private val _events = MutableSharedFlow<AppEvent>(replay = 10)
    val events = _events.asSharedFlow()

    suspend fun emitEvent(event: AppEvent) {
        Logger.d("🚌🏁 Emitting event = $event")
        _events.emit(event)
    }

    suspend inline fun <reified T : AppEvent> subscribe(crossinline onEvent: (T) -> Unit) {
        events.filterIsInstance<T>()
            .collectLatest { appEvent ->
                if (!coroutineContext.isActive) {
                    Logger.d("🚌🛑 Coroutine inactive - Not collecting event: $appEvent")
                    return@collectLatest
                }

                Logger.d("🚌🛍️ Collecting event: $appEvent")
                onEvent(appEvent)
            }
    }

    inline fun <reified T : AppEvent> subscribe(coroutineScope: CoroutineScope, crossinline onEvent: (T) -> Unit) {
        coroutineScope.launch {
            subscribe<T>(onEvent)
        }
    }
}
