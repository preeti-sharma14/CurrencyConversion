package com.assignment.currencyConversion.main.network

import com.assignment.currencyConversion.main.helper.EndPoints
import com.assignment.currencyConversion.main.model.ApiResponse
import com.assignment.currencyConversion.main.model.CurrencyListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
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
