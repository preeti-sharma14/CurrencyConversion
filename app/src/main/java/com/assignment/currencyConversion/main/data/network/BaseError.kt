package com.assignment.currencyConversion.main.data.network

import com.assignment.currencyConversion.main.domain.model.Error
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class BaseError (
    @Expose @SerializedName("error")val error:Error?
)