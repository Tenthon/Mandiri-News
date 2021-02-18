package com.mandiri.news.data.remote

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class AuthInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val request: Request = original.newBuilder()
            //.header("ApiKey", "83E215D9FEA32FAF08AA80DF8D991D77")
            //.method(original.method, original.body)
            .build()
        return chain.proceed(request)
    }
}