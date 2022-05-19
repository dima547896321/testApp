package com.loadmovietestapp.app.data.database

import com.loadmovietestapp.app.data.database.entities.Favorite
import io.reactivex.Completable
import io.reactivex.Single

interface IDatabaseRepo {

    fun getFavoriteInfo(movieId: String): Single<Favorite>

    fun addFavorite(entity: Favorite): Completable

    fun deleteFavorite(entity: Favorite): Completable
}