package com.mandiri.news.di


import com.mandiri.news.repository.NewsRepository
import com.mandiri.news.repository.NewsRepositoryImpl
import com.mandiri.news.view.view_model.NewsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { NewsViewModel(get(), get()) }


    single<NewsRepository>(createdAtStart = true) { NewsRepositoryImpl(get()) }


}
