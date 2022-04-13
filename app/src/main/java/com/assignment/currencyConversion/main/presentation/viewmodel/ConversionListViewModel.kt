package com.assignment.currencyConversion.main.presentation.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.assignment.currencyConversion.main.Utils.Event
import com.assignment.currencyConversion.main.Utils.onLoading
import com.assignment.currencyConversion.main.Utils.onSuccess
import com.assignment.currencyConversion.main.domain.UseCase.CurrencyDataUseCase
import com.assignment.currencyConversion.main.domain.helper.Resource
import com.assignment.currencyConversion.main.domain.helper.SingleLiveEvent
import com.assignment.currencyConversion.main.domain.model.ApiResponse
import com.assignment.currencyConversion.main.domain.model.CurrencyListResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ConversionListViewModel @Inject constructor(private val currencyDataUseCase: CurrencyDataUseCase) :
    ViewModel() {

    private val currencyData = SingleLiveEvent<Resource<ApiResponse>>()
    var showLoading = MutableLiveData<Event<Boolean>>()
    val updateData = currencyData
    val convertedRate = MutableLiveData<Double>()
    private val _conversionList = MutableLiveData<List<CurrencyListResponse>>()
    val conversionList:LiveData<List<CurrencyListResponse>>
      get() = _conversionList

    fun getConversionList(access_key: String) {
        currencyDataUseCase.execute().onLoading {
            showLoading.postValue(Event(true))
        }
            .onSuccess { conversionList ->
            showLoading.postValue(Event(false))
            _conversionList.postValue(conversionList.rates)
        }
    }


}