package com.assignment.currencyConversion.main.presentation.viewmodel

import com.assignment.currencyConversion.main.domain.helper.Resource
import com.assignment.currencyConversion.main.domain.model.ApiResponse
import com.assignment.currencyConversion.main.domain.model.CurrencyListResponse
import com.assignment.currencyConversion.main.data.network.ApiDataSource
import com.assignment.currencyConversion.main.data.network.BaseDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
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

    suspend fun getConversionList(access_key: String): Flow<Resource<CurrencyListResponse>> {
        return flow {
            emit(safeApiCall { apiDataSource.getConversionList(access_key) })
        }.flowOn(Dispatchers.IO)
    }

}