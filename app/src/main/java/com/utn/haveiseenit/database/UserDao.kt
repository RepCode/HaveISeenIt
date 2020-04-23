package com.utn.haveiseenit.database

import androidx.room.*
import com.utn.haveiseenit.entities.User
import com.utn.haveiseenit.entities.UserWithMovies

@Dao
public interface UserDao {

    @Query("SELECT * FROM users ORDER BY id")
    fun loadAllPersons(): MutableList<User?>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPerson(user: User?)

    @Update
    fun updatePerson(user: User?)

    @Delete
    fun delete(user: User?)

    @Query("SELECT * FROM users WHERE id = :id")
    fun loadPersonById(id: Int): User?

    @Query("SELECT * FROM users WHERE email = :email")
    fun loadPersonByEmail(email: String): User?

    @Transaction
    @Query("SELECT * FROM users")
    fun getUsersWithMovies(): List<UserWithMovies>
}