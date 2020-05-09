package com.utn.haveiseenit.entities

import androidx.room.*
import java.io.Serializable

@Entity(tableName = "movies")
data  class Movie(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "userId")
    val userId: Int,
    @ColumnInfo(name = "tmdbId")
    val tmdbId: Long,
    @ColumnInfo(name = "imageURL")
    val imageURL: String?,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "director")
    val director: String,
    @ColumnInfo(name = "rating")
    val rating: Float?,
    @ColumnInfo(name = "year")
    val year: Int,
    @ColumnInfo(name = "durationMin")
    val durationMin: Int,
    @ColumnInfo(name = "status")
    var status: Int
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
    const val pending = 0
    const val started = 1
    const val seen = 2
    const val inReview = 3
    const val reviewed = 4
}