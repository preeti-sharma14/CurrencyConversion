package com.assignment.currencyConversion.main.Utils

import com.assignment.currencyConversion.main.data.network.BaseError
import com.assignment.currencyConversion.main.data.network.remote.AuthorizationException
import com.assignment.currencyConversion.main.data.network.remote.HTTPBadRequest
import com.assignment.currencyConversion.main.data.network.remote.HTTPNotFoundException
import com.assignment.currencyConversion.main.domain.helper.ErrorCode.Companion.HTTP_ACCESS_KEY_INVALID
import com.assignment.currencyConversion.main.domain.helper.ErrorCode.Companion.HTTP_ACCOUNT_INACTIVE
import com.assignment.currencyConversion.main.domain.helper.ErrorCode.Companion.HTTP_END_POINT_NOT_EXIST
import com.assignment.currencyConversion.main.domain.helper.ErrorCode.Companion.HTTP_MONTHLY_PLAN_EXHAUST
import com.assignment.currencyConversion.main.domain.helper.ErrorCode.Companion.UNKNOWN_ERROR
import com.assignment.currencyConversion.main.domain.model.Status
import com.google.gson.Gson
import retrofit2.HttpException
import java.io.IOException
import java.io.PrintWriter
import java.io.StringWriter
import java.net.HttpURLConnection.*

object ErrorUtil {
    fun getHTTPCode(e: Throwable): String = when (e) {
        is HttpException -> {
            getHTTPCodeError(e.response()?.code() ?: 0)
        }
        is AuthorizationException -> HTTP_ACCESS_KEY_INVALID
        is HTTPBadRequest -> HTTP_ACCOUNT_INACTIVE
        is HTTPNotFoundException -> HTTP_END_POINT_NOT_EXIST
        else -> UNKNOWN_ERROR

    }

    fun getHTTPCodeError(code: Int): String =
        when (code) {
            HTTP_BAD_REQUEST -> HTTP_ACCOUNT_INACTIVE
            HTTP_UNAUTHORIZED -> HTTP_ACCESS_KEY_INVALID
            HTTP_PAYMENT_REQUIRED -> HTTP_MONTHLY_PLAN_EXHAUST
            HTTP_BAD_METHOD -> HTTP_END_POINT_NOT_EXIST
            else -> UNKNOWN_ERROR
        }

    fun getErrorStatus(e: Throwable): Status =
        if (e is HttpException) {
            getResponseStatus(e.response()?.errorBody()?.string().safeGet())
        }
    else
        {
        val stringWriter=StringWriter()
        e.printStackTrace(PrintWriter(stringWriter))
        Status(100,UNKNOWN_ERROR)
        }

    fun getResponseStatus(error: String): Status {
        try {
            val baseError = Gson().fromJson(error, BaseError::class.java)
            if (baseError != null) {
                baseError.let {
                    it.error?.let { error ->
                        return Status(
                            code = error.code,
                            message = error.message
                        )
                    }
                }
            } else{
                return Status(code=100,
                message = "Something Wrong"
                )
            }
        }catch (e:Exception){
            return Status(100,message = "Response Error")
        }
        return Status(100, UNKNOWN_ERROR)
    }
}