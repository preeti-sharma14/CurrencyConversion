package com.assignment.currencyConversion.main.domain.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Status(
    @Expose @SerializedName("code") val code: Int,
    @Expose @SerializedName("info") val message: String? = ""
)
{
    companion object {
        fun withCode(code:Int):Status{
            return Status(code,"No Server message")
        }
    }
}