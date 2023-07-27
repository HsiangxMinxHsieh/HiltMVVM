package com.timmy.hiltmvvm.api.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("groupInfo")
    val groupInfo: GroupInfo = GroupInfo(),
    @SerializedName("loginfo")
    val loginfo: Loginfo = Loginfo(),
    @SerializedName("payload")
    val payload: Payload = Payload(),
    @SerializedName("userInfo")
    val userInfo: UserInfo = UserInfo()
)

data class Loginfo(
    @SerializedName("avatar")
    val avatar: String="",
    @SerializedName("nickname")
    val nickname: String="",
    @SerializedName("token")
    val token: String="",
    @SerializedName("userid")
    val userid: String=""
)


class GroupInfo

class Payload

class UserInfo