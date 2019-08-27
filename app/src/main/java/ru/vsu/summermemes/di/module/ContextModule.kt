package ru.vsu.summermemes.di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.Reusable
import ru.vsu.summermemes.SummerMemesApp
import javax.inject.Singleton


@Module
class ContextModule(val app: Application) {

    @Singleton
    @Provides
    fun provideContext(): Context {
        return app.applicationContext
    }
}