package com.tinaciousdesign.interviews.stocks.networking

 open class ApiError(
    cause: Throwable? = null,
    message: String? = null,
) : Exception(message, cause)
