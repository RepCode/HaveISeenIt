package com.utn.haveiseenit.database

import androidx.room.*
import com.utn.haveiseenit.entities.Note

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes ORDER BY id")
    fun loadAllMovies(): MutableList<Note?>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(note: Note?)

    @Update
    fun updateMovie(note: Note?)

    @Delete
    fun delete(note: Note?)

    @Query("SELECT * FROM notes WHERE id = :id")
    fun loadMovieById(id: Int): Note?

}