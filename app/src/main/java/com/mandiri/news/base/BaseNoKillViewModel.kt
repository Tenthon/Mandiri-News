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
abstract class BaseNoKillViewModel : ViewModel() {

    val disposables = CompositeDisposable()
    val event = SingleLiveEvent<Events>()
    val event2 = SingleLiveEvent<Events>()
    val events = SingleLiveEvent<Events>()
    val events2 = SingleLiveEvent<Events>()
    val events3 = SingleLiveEvent<Events>()
    val events4 = SingleLiveEvent<Events>()
    val events5 = SingleLiveEvent<Events>()

    fun launch(job: () -> Disposable) {
        disposables.add(job())
    }

    @CallSuper
    override fun onCleared() {
        super.onCleared()
    }
}

