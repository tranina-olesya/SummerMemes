package ru.vsu.summermemes.data.sharedprefs.repositories

import android.content.Context
import ru.vsu.summermemes.data.sharedprefs.SharedPreferencesProvider
import ru.vsu.summermemes.data.sharedprefs.storages.AuthStorage
import ru.vsu.summermemes.data.sharedprefs.storages.UserStorage
import ru.vsu.summermemes.models.auth.AuthResponseEntity
import ru.vsu.summermemes.models.auth.UserInfo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(val context: Context) {
    private val userStorage =
        UserStorage(SharedPreferencesProvider.getSharedPrefs(context))

    private val authStorage =
        AuthStorage(SharedPreferencesProvider.getSharedPrefs(context))

    fun saveAuthResponse(authResponseEntity: AuthResponseEntity) {
        saveAccessToken(authResponseEntity.accessToken)
        saveUserInfo(authResponseEntity.userInfo)
    }

    fun saveAccessToken(token: String) {
        authStorage.saveAccessToken(token)
    }

    fun getAccessToken(): String? {
        return authStorage.getAccessToken()
    }

    fun saveUserInfo(userInfo: UserInfo) {
        userStorage.saveUserInfo(userInfo)
    }

    fun getUserInfo(): UserInfo? {
        return userStorage.getUserInfo()
    }
}