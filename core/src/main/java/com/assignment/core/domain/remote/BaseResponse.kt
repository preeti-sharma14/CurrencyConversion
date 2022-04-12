package com.assignment.core.domain.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

open class BaseResponse {
    @Expose
    @SerializedName("success")
    val success: Boolean? = false

    @Expose
    @SerializedName("timestamp")
    val timestamp: Int? = 0

    @Expose
    @SerializedName("base")
    val base: String? = null

    @Expose
    @SerializedName("date")
    val date: String? = null

    @Expose
    @SerializedName("rates")
    val rates: List<String>? = null

    @Expose
    @SerializedName("error")
    val serverError: Error? = null


    data class Error(
        @SerializedName("code")
        val errorCode: Int?,
        @SerializedName("info")
        val errorMessage: String?
    )
}