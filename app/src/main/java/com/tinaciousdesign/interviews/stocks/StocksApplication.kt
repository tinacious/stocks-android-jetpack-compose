package com.tinaciousdesign.interviews.stocks

import android.app.Application
import com.tinaciousdesign.interviews.stocks.BuildConfig
import com.tinaciousdesign.interviews.stocks.logging.CrashReportingTree
import com.tinaciousdesign.interviews.stocks.logging.DebugConsoleLoggingTree
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class StocksApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        setUpLogging()
    }

    private fun setUpLogging() {
        val loggingTree = if (BuildConfig.DEBUG) DebugConsoleLoggingTree() else CrashReportingTree()
        Timber.plant(loggingTree)
    }
}
