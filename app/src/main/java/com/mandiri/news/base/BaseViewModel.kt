package com.mandiri.news.base

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import com.mandiri.news.utils.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Base ViewModel
 * - handle Rx jobs with launch() and clear them on onCleared
 */
abstract class BaseViewModel : ViewModel() {

    val disposables = CompositeDisposable()
    val event = SingleLiveEvent<Events>()
    val event2 = SingleLiveEvent<Events>()

    fun launch(job: () -> Disposable) {
        disposables.add(job())
    }

    @CallSuper
    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}

data class Events(val isLoading: Boolean = false, val isSuccess: Boolean? = false, val message: String? = null)