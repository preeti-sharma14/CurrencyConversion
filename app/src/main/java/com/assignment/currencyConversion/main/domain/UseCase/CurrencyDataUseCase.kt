package com.assignment.currencyConversion.main.domain.UseCase

import com.assignment.currencyConversion.main.domain.FlowUseCase
import com.assignment.currencyConversion.main.domain.helper.Resource
import com.assignment.currencyConversion.main.domain.model.ApiResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CurrencyDataUseCase @Inject constructor(
    private val conversionCommonRepo: ConversionCommonRepo,
    ioDispatcher: CoroutineDispatcher
): FlowUseCase<Void, ApiResponse>(ioDispatcher) {
    override fun buildUsecase(parameters: Void?): Flow<Resource<ApiResponse>> {
        return conversionCommonRepo.getConversionData()
    }
}