package com.utn.haveiseenit.services

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

data class MovieResponse(
    @SerializedName("id") var tmdbId: Int,
    @SerializedName("poster_path") var posterPath: String,
    @SerializedName("original_title") var title: String
)

data class MoviesSearchResponse(
    @SerializedName("results") var results: List<MovieResponse>
)


interface APIService {
    @GET
    fun getMoviesByKeyword(@Url url:String): Call<MoviesSearchResponse>
}
