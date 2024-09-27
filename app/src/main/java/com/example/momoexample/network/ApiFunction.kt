package com.example.momoexample.network

import com.example.momoexample.datamodel.PageModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ApiFunction(private val service: ApiInterface) {
    suspend fun getPageInfo(
        success: Success<PageModel>,
        failure: FailureCallBack
    ) {
        withContext(Dispatchers.IO) {
            service.getPageInfo().handleModel(success, failure)
        }
    }
}