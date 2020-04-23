package com.utn.haveiseenit.database

import androidx.room.*
import com.utn.haveiseenit.entities.Review

@Dao
interface ReviewDao {

    @Query("SELECT * FROM reviews ORDER BY id")
    fun loadAllMovies(): MutableList<Review?>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(review: Review?)

    @Update
    fun updateMovie(review: Review?)

    @Delete
    fun delete(review: Review?)

    @Query("SELECT * FROM reviews WHERE id = :id")
    fun loadMovieById(id: Int): Review?

}