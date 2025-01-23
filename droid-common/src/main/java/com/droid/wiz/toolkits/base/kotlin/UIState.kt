package com.droid.wiz.toolkits.base.kotlin

sealed class UIState<out T> {

    data class Success<T>(val data: T) : UIState<T>()
    data class Failure(val code: Int, val message: String?) : UIState<Nothing>()
    data object Loading : UIState<Nothing>()
    data object Empty : UIState<Nothing>()
}