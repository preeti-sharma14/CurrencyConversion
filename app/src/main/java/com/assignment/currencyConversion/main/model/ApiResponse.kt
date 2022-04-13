package com.assignment.currencyConversion.main.model

data class ApiResponse(
    val success: Boolean,
    val timestamp: Int? = 0,
    val base: String? = null,
    val date: String? = null,
    val rates: HashMap<String, Rates>? = HashMap()
)

