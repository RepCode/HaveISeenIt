package com.utn.haveiseenit.activities.movies.viewModels

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.Coil
import coil.request.LoadRequest
import com.utn.haveiseenit.entities.Movie

class MovieViewModel : ViewModel() {
    private val movies: MutableLiveData<List<MovieView>> by lazy {
        MutableLiveData<List<MovieView>>().also {
        }
    }

    fun getUsers(): LiveData<List<MovieView>> {
        if (movies.value.isNullOrEmpty()) {
            loadMovies()
        }
        return movies
    }

    private fun loadMovies() {
        movies.value = mutableListOf(
            MovieView(
                Movie(
                    1,
                    1,
                    1,
                    "/oqhuGSl6gp22rIRo52IouPhV4hl.jpg",
                    "Fight Club",
                    "David Fincher",
                    8.9f,
                    1999 as Int,
                    120,
                    "PENDING"
                )
            ), MovieView(
                Movie(
                    1,
                    1,
                    1,
                    "/cvvF42ALBMJMNFQvB5y9L9FTnI7.jpg",
                    "Oldboy",
                    "Chan-wook Park",
                    10f,
                    2003 as Int,
                    120,
                    "SEEN"
                )
            ), MovieView(
                Movie(
                    1,
                    1,
                    1,
                    "/469mCKwBK7LUWoKoXjf1LaMnu9I.jpg",
                    "American Psycho",
                    "Mary Harron",
                    7.9f,
                    2000 as Int,
                    120,
                    "IN_REVIEW"
                )
            ), MovieView(
                Movie(
                    1,
                    1,
                    1,
                    "/aJCpHDC6RoGz7d1Fzayl019xnxX.jpg",
                    "Die Hard",
                    "John McTiernan",
                    8.9f,
                    1988 as Int,
                    120,
                    "REVIEWED"
                )
            ), MovieView(
                Movie(
                    1,
                    1,
                    1,
                    "/7ANTfqz1sWpa08y7D2dZnI88Hd2.jpg",
                    "Se7en",
                    "David Fincher",
                    9.5f,
                    1995 as Int,
                    120,
                    "STARTED"
                )
            )
        )
    }
}

class MovieView(val movie: Movie) {
    private var posterDrawable: Drawable? = null

    fun loadPoster(context: Context, onPosterLoaded: (drawable: Drawable) -> Unit) {
        if(posterDrawable != null){
            return onPosterLoaded(posterDrawable!!)
        }
        val request = LoadRequest.Builder(context)
            .data("https://image.tmdb.org/t/p/w92${movie.imageURL}")
            .target { drawable ->
                posterDrawable = drawable
                onPosterLoaded(drawable)
            }
            .build()
        Coil.execute(request)
    }
}