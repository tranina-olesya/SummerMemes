package ru.vsu.summermemes.api.interfaces

import io.reactivex.Observable
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import ru.vsu.summermemes.models.auth.AuthRequestEntity
import ru.vsu.summermemes.models.auth.AuthResponseEntity

interface AuthAPI {
    @POST("/auth/login")
    fun login(@Body authRequestEntity: AuthRequestEntity): Observable<AuthResponseEntity>

    @POST("auth/logout")
    fun logout(@Header("Authorization") accessToken: String): Observable<Response>
}