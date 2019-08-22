package ru.vsu.summermemes.data.sharedprefs.repositories

import ru.vsu.summermemes.SummerMemesApp
import ru.vsu.summermemes.data.sharedprefs.SharedPreferencesProvider
import ru.vsu.summermemes.data.sharedprefs.storages.AuthStorage
import ru.vsu.summermemes.data.sharedprefs.storages.UserStorage
import ru.vsu.summermemes.models.auth.AuthResponseEntity
import ru.vsu.summermemes.models.auth.UserInfo

class UserRepository {

    private val userStorage =
        UserStorage(SharedPreferencesProvider.getSharedPrefs(SummerMemesApp.provideContext()))

    private val authStorage =
        AuthStorage(SharedPreferencesProvider.getSharedPrefs(SummerMemesApp.provideContext()))

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