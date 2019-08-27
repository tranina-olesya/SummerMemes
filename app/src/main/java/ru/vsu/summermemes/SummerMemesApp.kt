package ru.vsu.summermemes

import android.app.Application

class SummerMemesApp : Application() {

    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {
        lateinit var appInstance: SummerMemesApp
    }
}