package com.assignment.currencyConversion.main.data.network

import javax.inject.Inject

class ApiDataSource @Inject constructor(private val apiService: ApiService) {
    suspend fun getConvertedRate(accessKey: String) = apiService.convertCurrency(accessKey)
    suspend fun getConversionList(accessKey: String)=apiService.listCurrency(accessKey)
}