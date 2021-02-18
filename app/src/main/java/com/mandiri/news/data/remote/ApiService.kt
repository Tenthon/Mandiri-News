package com.mandiri.news.data.remote

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory



object ApiService {

    fun <S> createService(serviceClass: Class<S>, okhttpClient: OkHttpClient, baseURl: String): S {
        val gson = GsonBuilder().create()

        val retrofit = Retrofit.Builder()
            .baseUrl(baseURl)
            .client(okhttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        return retrofit.create(serviceClass)
    }
}

fun api() = ApiService.createService(ApiClient::class.java, OkHttpClientFactory.create(
    interceptors = arrayOf(AuthInterceptor()),
    showDebugLog = true
), specialFun)
