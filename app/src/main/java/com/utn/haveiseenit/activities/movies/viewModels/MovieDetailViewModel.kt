package com.utn.haveiseenit.activities.movies.viewModels

import android.app.Application
import android.graphics.drawable.Drawable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.utn.haveiseenit.activities.movies.viewModels.models.MovieModel
import com.utn.haveiseenit.database.MovieDao
import com.utn.haveiseenit.database.appDatabase

class MovieDetailViewModel(val app: Application) : AndroidViewModel(app) {
    var movieDao: MovieDao = appDatabase.getAppDataBase(app)!!.movieDao()

    private val movieModel = MutableLiveData<MovieModel>()

    fun changeMovieStatus(status: String){
        movieModel.value?.movie?.status = status
        movieDao.updateMovieStatus(movieModel.value!!.movie.id, status)
    }

    fun getMovie(): LiveData<MovieModel> {
        return movieModel
    }

    fun setMovie(movieModel: MovieModel) {
        this.movieModel.value = movieModel
    }
}