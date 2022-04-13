package com.assignment.currencyConversion.main.model

data class CurrencyListResponse(
    val success: Boolean,
    val symbols: HashMap<String, String>? = HashMap()
)

