package com.loadmovietestapp.app.data.network

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {

    @GET
    fun searchMovie(
        @Url url: String = "",
        @Query("apiKey") apiKey: String,
        @Query("s") searchQuery: String
    ): Single<SearchResponse>
}