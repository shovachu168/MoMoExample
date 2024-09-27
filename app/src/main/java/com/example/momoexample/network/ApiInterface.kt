package com.example.momoexample.network

import com.example.momoexample.datamodel.PageModel
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("mock_data.json")
    fun getPageInfo(): Call<PageModel>
}