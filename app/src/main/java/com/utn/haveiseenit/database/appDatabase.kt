package com.utn.haveiseenit.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.utn.haveiseenit.entities.Movie
import com.utn.haveiseenit.entities.Note
import com.utn.haveiseenit.entities.Review
import com.utn.haveiseenit.entities.User

@Database(entities = [User::class, Movie::class, Review::class, Note::class], version = 2, exportSchema = false)
abstract class appDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun movieDao(): MovieDao
    abstract fun reviewDao(): ReviewDao
    abstract fun noteDao(): NoteDao

    companion object {
        var INSTANCE: appDatabase? = null

        fun getAppDataBase(context: Context): appDatabase? {
            if (INSTANCE == null) {
                synchronized(appDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        appDatabase::class.java,
                        "HaveISeenItDB"
                    ).allowMainThreadQueries().build()
                }
            }
            return INSTANCE
        }

        fun destroyDataBase(){
            INSTANCE = null
        }
    }
}