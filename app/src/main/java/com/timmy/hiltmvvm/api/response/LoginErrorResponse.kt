package com.timmy.linksdktest.apiResponse
import com.google.gson.annotations.SerializedName
import com.timmy.hiltmvvm.api.response.Loginfo
import com.timmy.hiltmvvm.api.response.Payload


data class LoginErrorResponse(
    @SerializedName("clientId")
    val clientId: String,
    @SerializedName("cmd")
    val cmd: String,
    @SerializedName("loginfo")
    val loginfo: Loginfo,
    @SerializedName("payload")
    val payload: Payload,
    @SerializedName("remark")
    val remark: String,
    @SerializedName("serverId")
    val serverId: Long,
    @SerializedName("timestamp")
    val timestamp: Long
)
