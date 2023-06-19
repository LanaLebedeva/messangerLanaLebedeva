package com.github.lanalebedeva.mydiscord.api.state

import kotlinx.coroutines.CancellationException

sealed class ResultChat<out T : Any> {
    class Success<out T : Any>(val data: T) : ResultChat<T>()
    class Error(exception: Exception) : ResultChat<Nothing>() {
        init {
            if (exception is CancellationException)
                throw exception
        }
    }
    object NothingInBd: ResultChat<Nothing>()
    object Progress : ResultChat<Nothing>()
}
