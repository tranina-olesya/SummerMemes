package ru.vsu.summermemes

import android.app.Application
import android.content.Context
import androidx.room.Room
import ru.vsu.summermemes.data.db.SummerMemesDatabase

class SummerMemesApp : Application() {

    lateinit var summerMemesDatabase: SummerMemesDatabase

    override fun onCreate() {
        super.onCreate()
        appInstance = this
        summerMemesDatabase = Room.databaseBuilder(
            appInstance.applicationContext,
            SummerMemesDatabase::class.java,
            "memes.db"
        ).build()
    }

    companion object {
        private lateinit var appInstance: SummerMemesApp

        @JvmStatic
        fun provideContext(): Context {
            return appInstance.applicationContext
        }

        @JvmStatic
        fun provideDatabase(): SummerMemesDatabase {
            return appInstance.summerMemesDatabase
        }
    }
}