package com.utn.haveiseenit.activities.movies

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.utn.haveiseenit.R
import com.utn.haveiseenit.activities.movies.fragments.MoviesListFragment
import com.utn.haveiseenit.activities.movies.fragments.MoviesListFragmentDirections
import com.utn.haveiseenit.services.APIService
import com.utn.haveiseenit.services.MovieResponse
import com.utn.haveiseenit.services.MoviesSearchResponse
import com.utn.haveiseenit.services.getRetrofit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MoviesActivity : AppCompatActivity(), ToolbarEvents {
    lateinit var acTextView: AutoCompleteTextView
    var movies: List<MovieResponse> = ArrayList()
    private lateinit var adapter: ArrayAdapter<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        adapter = ArrayAdapter(this, R.layout.search_suggestion_item, listOf())
        acTextView = findViewById(R.id.search_autocomplete)
        acTextView.threshold = 1
        acTextView.setAdapter(adapter)
        acTextView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val char = acTextView.text.lastOrNull()
                if (char != null && char == ' ') {
                    searchByName(acTextView.text.toString())
                }
                false
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
        acTextView.setOnItemClickListener { adapterView: AdapterView<*>, _: View, i: Int, _: Long ->
            val selected = adapterView.getItemAtPosition(i) as String
            val movie = movies.first { it.title == selected }

            onSearchItemSelected(movie)
        }
    }

    override var onSearchItemSelected = { _: MovieResponse -> Unit }

    override fun requestSearchBarFocus(){
        acTextView.requestFocus()
        acTextView.showDropDown()
    }

    override fun setSearchBarVisibility(isVisible: Boolean){
        if(isVisible){
            acTextView.visibility = View.VISIBLE
        } else {
            acTextView.visibility = View.INVISIBLE
        }
    }

    private fun searchByName(query: String) {
        GlobalScope.launch(Dispatchers.Default) {
            val call = getRetrofit().create(APIService::class.java)
                .getMoviesByKeyword("search/movie?api_key=486d247609821da0b98bb27f87b76be3&query=\"$query\"")
                .execute()
            val response = call.body() as MoviesSearchResponse
            launch(Dispatchers.Main) {
                movies = response.results
                adapter.clear()
                adapter.addAll(movies.map { it.title })
                adapter.notifyDataSetChanged()
            }
        }
    }
}

interface ToolbarEvents {
    var onSearchItemSelected: (MovieResponse) -> Unit
    fun requestSearchBarFocus()
    fun setSearchBarVisibility(isVisible: Boolean)
}