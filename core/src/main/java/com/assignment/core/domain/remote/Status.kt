package com.assignment.core.domain.remote

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Status(
    @Expose @SerializedName("success") val success:Boolean?,
    @Expose @SerializedName("error")val error:Error?
){

}