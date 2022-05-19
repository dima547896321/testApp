package com.loadmovietestapp.app.flow.main

import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.loadmovietestapp.app.data.network.Movie
import com.loadmovietestapp.app.databinding.ActivityMainBinding
import com.loadmovietestapp.app.flow.BaseActivity
import com.loadmovietestapp.app.flow.details.Details
import com.loadmovietestapp.app.utils.RxSearchObservable
import com.loadmovietestapp.app.utils.safeDispose
import com.loadmovietestapp.app.utils.subscribe
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import org.koin.androidx.viewmodel.ext.android.getViewModel
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : BaseActivity<MainVM>() {

    lateinit var binding: ActivityMainBinding

    private var searchViewDisposable: Disposable? = null

    private var footerAdapter = FooterAdapter(::onRetryFeature)
    private var movieAdapter = MovieAdapter(::openMovieDetails)
    private var concatAdapter = ConcatAdapter(movieAdapter, footerAdapter)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvList.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = concatAdapter
        }

        searchViewDisposable =
            RxSearchObservable.fromView(binding.vSearch)
                .debounce(150, TimeUnit.MILLISECONDS)
                .filter { text -> text.isNotEmpty() }
                .map { text -> text.lowercase(Locale.getDefault()).trim() }
                .distinctUntilChanged()
                .switchMap { s -> Observable.just(s) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { query ->
                    mViewModel.startNewSearch(query)
                }
        subscribeOnLiveData()
    }

    override val mViewModel: MainVM
        get() = getViewModel()

    fun subscribeOnLiveData() {
        subscribe(mViewModel.dataListLivaData) {
            movieAdapter.submitList(it)
        }
        subscribe(mViewModel.loadingState) {
            if (it == PaginationState.IDLE) {
                footerAdapter.submitList(emptyList())
            } else {
                footerAdapter.submitList(listOf(it))
            }
        }
        subscribe(mViewModel.toastMessageLiveData) {
            Toast.makeText(this, getString(it), Toast.LENGTH_LONG).show()
        }
    }

    private fun onRetryFeature() = mViewModel.retryLoad()
    private fun openMovieDetails(movie: Movie) {
        Details.start(this, movie)
    }

    override fun onDestroy() {
        super.onDestroy()
        searchViewDisposable.safeDispose()
    }
}

