package com.assignment.core.data.serialization

import com.google.gson.Gson
import com.google.gson.GsonBuilder


class GsonProvider {
    val instance: Gson by lazy {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.excludeFieldsWithoutExposeAnnotation()
        registerTypeAdapters(gsonBuilder = gsonBuilder)
        gsonBuilder.setLenient()
        gsonBuilder.setDateFormat(DateFormat.ISO_8601).create()
    }

    private fun registerTypeAdapters(gsonBuilder: GsonBuilder) {

    }
}