package com.timmy.hiltmvvm.data

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.timmy.hiltmvvm.api.ApiService
import com.timmy.sharedpreferencelibs.data.SampleDataFromAPI
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import javax.inject.Inject

class SampleRepository @Inject constructor(private val context: Application) {

    @Inject
    lateinit var retrofit: Retrofit

    private val apiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    private val result by lazy { MutableLiveData<SampleDataFromAPI>() }

    fun getLiveDataInRealm() = result

    fun init() { // 初始化，用於偵測資料庫是否有變化，或直接回傳值
    }

    suspend fun getDataFromAPI() {
        val responseBody = apiService.getData()
        result.postValue(responseBody)
        MainScope().launch {
            //處理畫面更新
//                responseBody.articles.forEach {
//                }

        }
    }


}
