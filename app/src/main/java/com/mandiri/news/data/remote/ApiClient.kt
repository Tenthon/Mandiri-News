package com.mandiri.news.data.remote

import com.mandiri.news.model.ResponseNews
import io.reactivex.Flowable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ApiClient {
    @GET("everything")
    fun news(@QueryMap param: Map<String, String>): Flowable<Response<ResponseNews>>
}