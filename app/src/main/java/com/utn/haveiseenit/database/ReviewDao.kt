package com.utn.haveiseenit.database

import androidx.room.*
import com.utn.haveiseenit.entities.Review

@Dao
interface ReviewDao {

    @Query("SELECT * FROM reviews ORDER BY id")
    fun loadAllReviews(): MutableList<Review?>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertReview(review: Review?)

    @Update
    fun updateReview(review: Review?)

    @Delete
    fun delete(review: Review?)

    @Query("SELECT * FROM reviews WHERE id = :id")
    fun loadReviewById(id: Int): Review?

}