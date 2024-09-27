package com.example.momoexample.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.momoexample.datamodel.PageModel
import com.example.momoexample.network.ApiService
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class PageViewModel: ViewModel() {
    val pageLiveData: MutableLiveData<PageModel> = MutableLiveData()
    val getDataFailed: MutableLiveData<Boolean> = MutableLiveData()
    private var supervisorJob = SupervisorJob()
    private val networkDelay: Long = 1000

    fun getPageData() {
        viewModelScope.launch(supervisorJob) {
            delay(networkDelay)
            ApiService.sp.getPageInfo(success = {
                getDataFailed.postValue(false)
                pageLiveData.postValue(it)
            }, failure = {
                getDataFailed.postValue(true)
            })
        }
    }

    fun setIsLikeStatus(index: Int) {
        pageLiveData.value?.productList?.get(index)?.let {
            it.isLike = !it.isLike
        }
    }
}