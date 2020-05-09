package com.utn.haveiseenit.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Note(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "movieId")
    val movieId: Int,
    @ColumnInfo(name = "text")
    val text: String,
    @ColumnInfo(name = "tag")
    val type: Int
)

object NoteTag{
    const val general = 0
    const val photography = 1
    const val plot = 2
    const val scene = 3
    const val actor = 4
}