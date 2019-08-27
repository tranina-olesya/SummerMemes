package ru.vsu.summermemes.di.module

import dagger.Module
import dagger.Provides
import dagger.Reusable
import ru.vsu.summermemes.api.ApiProvider
import ru.vsu.summermemes.api.interfaces.AuthAPI
import ru.vsu.summermemes.api.interfaces.MemeAPI
import javax.inject.Singleton

@Module
@Singleton
object NetworkModule {
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideMemeAPI(): MemeAPI {
        return ApiProvider.memeAPI
    }

    @Provides
    @Reusable
    @JvmStatic
    internal fun provideAuthAPI(): AuthAPI {
        return ApiProvider.authAPI
    }

}