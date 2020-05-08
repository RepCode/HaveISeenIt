package com.utn.haveiseenit.activities.movies.viewModels

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.lifecycle.ViewModel
import coil.Coil
import coil.request.LoadRequest
import com.utn.haveiseenit.entities.Movie
import com.utn.haveiseenit.entities.MovieStatuses
import com.utn.haveiseenit.services.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NewMovieViewModel : ViewModel() {
    fun setMovieData(movie: MovieResponse) {
        title = movie.title
        posterPath = movie.posterPath
        tmdbId = movie.tmdbId
        year = if (movie.releaseDate.isNullOrEmpty()) null else movie.releaseDate
            .substring(0, 4).toInt()
    }

    fun getTitle() = title
    fun getYearText() = if (year != null) year.toString() else ""
    fun loadPoster(context: Context, onPosterLoaded: (drawable: Drawable) -> Unit) {
        val request = LoadRequest.Builder(context)
            .data("https://image.tmdb.org/t/p/w92${posterPath}")
            .target { drawable ->
                posterDrawable = drawable
                onPosterLoaded(drawable)
            }
            .build()
        Coil.execute(request)
    }

    fun getCredits(onCastObtained: (director: String) -> Unit) {
        GlobalScope.launch(Dispatchers.Default) {
            val call = getRetrofit().create(APIService::class.java)
                .getMovieCredits("movie/$tmdbId/credits?api_key=486d247609821da0b98bb27f87b76be3")
                .execute()
            val response = call.body() as MovieCreditsResponse
            launch(Dispatchers.Main) {
                val directorObject = response.crew.firstOrNull { it.job == "Director" }
                director = directorObject?.name ?: ""
                onCastObtained(director)
            }
        }
    }

    fun getDetails(onDetailsObtained: (duration: Int) -> Unit) {
        GlobalScope.launch(Dispatchers.Default) {
            val call = getRetrofit().create(APIService::class.java)
                .getMovieDetails("movie/$tmdbId?api_key=486d247609821da0b98bb27f87b76be3")
                .execute()
            val response = call.body() as MovieDetailResponse
            launch(Dispatchers.Main) {
                duration = response.duration
                onDetailsObtained(duration!!)
            }
        }
    }

    fun getDBMovie(): Movie {
        return Movie(
            0,
            ApplicationUser.userId!!,
            tmdbId!!,
            posterPath,
            title,
            director,
            null,
            year!!,
            duration!!,
            MovieStatuses.pending
        )
    }

    private var title = ""
    private var director = ""
    private var duration: Int? = null
    var posterPath = ""
    var tmdbId: Long? = null
    private var year: Int? = null
    private var posterDrawable: Drawable? = null
}
