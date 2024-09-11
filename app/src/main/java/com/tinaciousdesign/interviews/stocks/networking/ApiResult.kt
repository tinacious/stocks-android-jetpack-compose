package com.tinaciousdesign.interviews.stocks.networking

sealed class ApiResult<ResultData, Error : ApiError> {
    data class Success<ResultData, Error : ApiError>(
        override val data: ResultData
    ) : ApiResult<ResultData, Error>() {
        override val ok: Boolean = true
        override val error: Error? = null
    }

    data class Failed<Result, Error : ApiError>(
        override val error: Error
    ) : ApiResult<Result, Error>() {
        override val ok: Boolean = false
        override val data: Result? = null
    }

    abstract val data: ResultData?
    abstract val error: Error?

    abstract val ok: Boolean

    val failed: Boolean get() = !ok
}
