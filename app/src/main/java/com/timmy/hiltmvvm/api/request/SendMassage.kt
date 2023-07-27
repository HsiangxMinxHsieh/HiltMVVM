package com.timmy.hiltmvvm.api.request
import android.text.format.DateUtils
import com.google.gson.annotations.SerializedName
import com.timmymike.timetool.TimeUnits
import com.timmymike.timetool.nowTime


data class SendMassage(
    @SerializedName("channelId")
    val channelId: String,
    @SerializedName("channelType")
    val channelType: Int,
    @SerializedName("clientId")
    val clientId: String,
    @SerializedName("cmd")
    val cmd: String,
    @SerializedName("fromId")
    val fromId: String,
    @SerializedName("payload")
    val payload: Payload,
    @SerializedName("serverId")
    val serverId: Long,
    @SerializedName("timestamp")
    val timestamp: Long = nowTime,
    @SerializedName("type")
    val type: Int
)

data class Payload(
    @SerializedName("content")
    val content: String
)