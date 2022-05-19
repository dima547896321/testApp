package com.loadmovietestapp.app.flow.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.loadmovietestapp.app.R
import com.loadmovietestapp.app.data.network.Movie
import com.loadmovietestapp.app.databinding.VhAdapterFooterBinding
import com.loadmovietestapp.app.databinding.VhAdapterMovieBinding
import com.loadmovietestapp.app.flow.main.PaginationState.ERROR
import com.loadmovietestapp.app.utils.gone
import com.loadmovietestapp.app.utils.loadPoster
import com.loadmovietestapp.app.utils.show

class FooterAdapter(private val onRetry: () -> Unit) :
    ListAdapter<PaginationState, FooterAdapter.VH>(object :
        DiffUtil.ItemCallback<PaginationState>() {
        override fun areItemsTheSame(oldItem: PaginationState, newItem: PaginationState): Boolean {
            return oldItem.ordinal == newItem.ordinal
        }

        override fun areContentsTheSame(
            oldItem: PaginationState,
            newItem: PaginationState
        ): Boolean {
            return oldItem.ordinal == newItem.ordinal
        }
    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(
            VhAdapterFooterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(getItem(position))

    inner class VH(private val binding: VhAdapterFooterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.buttonRetry.setOnClickListener {
                onRetry()
            }
        }

        fun bind(item: PaginationState) {
            when (item) {
                PaginationState.LOADING -> {
                    binding.run {
                        clRoot.show()
                        buttonRetry.gone()
                        tvError.gone()
                        progressBar.show()
                        tvNoData.gone()
                    }
                }
                ERROR -> {
                    binding.run {
                        clRoot.show()
                        buttonRetry.show()
                        tvError.show()
                        progressBar.gone()
                        tvNoData.gone()
                    }
                }
                PaginationState.NO_ITEMS -> {
                    binding.run {
                        clRoot.show()
                        buttonRetry.gone()
                        tvError.gone()
                        progressBar.gone()
                        tvNoData.show()
                    }
                }
                else -> {
                    // should not be here
                }
            }
        }
    }
}


class MovieAdapter(var actionOpenMovieInfo: ((Movie) -> Unit)? = null) :
    ListAdapter<Movie, MovieVH>(object :
        DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.imdbID == newItem.imdbID
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie) =
            oldItem == newItem
    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieVH {
        return MovieVH(
            VhAdapterMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            actionOpenMovieInfo
        )
    }

    override fun onBindViewHolder(holder: MovieVH, position: Int) = holder.bind(getItem(position))
}

class MovieVH(
    private val binding: VhAdapterMovieBinding,
    var actionOpenMovieInfo: ((Movie) -> Unit)? = null
) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener {
            data?.let {
                actionOpenMovieInfo?.invoke(it)
            }
        }
    }

    var data: Movie? = null
    fun bind(item: Movie) {
        data = item
        data?.let {
            binding.ivPoster.loadPoster(it.poster)
            binding.tvMovieName.text = it.title
            binding.tvMovieYear.text =
                binding.tvMovieYear.resources.getString(R.string.movie_created_year, it.year)
        }
    }
}
