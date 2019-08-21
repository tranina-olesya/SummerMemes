package ru.vsu.summermemes

import android.app.Application
import android.content.Context

class SummerMemesApp : Application() {

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {
        private lateinit var appInstance: SummerMemesApp

        @JvmStatic
        fun provideContext(): Context {
            return appInstance.applicationContext
        }
    }
}