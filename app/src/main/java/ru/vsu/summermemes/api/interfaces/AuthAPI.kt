package ru.vsu.summermemes.api.interfaces

import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST
import ru.vsu.summermemes.models.AuthRequestEntity
import ru.vsu.summermemes.models.AuthResponseEntity

interface AuthAPI {
    @POST("/auth/login")
    fun login(@Body authRequestEntity: AuthRequestEntity): Observable<AuthResponseEntity>
}