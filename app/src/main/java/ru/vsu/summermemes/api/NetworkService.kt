package ru.vsu.summermemes.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.vsu.summermemes.BuildConfig
import ru.vsu.summermemes.SummerMemesApp
import ru.vsu.summermemes.api.interceptors.AuthInterceptor
import ru.vsu.summermemes.data.sharedprefs.repositories.UserRepository


object NetworkService {

    private const val BASE_URL = "https://demo3161256.mockable.io"

    const val AUTH_HEADER = "Authorization"

    val accessToken: String?
        get() {
            return UserRepository(SummerMemesApp.appInstance).getAccessToken()
        }

    fun buildOkHttp(): OkHttpClient {
        val interceptors = mutableListOf<Interceptor>()
        if (BuildConfig.DEBUG) {
            val httpLoggingInterceptor =
                HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            interceptors.add(httpLoggingInterceptor)
        }
        return buildOkHttpWithInterceptors(interceptors)
    }

    fun buildOkHttpWithAuth(): OkHttpClient {
        val interceptors = mutableListOf<Interceptor>()
        interceptors.add(AuthInterceptor())
        return buildOkHttpWithInterceptors(interceptors)
    }

    fun buildRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    private fun buildOkHttpWithInterceptors(interceptors: List<Interceptor>): OkHttpClient {
        val builder = OkHttpClient.Builder()

        for (interceptor in interceptors) {
            builder.networkInterceptors().add(interceptor)
        }
        return builder.build()
    }
}