package com.assignment.core.data.remote

import com.assignment.core.data.serialization.GsonProvider
import com.assignment.core.di.scopes.PerApplication
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@PerApplication
class NetworkProvider @Inject constructor(
    private val gsonProvider: GsonProvider,
    endPoint: String
) {
    companion object {
        private const val TAG = "NetworkProvider"
        private const val CONNECT_TIME_OUT = 120L
        private const val READ_TIME_OUT = 120L

        private fun okhttp3.Response?.safeClose() {
            this?.let {
                try {
                    it.close()
                } catch (e: Exception) {
                    //ignore
                }
            }
        }
    }

    private var retrofit: Retrofit

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(endPoint)
            .client(makeOkHttpClient())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gsonProvider.instance))
            .build()

    }

    private fun makeOkHttpClient(): OkHttpClient {
        val builder: OkHttpClient.Builder = OkHttpClient.Builder()

        builder.apply {
            addInterceptor(makeHeaderInterceptor())
            connectTimeout(CONNECT_TIME_OUT, TimeUnit.SECONDS)
            readTimeout(READ_TIME_OUT, TimeUnit.SECONDS)
        }
        return builder.build()
    }

    private fun makeHeaderInterceptor(): Interceptor {
        return Interceptor {
            it.proceed(getRequestBuilder(it.request()).build())
        }
    }

    private fun getRequestBuilder(chainRequest: Request): Request.Builder {
        val hostName = chainRequest.url.host
        val requestBuilder = chainRequest.newBuilder()
        return requestBuilder
    }

}