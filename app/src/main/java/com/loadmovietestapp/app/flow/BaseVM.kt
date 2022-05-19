package com.loadmovietestapp.app.flow

import androidx.lifecycle.ViewModel
import com.loadmovietestapp.app.utils.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable

open class BaseVM : ViewModel() {

    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    val toastMessageLiveData = SingleLiveEvent<Int>()


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}