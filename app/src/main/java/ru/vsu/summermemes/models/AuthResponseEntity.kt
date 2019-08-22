package ru.vsu.summermemes.models

import com.google.gson.annotations.SerializedName

data class AuthResponseEntity(
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("userInfo")
    val userInfo: UserInfo
)