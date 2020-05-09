package com.utn.haveiseenit.database

import androidx.room.*
import com.utn.haveiseenit.entities.Note

@Dao
interface NoteDao {

    @Query("SELECT * FROM notes WHERE movieId= :movieId")
    fun loadMovieNotes(movieId:Int): MutableList<Note>

    @Query("SELECT * FROM notes ORDER BY id")
    fun loadAllNotes(): MutableList<Note?>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(note: Note?)

    @Update
    fun updateNote(note: Note?)

    @Delete
    fun delete(note: Note?)

    @Query("SELECT * FROM notes WHERE id = :id")
    fun loadNoteById(id: Int): Note?

}