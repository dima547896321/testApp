package com.loadmovietestapp.app.flow.main

import androidx.lifecycle.MutableLiveData
import com.loadmovietestapp.app.R
import com.loadmovietestapp.app.data.network.Movie
import com.loadmovietestapp.app.data.network.isSuccess
import com.loadmovietestapp.app.data.providers.IMovieProvider
import com.loadmovietestapp.app.flow.BaseVM
import com.loadmovietestapp.app.utils.RxUtils
import com.loadmovietestapp.app.utils.hasNetwork
import com.loadmovietestapp.app.utils.safeDispose
import io.reactivex.disposables.Disposable
import timber.log.Timber

class MainVM(
    private val movieProvider: IMovieProvider
) : BaseVM() {


    private var searchMoviesDisposable: Disposable? = null
    private var searchStringString: String = ""

    val dataListLivaData = MutableLiveData<List<Movie>>()
        .apply { value = emptyList() }

    var loadingState: MutableLiveData<PaginationState> = MutableLiveData<PaginationState>().apply {
        value = PaginationState.IDLE
    }

    fun startNewSearch(query: String) {
        searchStringString = query
        searchRemote()
    }

    fun retryLoad() {
        searchRemote()
    }

    private fun searchRemote() {
        if (hasNetwork()) {
            searchMoviesDisposable.safeDispose()

            searchMoviesDisposable = movieProvider.searchMovie(
                searchStringString
            )
                .compose(RxUtils.ioToMainTransformerSingle())
                .subscribe(
                    {
                        Timber.d(it.toString())
                        if (it.isSuccess()) {
                            dataListLivaData.value = it.dataList
                            loadingState.value = PaginationState.IDLE
                        } else {
                            dataListLivaData.value = emptyList()
                            loadingState.value = PaginationState.ERROR
                        }

                    },
                    {
                        Timber.e(it)
                        // requirements
                        loadingState.value = PaginationState.ERROR
                    })
            compositeDisposable.add(searchMoviesDisposable!!)
        } else {
            toastMessageLiveData.value = R.string.no_internet_connection
        }
    }
}

enum class PaginationState {
    IDLE,
    LOADING,
    NO_ITEMS,
    ERROR
}