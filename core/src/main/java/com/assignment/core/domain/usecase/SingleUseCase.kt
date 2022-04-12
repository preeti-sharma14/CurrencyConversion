package com.assignment.core.domain.usecase

import com.assignment.core.domain.executor.PostExecutionThread
import com.assignment.core.domain.executor.ThreadExecutor
import com.assignment.core.domain.remote.BaseError
import com.assignment.core.domain.remote.BaseResponse
import com.assignment.core.domain.remote.Status
import com.assignment.core.domain.rxcallback.CallbackWrapper
import com.google.gson.Gson
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import java.util.concurrent.TimeoutException

abstract class SingleUseCase<T : BaseResponse, Params> {
    private val threadScheduler: Scheduler
    private val postExecutionThread: PostExecutionThread
    private var useCaseListener: UseCaseListener? = null

    constructor(
        postExecutionThread: PostExecutionThread
    ) {
        this.postExecutionThread = postExecutionThread
        threadScheduler=Schedulers.io()
    }

    constructor(
        threadExecutor: ThreadExecutor,
        postExecutionThread: PostExecutionThread
    ) {
        threadScheduler = Schedulers.from(threadExecutor)
        this.postExecutionThread = postExecutionThread
    }

    abstract fun buildUseCase(params: Params? = null): Single<T>

    fun setListener(useCaseListener: UseCaseListener): SingleUseCase<T, Params> {
        this.useCaseListener = useCaseListener
        return this
    }

    open fun execute(callbackWrapper: CallbackWrapper<T>?, params: Params? = null): Disposable? {
        if (callbackWrapper == null) {
            return null
        }
        val single = buildUseCase(params)
            .subscribeOn(threadScheduler)
            .observeOn(postExecutionThread.scheduler())
        useCaseListener?.onPreExecute()
        return single.subscribe({ result ->
            useCaseListener?.onPostExecute()
            callbackWrapper.onResponseSuccess(result)
        }, { exception ->
            useCaseListener?.onPostExecute()
            when (exception) {
                is IOException,
                is TimeoutException -> callbackWrapper.onNetworkError(exception)
                else -> callbackWrapper.onServerError(exception)
            }
        })
    }

    private fun handleResponseError(errorText: String, callbackWrapper: CallbackWrapper<T>) {
        try {
            val baseError = Gson().fromJson(
                errorText, BaseError::class.java
            )
            if (baseError != null) {
                baseError?.let {
                    if (it.errors.isNullOrEmpty()) {
                        it.error?.let { error ->
                            callbackWrapper.onResponseError(
                                Status(false, error)
                            )
                        }
                    }
                }
            } else {
                callbackWrapper.onResponseError(
                    Status(
                        false,
                        error("Something went wrong,Please try again")
                    )
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            callbackWrapper.onResponseError(Status(false, error("Response error")))
        }
    }
}