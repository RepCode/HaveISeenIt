package com.utn.haveiseenit.services

import com.google.gson.annotations.SerializedName
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url
import java.io.Serializable

data class MovieCastPerson(
    @SerializedName("name") var name: String,
    @SerializedName("job") var job: String
)

data class MovieCreditsResponse(
    @SerializedName("crew") var crew: List<MovieCastPerson>
)

class MovieResponse:Serializable{
    @SerializedName("id") var tmdbId: Long = 0
    @SerializedName("poster_path") var posterPath: String = ""
    @SerializedName("original_title") var title: String = ""
    @SerializedName("release_date") var releaseDate: String = ""
}

data class MoviesSearchResponse(
    @SerializedName("results") var results: List<MovieResponse>
)

data class MovieDetailResponse(
    @SerializedName("runtime") var duration: Int
)

interface APIService {
    @GET
    fun getMoviesByKeyword(@Url url:String): Call<MoviesSearchResponse>

    @GET
    fun getMovieCredits(@Url url:String): Call<MovieCreditsResponse>

    @GET
    fun getMovieDetails(@Url url:String): Call<MovieDetailResponse>
}

class HttpInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        val code = response.code()

        if (code in 400..500) {
            throw Exception()
        }
        return response
    }
}

fun getRetrofit(): Retrofit {
    val interceptor = HttpInterceptor()
    val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
    return Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
}
