package com.loadmovietestapp.app.data.providers

import com.loadmovietestapp.app.data.database.IDatabaseRepo
import com.loadmovietestapp.app.data.database.entities.Favorite
import com.loadmovietestapp.app.data.network.RemoteRepoImpl
import com.loadmovietestapp.app.data.network.SearchResponse
import com.loadmovietestapp.app.data.pref.PrefRepoImpl
import io.reactivex.Completable
import io.reactivex.Single

class MovieProviderImpl(
    private val remoteRepoImpl: RemoteRepoImpl,
    private val prefRepoImpl: PrefRepoImpl,
    private var databaseImpl: IDatabaseRepo
) : IMovieProvider {
    override fun searchMovie(searchQuery: String): Single<SearchResponse> {
        return remoteRepoImpl.searchMovie(
            prefRepoImpl.getApiKey(),
            searchQuery
        )
    }

    override fun loadIsLikedMovie(movieId: String): Single<Favorite> {
        return databaseImpl.getFavoriteInfo(movieId)
    }

    override fun changeFavoriteStatusForMovie(status: Boolean, movieId: String): Completable {
        return if (status) {
            databaseImpl.addFavorite(Favorite(movieId))
        } else {
            databaseImpl.deleteFavorite(Favorite(movieId))
        }
    }
}