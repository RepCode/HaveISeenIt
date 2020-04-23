package com.utn.haveiseenit.entities

import androidx.room.*

@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "myRating")
    val myRating: Int?,
    @ColumnInfo(name = "userId")
    val userId: Int,
    @ColumnInfo(name = "tmdbId")
    val tmdbId: Long,
    @ColumnInfo(name = "imageURL")
    val imageURL: String,
    @ColumnInfo(name = "year")
    val year: String,
    @ColumnInfo(name = "status")
    val status: String
)

class MovieAndReview(
    @Embedded
    val movie: Movie,
    @Relation(
        parentColumn = "id",
        entityColumn = "movieId"
    )
    val review: Review
)

data class MovieWithNotes(
    @Embedded val movie: Movie,
    @Relation(
        parentColumn = "id",
        entityColumn = "movieId"
    )
    val playlists: List<Note>
)