package com.assignment.currencyConversion.main.data.network

import com.assignment.currencyConversion.main.domain.helper.EndPoints
import com.assignment.currencyConversion.main.domain.model.ApiResponse
import com.assignment.currencyConversion.main.domain.model.CurrencyListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(EndPoints.CONVERT_URL)
    suspend fun convertCurrency(
        @Query("access_key") access_key: String
    ): Response<ApiResponse>

    @GET(EndPoints.SYMBOL)
    suspend fun listCurrency(
        @Query("access_key") access_key: String
    ): Response<CurrencyListResponse>

}
