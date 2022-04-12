package com.assignment.core.domain.remote

import io.reactivex.rxjava3.core.Single

interface ConversionListRemote {
    fun getConversionList(): Single<BaseResponse>
}