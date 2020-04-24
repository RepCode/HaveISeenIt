package com.utn.haveiseenit.activities.movies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.utn.haveiseenit.R

class MoviesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
//        supportActionBar?.setDisplayShowTitleEnabled(false);
    }
}