package com.assignment.currencyConversion.main.domain.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Error(
    @Expose @SerializedName("info") val message:String,
    @Expose @SerializedName("code") val code:Int
)
