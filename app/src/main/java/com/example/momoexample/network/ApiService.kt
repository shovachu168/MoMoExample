package com.example.momoexample.network

import okhttp3.OkHttpClient
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class ApiService private constructor() {

    companion object {
        private val service: ApiService = ApiService()
        val sp: ApiFunction by lazy { ApiFunction(service.retrofit.create(ApiInterface::class.java)) }
    }

    private val retrofit: Retrofit
    private val baseUrl: String = "https://localhost/"
    private val timeout: Long = 10

    private val client: OkHttpClient.Builder
        get() {
            return OkHttpClient.Builder()
                .addInterceptor(MockInterceptor())
                .callTimeout(timeout, TimeUnit.SECONDS)
                .readTimeout(timeout, TimeUnit.SECONDS)
                .writeTimeout(timeout, TimeUnit.SECONDS)
        }

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
            .build()
    }
}
