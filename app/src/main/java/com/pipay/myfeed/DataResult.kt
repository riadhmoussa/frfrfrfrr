package com.pipay.myfeed

sealed class DataResult<T> {
    data class Success<T>(val value: T): DataResult<T>()
    class Failure<T>(val message: String, val throwable: Throwable?): DataResult<T>()
}
