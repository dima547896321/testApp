package com.loadmovietestapp.app.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class Favorite(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "movie_id")
    var movieID: String = ""

)