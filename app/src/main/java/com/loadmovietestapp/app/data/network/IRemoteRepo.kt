package com.loadmovietestapp.app.data.network

import io.reactivex.Single

interface IRemoteRepo {
    fun searchMovie(
        apiKey: String,
        searchQuery: String
    ): Single<SearchResponse>
}