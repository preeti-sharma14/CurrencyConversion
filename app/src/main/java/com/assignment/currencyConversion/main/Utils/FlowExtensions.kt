package com.assignment.currencyConversion.main.Utils

import com.assignment.currencyConversion.main.domain.helper.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach

val String.Companion.empty: String get() = String()

//return empty string if 'null',else vaue
fun String?.safeGet(): String = this ?: String.empty

fun <T> resultFlow(block: suspend () -> T): Flow<Resource<T>> = flow {
    emit(Resource.Loading)
    try {
        emit(Resource.Success(block()))
    } catch (exception: Exception) {
        val errorStatus = ErrorUtil.getErrorStatus(exception)
        emit(
            Resource.Error(
                throwable = Throwable(exception),
                errorCode = errorStatus.code.toString(),
                errorDesc = errorStatus.message.safeGet()

            )
        )
    }
}

fun <T> Flow<Resource<T>>.onSuccess(action: suspend (data: T) -> Unit): Flow<Resource<T>> =
    onEach { result ->
        if (result is Resource.Success) action(result.data)
    }

fun <T> Flow<Resource<T>>.onError(action: suspend (String) -> Unit): Flow<Resource<T>> =
    onEach { result ->
        if (result is Resource.Error) action(result.errorDesc)
    }

fun <T> Flow<Resource<T>>.onLoading(action: suspend () -> Unit): Flow<Resource<T>> =
    onEach { result ->
        if (result is Resource.Loading) action()
    }

