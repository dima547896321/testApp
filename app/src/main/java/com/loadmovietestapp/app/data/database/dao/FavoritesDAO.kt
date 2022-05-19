package com.loadmovietestapp.app.data.database.dao

import androidx.room.*
import com.loadmovietestapp.app.data.database.entities.Favorite
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface FavoritesDAO {
    @Query("select * from favorites WHERE movie_id = :id")
    fun getMovieFavorite(id: String): Single<Favorite>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addMovieToFavorites(entity: Favorite): Completable

    @Delete
    fun delete(entity: Favorite): Completable
}