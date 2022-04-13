package com.assignment.currencyConversion.main.domain.UseCase

import com.assignment.currencyConversion.main.domain.helper.Resource
import com.assignment.currencyConversion.main.domain.model.ApiResponse
import com.assignment.currencyConversion.main.domain.model.CurrencyListResponse
import kotlinx.coroutines.flow.Flow

interface ConversionCommonRepo {
    fun getCurrencyList(): Flow<Resource<CurrencyListResponse>>
    fun getConversionData():Flow<Resource<ApiResponse>>
}