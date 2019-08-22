package ru.vsu.summermemes.api.repositories

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.vsu.summermemes.api.NetworkService
import ru.vsu.summermemes.models.AuthRequestEntity
import ru.vsu.summermemes.models.AuthResponseEntity

class AuthRepository {
    val authAPI = NetworkService.authAPI

    fun login(authRequestEntity: AuthRequestEntity): Observable<AuthResponseEntity> {
        return authAPI
            .login(authRequestEntity)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }
}