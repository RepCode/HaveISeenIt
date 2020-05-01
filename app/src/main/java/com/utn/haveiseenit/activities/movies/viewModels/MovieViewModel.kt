package com.utn.haveiseenit.activities.movies.viewModels

import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import androidx.lifecycle.*
import coil.Coil
import coil.request.LoadRequest
import com.utn.haveiseenit.database.MovieDao
import com.utn.haveiseenit.database.appDatabase
import com.utn.haveiseenit.entities.Movie
import com.utn.haveiseenit.services.ApplicationUser

class MovieViewModel(application: Application) : AndroidViewModel(application) {
    var movieDao: MovieDao = appDatabase.getAppDataBase(application)!!.movieDao()

    private val movies: MutableLiveData<List<MovieView>> by lazy {
        MutableLiveData<List<MovieView>>().also {
        }
    }

    fun getMovies(): LiveData<List<MovieView>> {
        if (movies.value.isNullOrEmpty()) {
            loadMovies()
        }
        return movies
    }

    private fun loadMovies() {
        val movieList = mutableListOf<MovieView>()
        movieDao.loadUserMovies(ApplicationUser.userId!!)?.forEach { it->
            movieList.add(MovieView(it!!))
        }
        movies.value = movieList
    }

    fun updateMovieStatus(position: Int, status: String){
        movies.value!![position].movie.status = status
        movieDao.updateMovieStatus(movies.value!![position].movie.id, status)
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