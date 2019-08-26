package ru.vsu.summermemes.api.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import ru.vsu.summermemes.api.NetworkService

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val headers = originalRequest.headers.newBuilder()

        NetworkService.accessToken?.let { token ->
            headers.add(NetworkService.AUTH_HEADER, token)
        }

        val newRequest = originalRequest.newBuilder()
            .headers(headers.build())
            .build()

        return chain.proceed(newRequest)
    }

}