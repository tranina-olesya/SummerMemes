package ru.vsu.summermemes.data.sharedprefs.storages

import android.content.SharedPreferences
import ru.vsu.summermemes.data.sharedprefs.SharedPreferencesConstants

class AuthStorage(val sharedPreferences: SharedPreferences) {

    fun saveAccessToken(token: String) {
        val editor = sharedPreferences.edit()
        editor.putString(SharedPreferencesConstants.SHARED_PREFS_ACCESS_TOKEN, token)
        editor.apply()
    }

    fun getAccessToken(): String? {
        return sharedPreferences.getString(SharedPreferencesConstants.SHARED_PREFS_ACCESS_TOKEN, null)
    }
}