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
    private val selectedIds = MutableLiveData<MutableList<Int>>()
    private val hasSelectedIds = MutableLiveData<Boolean>()
    private val hasMovies = MutableLiveData<Boolean>(false)

    fun hasSelectedMovies(): LiveData<Boolean> {
        return hasSelectedIds
    }

    fun removeSelectedMovie(id: Int) {
        selectedIds.value!!.remove(id)
        hasSelectedIds.value = !selectedIds.value.isNullOrEmpty()
    }

    fun addSelectedMovie(id: Int) {
        if (selectedIds.value == null) {
            selectedIds.value = mutableListOf()
        }
        selectedIds.value!!.add(id)
        hasSelectedIds.value = !selectedIds.value.isNullOrEmpty()
    }

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
        hasSelectedIds.value = !selectedIds.value.isNullOrEmpty()
    }

    fun deleteMovies() {
        movieDao.deleteMoviesByIds(selectedIds.value!!.toTypedArray())
        selectedIds.value = mutableListOf()
        loadMovies()
    }
}