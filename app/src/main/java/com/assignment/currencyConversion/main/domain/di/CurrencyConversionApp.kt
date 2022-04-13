package com.assignment.currencyConversion.main.domain.di

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CurrencyConversionApp: Application(),LifecycleOwner {
    override fun getLifecycle(): Lifecycle {
        TODO("Not yet implemented")
    }
}