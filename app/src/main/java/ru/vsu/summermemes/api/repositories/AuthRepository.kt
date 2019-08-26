package ru.vsu.summermemes.api.repositories

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.vsu.summermemes.api.ApiProvider
import ru.vsu.summermemes.models.auth.AuthRequestEntity
import ru.vsu.summermemes.models.auth.AuthResponseEntity

class AuthRepository {
    val authAPI = ApiProvider.authAPI

    fun login(authRequestEntity: AuthRequestEntity): Observable<AuthResponseEntity> {
        return authAPI
            .login(authRequestEntity)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    fun logout(accessToken: String): Completable {
        return authAPI
            .logout(accessToken)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }
}