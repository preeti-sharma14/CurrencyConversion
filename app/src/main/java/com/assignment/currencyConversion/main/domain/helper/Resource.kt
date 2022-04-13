package com.assignment.currencyConversion.main.domain.helper

import androidx.lifecycle.MutableLiveData
import com.assignment.currencyConversion.main.Utils.empty


sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(
        val throwable: Throwable,
        val errorCode: String,
        val errorDesc: String
    ) : Resource<Nothing>()

    object Loading : Resource<Nothing>()
    object Empty : Resource<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[throwable=$throwable]"
            is Empty -> String.empty
            Loading -> "Loading"
        }
    }


    fun <T> Resource<T>.successOr(fallback: T): T {
        return (this as? Success<T>)?.data ?: fallback
    }

    val <T> Resource<T>.data: T?
        get() = (this as? Success)?.data

    inline fun <reified T> Resource<T>.updateOnSuccess(liveData: MutableLiveData<T>) {
        if (this is Success) {
            liveData.value = data
        }

    }
}
