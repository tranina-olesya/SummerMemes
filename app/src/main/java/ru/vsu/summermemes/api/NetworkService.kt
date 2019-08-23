package ru.vsu.summermemes.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.vsu.summermemes.BuildConfig
import ru.vsu.summermemes.api.interfaces.AuthAPI
import ru.vsu.summermemes.api.interfaces.MemeAPI


object NetworkService {
    private const val BASE_URL = "https://demo3161256.mockable.io"

    val authAPI by lazy {
        createAuthAPI(buildRetrofit(buildOkHttp()))
    }

    val memeAPI by lazy {
        createMemeAPI(buildRetrofit(buildOkHttp()))
    }

    private fun buildOkHttp(): OkHttpClient {
        val builder = OkHttpClient.Builder()

        val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.networkInterceptors().add(httpLoggingInterceptor)
        }
        return builder.build()

    }

    private fun buildRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    private fun createAuthAPI(retrofit: Retrofit): AuthAPI {
        return retrofit.create(AuthAPI::class.java)
    }

    private fun createMemeAPI(retrofit: Retrofit): MemeAPI {
        return retrofit.create(MemeAPI::class.java)
    }
}