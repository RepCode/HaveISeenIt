package com.utn.haveiseenit.activities.movies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Filter
import com.utn.haveiseenit.R
import com.utn.haveiseenit.services.APIService
import com.utn.haveiseenit.services.MoviesSearchResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MoviesActivity : AppCompatActivity() {
    lateinit var acTextView: AutoCompleteTextView
    var suggestions: List<String> = ArrayList()
    private lateinit var adapter: ArrayAdapter<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        adapter = ArrayAdapter<String>(this, R.layout.search_suggestion_item, suggestions)
        acTextView = findViewById(R.id.search_autocomplete)
        acTextView.threshold = 1
        acTextView.setAdapter(adapter)
        acTextView.setOnKeyListener{ view: View, i: Int, keyEvent: KeyEvent ->
            val char = acTextView.text.lastOrNull()
            if(char != null && char == ' '){
                searchByName(acTextView.text.toString())
            }
            false
        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/search/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    private fun searchByName(query: String) {
        GlobalScope.launch(Dispatchers.Default) {
            val call = getRetrofit().create(APIService::class.java).getMoviesByKeyword("movie?api_key=486d247609821da0b98bb27f87b76be3&query=\"$query\"").execute()
            val response = call.body() as MoviesSearchResponse
            launch(Dispatchers.Main) {
                suggestions = response.results.map { it.title }
                adapter.clear()
                adapter.addAll(suggestions)
                adapter.notifyDataSetChanged()
            }
        }
    }
}