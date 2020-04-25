package com.utn.haveiseenit.entities

import androidx.room.*

@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "userId")
    val userId: Int,
    @ColumnInfo(name = "tmdbId")
    val tmdbId: Long,
    @ColumnInfo(name = "imageURL")
    val imageURL: String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "director")
    val director: String,
    @ColumnInfo(name = "rating")
    val rating: Float,
    @ColumnInfo(name = "year")
    val year: Int,
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

object MovieStatuses{
    public val pending = "PENDING"
    public val started = "STARTED"
    public val seen = "SEEN"
    public val inReview = "IN_REVIEW"
    public val reviewed = "REVIEWED"
}