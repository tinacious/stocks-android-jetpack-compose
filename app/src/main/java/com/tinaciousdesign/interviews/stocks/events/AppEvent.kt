package com.tinaciousdesign.interviews.stocks.events

sealed class AppEvent {
    data object ConnectionLost : AppEvent()

    data object ConnectionRestored : AppEvent()
}
