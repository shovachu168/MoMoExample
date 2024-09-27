package com.example.momoexample.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.BufferedReader
import java.io.InputStream

class MockInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url()
        val path = "res/raw/${url.pathSegments().last()}"
        Log.d("Alex","gson path is $path")
        val response = path.readFile(this)

        require(!response.isNullOrEmpty()) { "JSON file $path should exist and not be empty" }

        return Response.Builder()
            .code(200)
            .message(response)
            .request(chain.request())
            .protocol(Protocol.HTTP_1_1)
            .body(ResponseBody.create(MediaType.parse("application/json"), response))
            .addHeader("content-type", "application/json")
            .build()
    }
}

fun String.openStream(clz: Any) = clz.javaClass.classLoader?.getResourceAsStream(this)

fun InputStream.readFile() = this.bufferedReader().use(BufferedReader::readText)

fun String.readFile(clz: Any) = this.openStream(clz)?.readFile()