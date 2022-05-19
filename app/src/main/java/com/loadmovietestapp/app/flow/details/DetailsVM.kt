package com.loadmovietestapp.app.flow.details

import com.loadmovietestapp.app.data.providers.IMovieProvider
import com.loadmovietestapp.app.flow.BaseVM
import com.loadmovietestapp.app.utils.RxUtils
import com.loadmovietestapp.app.utils.SingleLiveEvent
import com.loadmovietestapp.app.utils.safeDispose
import io.reactivex.disposables.Disposable
import timber.log.Timber

class DetailsVM(
    private val movieProvider: IMovieProvider
) : BaseVM() {

    private var changMovieFavoriteStatus: Disposable? = null
    private var loadMovieFavoriteStatus: Disposable? = null
    var isMovieLiked = SingleLiveEvent<Boolean>().apply {
        value = false
    }


    fun loadIsLikedMovie(movieId: String) {
        loadMovieFavoriteStatus = movieProvider.loadIsLikedMovie(movieId)
            .compose(RxUtils.ioToMainTransformerSingle())
            .subscribe({
                isMovieLiked.value = true
            }, {
                Timber.e(it)
                isMovieLiked.value = false
            })
    }

    fun changeFavoriteStatusForMovie(movieId: String) {
        changMovieFavoriteStatus.safeDispose()

        changMovieFavoriteStatus = movieProvider.changeFavoriteStatusForMovie(!isMovieLiked.value!!, movieId)
            .compose(RxUtils.ioToMainTransformerCompletable())
            .subscribe({
                isMovieLiked.value = !isMovieLiked.value!!
            }, {
                Timber.e(it)
            })
        compositeDisposable.add(changMovieFavoriteStatus!!)
    }
}