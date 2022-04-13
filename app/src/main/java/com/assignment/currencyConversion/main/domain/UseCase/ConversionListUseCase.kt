package com.assignment.currencyConversion.main.domain.UseCase

import com.assignment.currencyConversion.main.domain.FlowUseCase
import com.assignment.currencyConversion.main.domain.helper.Resource
import com.assignment.currencyConversion.main.domain.model.CurrencyListResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ConversionListUseCase @Inject constructor(
    private val conversionCommonRepo: ConversionCommonRepo,
     ioDispatcher: CoroutineDispatcher
): FlowUseCase<Void, CurrencyListResponse>(ioDispatcher) {
    override fun buildUsecase(parameters: Void?): Flow<Resource<CurrencyListResponse>> {
        return conversionCommonRepo.getCurrencyList()
    }
}