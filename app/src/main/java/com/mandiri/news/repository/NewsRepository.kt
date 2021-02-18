package com.mandiri.news.repository

import com.mandiri.news.data.remote.ApiClient
import com.mandiri.news.model.ResponseNews
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

interface NewsRepository {
    fun getData(
        param: HashMap<String, String>,
        onResult: (isSuccess: Boolean?, messages: String?, datas: ResponseNews?) -> Unit
    ): Disposable
}

class NewsRepositoryImpl(private val apiClient: ApiClient) : NewsRepository {
    override fun getData(
        param: HashMap<String, String>,
        onResult: (isSuccess: Boolean?, messages: String?, datas: ResponseNews?) -> Unit
    ): Disposable =
        apiClient.news(param).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                if (it.isSuccessful) {
                    val resp = it.body()
                    if (resp != null) {
                        onResult(true, "${it.message()}", resp)
                    } else onResult(false, "${it.message()}", null)
                } else onResult(false, it.message(), null)
            }, {
                onResult(false, "${it.message}", null)
            })
}