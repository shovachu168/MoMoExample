package com.example.momoexample.network

import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException


typealias Success<T> = (T) -> Unit
typealias FailureCallBack = (String?) -> Unit

fun <T> Call<T>.handleModel(
    success: Success<T>,
    failure: FailureCallBack
) {
    enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            if (response.code() == 200) {
                response.body()?.let(success) ?: failure("unKnown error handel Model")
            } else {
                failure(getErrorResponse(response.errorBody()?.string()))
            }
        }

        override fun onFailure(call: Call<T>, t: Throwable) {
            failure(getErrorMessage(t))
        }
    })
}


private fun getErrorMessage(t: Throwable): String {
    return when (t) {
        is NoConnectivityException -> "Please check network status"
        else -> t.toString()
    }
}

private fun getErrorResponse(jsonString: String?): String {
    return try {
        val json = Gson().fromJson(jsonString, ErrorResponse::class.java)
        json.message
    } catch (e: Exception) {
        jsonString!!
    }
}


class NoConnectivityException : IOException() {
    override val message: String
        get() = "No connectivity exception"
}

data class ErrorResponse(
    val message: String
)