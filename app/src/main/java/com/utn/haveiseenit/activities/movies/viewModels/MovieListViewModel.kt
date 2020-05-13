package com.utn.haveiseenit.activities.movies.viewModels

import android.app.Application
import androidx.lifecycle.*
import com.utn.haveiseenit.activities.movies.viewModels.models.MovieModel
import com.utn.haveiseenit.database.MovieDao
import com.utn.haveiseenit.database.appDatabase
import com.utn.haveiseenit.entities.Movie
import com.utn.haveiseenit.services.ApplicationUser

class MovieListViewModel(application: Application) : AndroidViewModel(application) {
    var movieDao: MovieDao = appDatabase.getAppDataBase(application)!!.movieDao()

    private val movies: MutableLiveData<List<MovieModel>> by lazy {
        MutableLiveData<List<MovieModel>>().also {
        }
    }

    private val hasMovies = MutableLiveData<Boolean>(false)

    fun getMovies(): LiveData<List<MovieModel>> {
        loadMovies()
        return movies
    }

    fun hasMovies(): LiveData<Boolean> {
        return hasMovies
    }

    private fun loadMovies() {
        val movieList = mutableListOf<MovieModel>()
        movieDao.loadUserMovies(ApplicationUser.userId!!)?.forEach { it ->
            movieList.add(MovieModel(it!!))
        }
        movies.value = movieList
        hasMovies.value = !movieList.isNullOrEmpty()
    }

    private fun removeMovies(movieIds: Array<Int>) {
        movieDao.deleteMoviesByIds(movieIds)
        loadMovies()
    }
}