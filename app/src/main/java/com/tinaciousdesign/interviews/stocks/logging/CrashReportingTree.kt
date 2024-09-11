package com.tinaciousdesign.interviews.stocks.logging

import android.annotation.SuppressLint
import android.util.Log
import timber.log.Timber

@SuppressLint("LogNotTimber")
class CrashReportingTree : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority < Log.INFO) return

        logToConsole(priority, tag, message, t)
        logToMonitoringService(priority, tag, message, t)
    }

    private fun logToConsole(priority: Int, tag: String?, message: String, t: Throwable?) {
        when (priority) {
            Log.ASSERT, Log.ERROR -> Log.e(tag, message, t)
            Log.WARN -> Log.w(tag, message, t)
        }
    }

    private fun logToMonitoringService(priority: Int, tag: String?, message: String, t: Throwable?) {
        // todo: Implement third-party logging service, e.g. Crashlytics
    }
}
