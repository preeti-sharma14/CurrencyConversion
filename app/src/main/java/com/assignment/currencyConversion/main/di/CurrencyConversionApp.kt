package com.assignment.currencyConversion.main.di

import android.app.Application
import android.content.res.Configuration
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CurrencyConversionApp: Application(),LifecycleOwner {
    override fun getLifecycle(): Lifecycle {
        TODO("Not yet implemented")
    }
}