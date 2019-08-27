package ru.vsu.summermemes.api.repositories

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.vsu.summermemes.api.ApiProvider
import ru.vsu.summermemes.api.interfaces.AuthAPI
import ru.vsu.summermemes.models.auth.AuthRequestEntity
import ru.vsu.summermemes.models.auth.AuthResponseEntity
import javax.inject.Inject

class AuthRepository @Inject constructor(val authAPI: AuthAPI) {

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