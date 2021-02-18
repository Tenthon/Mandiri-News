package com.mandiri.news.view.view_model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.mandiri.news.base.BaseViewModel
import com.mandiri.news.base.Events
import com.mandiri.news.data.local.SessionPref
import com.mandiri.news.model.ArticlesItem
import com.mandiri.news.model.ResponseNews
import com.mandiri.news.repository.NewsRepository

class NewsViewModel(private val repository: NewsRepository, private val sessionPref: SessionPref) :
    BaseViewModel() {
    val data = MutableLiveData<MutableList<ArticlesItem>>()

    fun getListNews(param: HashMap<String, String>) {
        event.value = Events(isLoading = true, message = "Mengambil data...", isSuccess = null)
        launch {
            repository.getData(param) { isSuccess, messages, datas ->
                Log.i("autolog", "datas: ${Gson().toJson(datas)}")
                event.value = Events(isLoading = false, isSuccess = isSuccess)
                if (datas != null) {
                data.value = datas.articles?.toMutableList()
                }
            }
        }
    }
}