package ru.vsu.summermemes.models.auth

import com.google.gson.annotations.SerializedName

data class AuthRequestEntity(
    @SerializedName("login")
    val login: String,
    @SerializedName("password")
    val password: String
)