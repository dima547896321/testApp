package com.loadmovietestapp.app.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.loadmovietestapp.app.data.database.DatabaseRepoImpl.Companion.DATA_BASE_VERSION
import com.loadmovietestapp.app.data.database.dao.FavoritesDAO
import com.loadmovietestapp.app.data.database.entities.Favorite

@Database(entities = [Favorite::class], version = DATA_BASE_VERSION)
abstract class AppDatabase : RoomDatabase() {
    abstract val favoritesDAO: FavoritesDAO
}