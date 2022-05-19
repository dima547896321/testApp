package com.loadmovietestapp.app.data.database

import com.loadmovietestapp.app.data.database.entities.Favorite
import io.reactivex.Completable
import io.reactivex.Single

class DatabaseRepoImpl(private val database: AppDatabase) : IDatabaseRepo {

    companion object {
        const val DATA_BASE_VERSION = 1
    }

    override fun getFavoriteInfo(movieId: String): Single<Favorite> {
        return database.favoritesDAO.getMovieFavorite(movieId)
    }

    override fun addFavorite(entity: Favorite): Completable {
        return database.favoritesDAO.addMovieToFavorites(entity)
    }

    override fun deleteFavorite(entity: Favorite): Completable {
        return database.favoritesDAO.delete(entity)
    }
}