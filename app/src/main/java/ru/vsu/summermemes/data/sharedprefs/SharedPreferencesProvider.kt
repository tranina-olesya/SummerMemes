package ru.vsu.summermemes.data.sharedprefs

import android.content.Context
import android.content.SharedPreferences
import ru.vsu.summermemes.data.sharedprefs.SharedPreferencesConstants.APP_SHARED_PREFS_NAME

object SharedPreferencesProvider {
    fun getSharedPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(APP_SHARED_PREFS_NAME, Context.MODE_PRIVATE)
    }
}