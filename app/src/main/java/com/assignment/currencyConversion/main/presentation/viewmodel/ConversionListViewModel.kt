package com.assignment.currencyConversion.main.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.currencyConversion.main.helper.Resource
import com.assignment.currencyConversion.main.helper.SingleLiveEvent
import com.assignment.currencyConversion.main.model.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConversionListViewModel @Inject constructor(private val conversionRepo: ConversionRepo) :
    ViewModel() {

    private val data=SingleLiveEvent<Resource<ApiResponse>>()
     val updateData =data
    val convertedRate = MutableLiveData<Double>()

    fun getConvertedData(access_key: String) {
        viewModelScope.launch {
            conversionRepo.getConvertedData(access_key).collect {
                updateData.value = it
            }
        }
    }

}