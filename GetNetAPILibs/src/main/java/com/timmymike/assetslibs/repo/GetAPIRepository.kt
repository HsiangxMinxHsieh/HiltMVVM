package com.timmymike.assetslibs.repo

import androidx.lifecycle.MutableLiveData
import com.timmymike.assetslibs.api.ApiService
import com.timmymike.base.data.SampleDataFromAPI
import retrofit2.Retrofit
import javax.inject.Inject

class GetAPIRepository @Inject constructor(/*private val context: Application*/) {

    @Inject
    lateinit var retrofit: Retrofit

    private val apiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    private val result by lazy { MutableLiveData<SampleDataFromAPI>() }

    fun getLiveDataInRealm() = result

    suspend fun getDataFromAPI() {
        val responseBody = apiService.getData()
        result.postValue(responseBody)
//        MainScope().launch {
//            //處理畫面更新
////                responseBody.articles.forEach {
////                }
//
//        }
    }


}
