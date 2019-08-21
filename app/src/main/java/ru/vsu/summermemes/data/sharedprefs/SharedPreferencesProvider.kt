package ru.vsu.summermemes.data.sharedprefs

import android.content.Context
import android.content.SharedPreferences
import ru.vsu.summermemes.data.sharedprefs.SharedPreferencesConstants.SHARED_PREFS_ACCESS_TOKEN
import ru.vsu.summermemes.data.sharedprefs.SharedPreferencesConstants.SHARED_PREFS_FIRST_NAME
import ru.vsu.summermemes.data.sharedprefs.SharedPreferencesConstants.SHARED_PREFS_LAST_NAME
import ru.vsu.summermemes.data.sharedprefs.SharedPreferencesConstants.SHARED_PREFS_USERNAME
import ru.vsu.summermemes.data.sharedprefs.SharedPreferencesConstants.SHARED_PREFS_USER_DESCRIPTION
import ru.vsu.summermemes.data.sharedprefs.SharedPreferencesConstants.SHARED_PREFS_USER_ID
import ru.vsu.summermemes.data.sharedprefs.SharedPreferencesConstants.USER_SHARED_PREFS_NAME
import ru.vsu.summermemes.models.UserInfo

class SharedPreferencesProvider(context: Context) {

    private var sharedPreferences: SharedPreferences = context.getSharedPreferences(USER_SHARED_PREFS_NAME, Context.MODE_PRIVATE)

    fun saveUserInfo(userInfo: UserInfo) {
        val editor = sharedPreferences.edit()

        editor.putLong(SHARED_PREFS_USER_ID, userInfo.id)
        editor.putString(SHARED_PREFS_USERNAME, userInfo.username)
        editor.putString(SHARED_PREFS_FIRST_NAME, userInfo.firstName)
        editor.putString(SHARED_PREFS_LAST_NAME, userInfo.lastName)
        editor.putString(SHARED_PREFS_USER_DESCRIPTION, userInfo.userDescription)

        editor.apply()
    }

    fun saveAccessToken(token: String) {
        val editor = sharedPreferences.edit()
        editor.putString(SHARED_PREFS_ACCESS_TOKEN, token)
        editor.apply()
    }

    fun getAccessToken(): String? {
        return sharedPreferences.getString(SHARED_PREFS_ACCESS_TOKEN, null)
    }

    fun getUserInfo(): UserInfo? {
        if (sharedPreferences.contains(SHARED_PREFS_USER_ID) &&
                sharedPreferences.contains(SHARED_PREFS_USERNAME) &&
                sharedPreferences.contains(SHARED_PREFS_FIRST_NAME) &&
                sharedPreferences.contains(SHARED_PREFS_LAST_NAME) &&
                sharedPreferences.contains(SHARED_PREFS_USER_DESCRIPTION)) {
            val userInfo = UserInfo(
                    sharedPreferences.getLong(SHARED_PREFS_USER_ID, 0),
                    sharedPreferences.getString(SHARED_PREFS_USERNAME, "") ?: "",
                    sharedPreferences.getString(SHARED_PREFS_FIRST_NAME, "") ?: "",
                    sharedPreferences.getString(SHARED_PREFS_LAST_NAME, "") ?: "",
                    sharedPreferences.getString(SHARED_PREFS_USER_DESCRIPTION, "") ?: ""
            )
            return userInfo
        }
        return null
    }
}