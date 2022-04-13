package com.assignment.currencyConversion.main.network

import com.assignment.currencyConversion.main.helper.EndPoints
import javax.inject.Inject

class ApiDataSource @Inject constructor(private val apiService: ApiService) {
    suspend fun getConvertedRate(accessKey: String) = apiService.convertCurrency(accessKey)
    suspend fun getConversionList(accessKey: String)=apiService.listCurrency(accessKey)
}