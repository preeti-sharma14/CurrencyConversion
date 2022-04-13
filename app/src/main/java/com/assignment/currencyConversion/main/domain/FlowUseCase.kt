package com.assignment.currencyConversion.main.domain

import com.assignment.currencyConversion.main.domain.helper.Resource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

abstract class FlowUseCase<in P, Res>(private val coroutineDispatcher: CoroutineDispatcher) {
    fun execute(parameters: P? = null): Flow<Resource<Res>> {
        return buildUsecase(parameters)
            .buffer()
            .catch {
                emit(
                    Resource.error("Error")
                )
            }.flowOn(coroutineDispatcher)
    }

    abstract fun buildUsecase(parameters: P?): Flow<Resource<Res>>
}