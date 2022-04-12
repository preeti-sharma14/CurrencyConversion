package com.assignment.core.domain.usecase

import com.assignment.core.domain.executor.PostExecutionThread
import com.assignment.core.domain.remote.BaseResponse
import io.reactivex.Single
import javax.inject.Inject

class GetConversionListUseCase@Inject constructor(
    private val postExecutionThread: PostExecutionThread
): SingleUseCase<BaseResponse, Void?>(postExecutionThread){
    override fun buildUseCase(params: Void?): Single<BaseResponse> {
        TODO("Not yet implemented")
    }

}


