package com.assignment.core.domain.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class BaseError (
    @Expose @SerializedName("errors") val errors:ArrayList<Error>?,
    @Expose @SerializedName("error")val error:Error?
)