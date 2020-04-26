package com.utn.haveiseenit.activities.movies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.utn.haveiseenit.R
import com.utn.haveiseenit.activities.movies.viewModels.MovieViewModel
import com.utn.haveiseenit.entities.Movie

class MoviesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        val adapter = ArrayAdapter<String>(this, R.layout.search_suggestion_item, arrayOf("peli 1", "peli 2", "peli3"))
        val acTextView = findViewById<AutoCompleteTextView>(R.id.search_autocomplete)
        acTextView.threshold = 1
        acTextView.setAdapter(adapter)
    }
}