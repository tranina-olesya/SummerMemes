package ru.vsu.summermemes.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.vsu.summermemes.BuildConfig


object NetworkService {
    const val BASE_URL = "https://demo3161256.mockable.io"

    fun create(): MemesAPI {
        return create(buildRetrofit(buildOkHttp()))
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

    private fun create(retrofit: Retrofit): MemesAPI {
        return retrofit.create(MemesAPI::class.java)
    }
}