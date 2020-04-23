package com.utn.haveiseenit.database

import androidx.room.*
import com.utn.haveiseenit.entities.Movie
import com.utn.haveiseenit.entities.MovieAndReview
import com.utn.haveiseenit.entities.MovieWithNotes
import com.utn.haveiseenit.entities.User

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies ORDER BY id")
    fun loadAllMovies(): MutableList<Movie?>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: Movie?)

    @Update
    fun updateMovie(movie: Movie?)

    @Delete
    fun delete(movie: Movie?)

    @Query("SELECT * FROM movies WHERE id = :id")
    fun loadMovieById(id: Int): Movie?

    @Transaction
    @Query("SELECT * FROM movies")
    fun getMoviesAndLibraries(): List<MovieAndReview>

    @Transaction
    @Query("SELECT * FROM movies")
    fun getMoviesWithPlaylists(): List<MovieWithNotes>
}