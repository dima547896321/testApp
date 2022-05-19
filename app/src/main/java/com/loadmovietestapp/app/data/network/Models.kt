package com.loadmovietestapp.app.data.network

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

data class SearchResponse(
    @SerializedName("Search") var dataList: List<Movie> = emptyList(),
    @SerializedName("totalResults") var totalResults: Int = 0,
    @SerializedName("Response") var response: String = "False"
)

fun SearchResponse.isSuccess() = response.contentEquals("True")

@Parcelize
data class Movie(
    @SerializedName("Title") var title: String = "",
    @SerializedName("Year") var year: String = "",
    @SerializedName("imdbID") var imdbID: String = "",
    @SerializedName("Type") var type: String = "",
    @SerializedName("Poster") var poster: String = "",
): Parcelable