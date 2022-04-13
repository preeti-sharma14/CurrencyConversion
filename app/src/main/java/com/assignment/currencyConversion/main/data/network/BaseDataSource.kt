package com.assignment.currencyConversion.main.data.network

import android.util.Log
import com.assignment.currencyConversion.main.domain.helper.Resource
import retrofit2.Response

abstract class BaseDataSource {
    suspend fun <T> safeApiCall(apiCall:suspend()->Response<T>):Resource<T>{
        try{
            val apiResponse=apiCall()
            if(apiResponse.isSuccessful)
            {
                val body=apiResponse.body()
                if(body!=null){
                    return Resource.success(body)
                }
            }
            return error("${apiResponse.message()}")
        }catch (e:Exception){
            return error(e.message?:e.toString())
        }
    }
    private fun <T> error(message:String):Resource<T>{
        Log.e("Remote Data Source",message)
        return Resource.error("Network call has failed for following reason:$message")
    }
}