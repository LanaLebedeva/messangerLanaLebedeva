package com.github.lanalebedeva.mydiscord.viewStateChat

sealed class ViewStateChat<out T : Any> {

    class Error(val exception: Throwable) : ViewStateChat<Nothing>()
    object Loading : ViewStateChat<Nothing>()

    class Data<out T : Any>(val data: T) : ViewStateChat<T>()

    object Init : ViewStateChat<Nothing>()
}
