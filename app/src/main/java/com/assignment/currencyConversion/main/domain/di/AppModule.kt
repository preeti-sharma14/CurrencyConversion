package com.assignment.currencyConversion.main.domain.di

import android.app.Application
import android.content.Context
import com.assignment.currencyConversion.main.domain.helper.EndPoints
import com.assignment.currencyConversion.main.data.network.ApiDataSource
import com.assignment.currencyConversion.main.data.network.ApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    private val READ_TIMEOUT=30L
    private val WRITE_TIMEOUT=30L
    //API Base Url
    @Provides
    fun providesBaseUrl() = EndPoints.BASE_URL

    //Gson for converting JSON String to Java Objects
    @Provides
    fun providesGson() : Gson = GsonBuilder().setLenient().create()

    //Retrofit for networking
    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson) : Retrofit = Retrofit.Builder()
        .baseUrl(EndPoints.BASE_URL)
        .client(
            OkHttpClient.Builder().also { client ->
                val logging = HttpLoggingInterceptor()
                logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                client.addInterceptor(logging)
                client.connectTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                client.readTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                client.protocols(Collections.singletonList(Protocol.HTTP_1_1))

        }.build()
        )
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    //Api Service with retrofit instance
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) : ApiService = retrofit.create(ApiService::class.java)

    //Class helper with apiService Interface
    @Provides
    @Singleton
    fun provideApiDatSource(apiService: ApiService) = ApiDataSource(apiService)

    @Provides
    @Singleton
    fun provideContext(application: Application): Context?
    {
        return application.applicationContext
    }
}