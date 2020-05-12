package com.utn.haveiseenit.activities.movies.viewModels

import android.app.Application
import android.graphics.drawable.Drawable
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.utn.haveiseenit.activities.movies.viewModels.models.MovieModel
import com.utn.haveiseenit.database.MovieDao
import com.utn.haveiseenit.database.NoteDao
import com.utn.haveiseenit.database.ReviewDao
import com.utn.haveiseenit.database.appDatabase
import com.utn.haveiseenit.entities.Note
import com.utn.haveiseenit.entities.Review

class MovieDetailViewModel(val app: Application) : AndroidViewModel(app) {
    private var movieDao: MovieDao = appDatabase.getAppDataBase(app)!!.movieDao()
    private var reviewDao: ReviewDao = appDatabase.getAppDataBase(app)!!.reviewDao()
    private var noteDao: NoteDao = appDatabase.getAppDataBase(app)!!.noteDao()

    private val isEditMode = MutableLiveData<Boolean>(false)
    private val movieModel = MutableLiveData<MovieModel>()
    private val review = MutableLiveData<Review?>()
    private val notes = MutableLiveData<List<Note>>()
    private val commitChanges = MutableLiveData<Unit>()
    private val discardChanges = MutableLiveData<Unit>()

    fun getMovieNotes():LiveData<List<Note>>{
        if(notes.value.isNullOrEmpty()){
             notes.value = noteDao.loadMovieNotes(movieModel.value!!.movie.id)
        }
        return notes
    }

    fun addNote(text: String, tag: Int){
        noteDao.insertNote(Note(0, movieModel.value!!.movie.id, text, tag))
        notes.value = noteDao.loadMovieNotes(movieModel.value!!.movie.id)
    }

    fun getMovieReview(): LiveData<Review?> {
        if (review.value == null) {
            val id = movieModel.value?.movie?.id
            if (id != null) {
                review.value = movieDao.loadMovieReview(id!!)
            }
        }
        return review
    }

    fun addReview(review: Review) {
        reviewDao.insertReview(review)
    }

    fun updateReview(review: Review) {
        reviewDao.updateReview(review)
    }

    fun changeMovieStatus(status: Int) {
        movieModel.value?.movie?.status = status
        movieDao.updateMovieStatus(movieModel.value!!.movie.id, status)
        movieModel.postValue(movieModel.value)
    }

    fun getMovie(): LiveData<MovieModel> {
        return movieModel
    }

    fun setMovie(movieModel: MovieModel) {
        this.movieModel.value = movieModel
        this.review.value = null
        this.notes.value = listOf()
    }

    fun setEditMode() {
        isEditMode.value = true
    }

    fun clearEditMode() {
        isEditMode.value = false
    }

    fun getIsEditMode(): LiveData<Boolean> {
        return isEditMode
    }

    fun emitCommitChanges() {
        commitChanges.postValue(Unit)
    }

    fun emitDiscardChanges() {
        discardChanges.postValue(Unit)
    }

    fun onCommitChanges(): LiveData<Unit> {
        return commitChanges
    }

    fun onDiscardChanges(): LiveData<Unit> {
        return discardChanges
    }
}