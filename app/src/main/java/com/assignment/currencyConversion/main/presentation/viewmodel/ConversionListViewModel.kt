package com.assignment.currencyConversion.main.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.currencyConversion.main.helper.EndPoints
import com.assignment.currencyConversion.main.helper.Resource
import com.assignment.currencyConversion.main.helper.SingleLiveEvent
import com.assignment.currencyConversion.main.model.ApiResponse
import com.assignment.currencyConversion.main.model.CurrencyListResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConversionListViewModel @Inject constructor(private val conversionRepo: ConversionRepo) :
    ViewModel() {
     val conversionList = SingleLiveEvent<Resource<CurrencyListResponse>>()
    private val currencyData = SingleLiveEvent<Resource<ApiResponse>>()
    val updateData = currencyData
    val convertedRate = MutableLiveData<Double>()

    init {
        viewModelScope.launch {
            conversionRepo.getConversionList(EndPoints.API_KEY).collect {
                conversionList.value = it
            }
        }
    }

    val currencyListConversion: LiveData<Resource<CurrencyListResponse>>
        get() = conversionList

    fun getConvertedData(access_key: String) {
        viewModelScope.launch {
            conversionRepo.getConvertedData(access_key).collect {
                updateData.value = it
            }
        }
    }

    fun getConversionList(access_key: String): Resource<CurrencyListResponse>? {

        return conversionList.value
    }


}