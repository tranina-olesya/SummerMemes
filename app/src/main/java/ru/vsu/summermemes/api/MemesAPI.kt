package ru.vsu.summermemes.api

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import ru.vsu.summermemes.models.LoginRequestEntity
import ru.vsu.summermemes.models.LoginResponseEntity

interface MemesAPI {
    @POST("/auth/login")
    fun login(@Body loginRequestEntity: LoginRequestEntity): Call<LoginResponseEntity>
}