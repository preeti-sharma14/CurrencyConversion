package com.assignment.core.data.remote.services

import com.assignment.core.domain.remote.BaseResponse
import com.assignment.core.domain.remote.ConversionListRemote
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class ConversionImpl @Inject constructor(
    private val conversionService: ConversionService
) : ConversionListRemote {
    override fun getConversionList(): Single<BaseResponse> =
    conversionService.getConversionList()
}