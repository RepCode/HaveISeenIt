package com.utn.haveiseenit.entities

import androidx.room.*

@Entity(tableName = "users")
class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "userName")
    val userName: String,
    @ColumnInfo(name = "email")
    val email: String,
    @ColumnInfo(name = "passHash")
    val passHash: String
)

class UserWithMovies() {
    @Embedded
    lateinit var user: User

    @Relation(
        parentColumn = "id",
        entityColumn = "userId"
    )
    lateinit var movies: List<Movie>
}
