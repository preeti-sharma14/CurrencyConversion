package com.assignment.core.domain.rxcallback

import com.assignment.core.domain.remote.Status

interface ResponseCallback<T> {

    fun onResponseSuccess(response: T)
    fun onResponseError(errorResponse: Status)
    fun onNetworkError(throwable: Throwable)
    fun onServerError(throwable: Throwable)
}