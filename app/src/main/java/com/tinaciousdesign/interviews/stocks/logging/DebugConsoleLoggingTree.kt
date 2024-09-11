package com.tinaciousdesign.interviews.stocks.logging

import timber.log.Timber

/**
 * Should only be used in debug builds since references to [StackTraceElement] will be lost in minified builds
 */
class DebugConsoleLoggingTree : Timber.DebugTree() {
    override fun createStackElementTag(element: StackTraceElement): String? {
        return String.format(
            "%s:%s#%s",
            element.fileName.replace(".kt", ""),
            element.lineNumber,
            element.methodName,
        )
    }
}
