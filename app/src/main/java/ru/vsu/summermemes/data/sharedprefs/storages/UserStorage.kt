package ru.vsu.summermemes.data.sharedprefs.storages

import android.content.SharedPreferences
import ru.vsu.summermemes.data.sharedprefs.SharedPreferencesConstants
import ru.vsu.summermemes.models.auth.UserInfo

class UserStorage(val sharedPreferences: SharedPreferences) {

    fun saveUserInfo(userInfo: UserInfo) {
        val editor = sharedPreferences.edit()

        editor.putLong(SharedPreferencesConstants.SHARED_PREFS_USER_ID, userInfo.id)
        editor.putString(SharedPreferencesConstants.SHARED_PREFS_USERNAME, userInfo.username)
        editor.putString(SharedPreferencesConstants.SHARED_PREFS_FIRST_NAME, userInfo.firstName)
        editor.putString(SharedPreferencesConstants.SHARED_PREFS_LAST_NAME, userInfo.lastName)
        editor.putString(SharedPreferencesConstants.SHARED_PREFS_USER_DESCRIPTION, userInfo.userDescription)

        editor.apply()
    }

    fun getUserInfo(): UserInfo? {
        if (sharedPreferences.contains(SharedPreferencesConstants.SHARED_PREFS_USER_ID) &&
            sharedPreferences.contains(SharedPreferencesConstants.SHARED_PREFS_USERNAME) &&
            sharedPreferences.contains(SharedPreferencesConstants.SHARED_PREFS_FIRST_NAME) &&
            sharedPreferences.contains(SharedPreferencesConstants.SHARED_PREFS_LAST_NAME) &&
            sharedPreferences.contains(SharedPreferencesConstants.SHARED_PREFS_USER_DESCRIPTION)) {
            val userInfo = UserInfo(
                sharedPreferences.getLong(SharedPreferencesConstants.SHARED_PREFS_USER_ID, 0),
                sharedPreferences.getString(SharedPreferencesConstants.SHARED_PREFS_USERNAME, "") ?: "",
                sharedPreferences.getString(SharedPreferencesConstants.SHARED_PREFS_FIRST_NAME, "") ?: "",
                sharedPreferences.getString(SharedPreferencesConstants.SHARED_PREFS_LAST_NAME, "") ?: "",
                sharedPreferences.getString(SharedPreferencesConstants.SHARED_PREFS_USER_DESCRIPTION, "") ?: ""
            )
            return userInfo
        }
        return null
    }
}