package com.loadmovietestapp.app.data.network

import io.reactivex.Single

class RemoteRepoImpl(private val apiService: ApiService) : IRemoteRepo {
    override fun searchMovie(apiKey: String, searchQuery: String): Single<SearchResponse> {
        return apiService.searchMovie("", apiKey, searchQuery)
    }
}