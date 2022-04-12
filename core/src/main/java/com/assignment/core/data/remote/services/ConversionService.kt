package com.assignment.core.data.remote.services

import com.assignment.core.domain.remote.BaseResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface ConversionService {

    @GET(ConversionEndpoints.GET_CURRENCY_LIST)
    fun getConversionList(): Single<BaseResponse>
}