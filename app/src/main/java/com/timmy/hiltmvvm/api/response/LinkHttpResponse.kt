package com.timmy.hiltmvvm.api.response

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken


/**
 * 2023-07-07 09:57
 * Link的回應包裝
 */

data class LinkHttpResponse<T>(
    @SerializedName("code")
    val code: Int = 0,
    @SerializedName("data")
    val `data`: T? = null,
    @SerializedName("msg")
    val msg: String? = null,
    @SerializedName("serverId")
    val serverId: Long = 0,
    @SerializedName("clientId")
    val clientId: String? = null,
    @SerializedName("timestamp")
    val timestamp: Long = 0
) {
    companion object {

        inline fun <reified T: Any> String.getErrorOrDataList():List<T> = ( kotlin.runCatching {
            // 成功的時候msg會沒值，因此要先取msg，如果沒有msg，才回傳後面的正確內容。
            return@runCatching getError<T>() ?: getData<List<T>>()
        }.getOrNull() as?  List<T>)?: emptyList()

        inline fun <reified T:Any> String.getErrorOrData() = kotlin.runCatching {
            // 成功的時候msg會沒值，因此要先取msg，如果沒有msg，才回傳後面的正確內容。
            return@runCatching getError<T>() ?: getData<T>()
        }.getOrNull() ?: "回應轉型錯誤，原因可能是回應的msg和data皆為null，或者是傳入類型不符。"

        inline fun <reified T> String.getResponse() = kotlin.runCatching {
            return@runCatching Gson().fromJson<LinkHttpResponse<T>>(this, object : TypeToken<LinkHttpResponse<T>>() {}.type)
        }.getOrNull()

        inline fun <reified T> String.getError() = kotlin.runCatching {
            return@runCatching (Gson().fromJson<LinkHttpResponse<T>>(this, object : TypeToken<LinkHttpResponse<T>>() {}.type).msg)
        }.getOrNull()

        inline fun <reified T> String.getData(): T? = kotlin.runCatching {
            Gson().let {gson->
                return@runCatching (gson.fromJson<T>(
                    gson. // 拆第二層
                    toJson(
                        gson.      // 重新轉Json
                        fromJson<LinkHttpResponse<T>>(this, object : TypeToken<LinkHttpResponse<T>>() {}.type).data
                    ), // 拆第一層包裝(由於可能是Ｌist或單一類別，因此比較複雜)
                    object : TypeToken<T>() {}.type
                ))
            }
        }.getOrNull()


        inline fun <reified T> String.getCode() = kotlin.runCatching {
            return@runCatching (Gson().fromJson<LinkHttpResponse<T>>(this, object : TypeToken<LinkHttpResponse<T>>() {}.type).code)
        }.getOrDefault(-1000)
    }
}
