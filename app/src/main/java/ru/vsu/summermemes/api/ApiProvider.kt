package ru.vsu.summermemes.api

import retrofit2.Retrofit
import ru.vsu.summermemes.api.interfaces.AuthAPI
import ru.vsu.summermemes.api.interfaces.MemeAPI

object ApiProvider {

    val authAPI by lazy {
        createAuthAPI(NetworkService.buildRetrofit(NetworkService.buildOkHttp()))
    }

    val memeAPI by lazy {
        createMemeAPI(NetworkService.buildRetrofit(NetworkService.buildOkHttpWithAuth()))
    }


    private fun createAuthAPI(retrofit: Retrofit): AuthAPI {
        return retrofit.create(AuthAPI::class.java)
    }

    private fun createMemeAPI(retrofit: Retrofit): MemeAPI {
        return retrofit.create(MemeAPI::class.java)
    }
}