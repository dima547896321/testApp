package com.loadmovietestapp.app.flow.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.loadmovietestapp.app.R
import com.loadmovietestapp.app.data.network.Movie
import com.loadmovietestapp.app.databinding.ActivityDetailsBinding
import com.loadmovietestapp.app.flow.BaseActivity
import com.loadmovietestapp.app.utils.loadPoster
import com.loadmovietestapp.app.utils.subscribe
import org.koin.androidx.viewmodel.ext.android.getViewModel

class Details : BaseActivity<DetailsVM>() {

    override val mViewModel: DetailsVM
        get() = getViewModel()

    lateinit var binding: ActivityDetailsBinding

    var movie: Movie? = null

    companion object {
        private const val EXTRA_INTENT_PARAM_DATA =
            "EXTRA_INTENT_PARAM_DATA"

        @JvmStatic
        fun start(context: Context, data: Movie) {
            val starter = Intent(context, Details::class.java)
                .putExtra(EXTRA_INTENT_PARAM_DATA, data)
            context.startActivity(starter)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        if (intent.hasExtra(EXTRA_INTENT_PARAM_DATA)) {
            movie = intent.getParcelableExtra(EXTRA_INTENT_PARAM_DATA) as? Movie
            mViewModel.loadIsLikedMovie(movie!!.imdbID)
        } else {
            finish()
            return
        }

        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivLike.setOnClickListener {
            mViewModel.changeFavoriteStatusForMovie(movie!!.imdbID)
        }

        binding.tvMovieName.text = movie!!.title
        binding.tvMovieType.text = getString(R.string.movie_type, movie!!.type)
        binding.ivPoster.loadPoster(movie!!.poster)
        subscribeOnLiveData()
    }

    fun subscribeOnLiveData() {
        subscribe(mViewModel.isMovieLiked) {
            binding.ivLike.isSelected = it
        }
    }
}