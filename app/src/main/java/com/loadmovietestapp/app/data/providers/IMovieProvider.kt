package com.loadmovietestapp.app.data.providers

import com.loadmovietestapp.app.data.database.entities.Favorite
import com.loadmovietestapp.app.data.network.SearchResponse
import io.reactivex.Completable
import io.reactivex.Single

interface IMovieProvider {
    fun searchMovie(
        searchQuery: String
    ): Single<SearchResponse>

    fun loadIsLikedMovie(movieId: String):Single<Favorite>

    fun changeFavoriteStatusForMovie(status: Boolean, movieId: String):Completable
}