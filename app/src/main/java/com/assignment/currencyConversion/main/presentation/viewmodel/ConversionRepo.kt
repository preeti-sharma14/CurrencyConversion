package com.assignment.currencyConversion.main.presentation.viewmodel

import com.assignment.currencyConversion.main.helper.Resource
import com.assignment.currencyConversion.main.model.ApiResponse
import com.assignment.currencyConversion.main.network.ApiDataSource
import com.assignment.currencyConversion.main.network.BaseDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ConversionRepo @Inject constructor(private val apiDataSource: ApiDataSource) :
    BaseDataSource() {
    suspend fun getConvertedData(access_key: String): Flow<Resource<ApiResponse>> {
        return flow {
            emit(safeApiCall { apiDataSource.getConvertedRate(access_key) })
        }.flowOn(Dispatchers.IO)
    }
}