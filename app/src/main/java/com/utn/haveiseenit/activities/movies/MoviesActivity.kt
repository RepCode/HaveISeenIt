package com.utn.haveiseenit.activities.movies

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.utn.haveiseenit.R
import com.utn.haveiseenit.activities.movies.fragments.MoviesListFragment
import com.utn.haveiseenit.activities.movies.fragments.MoviesListFragmentDirections
import com.utn.haveiseenit.activities.movies.layoutHelpers.MovieLayoutHelpers
import com.utn.haveiseenit.activities.movies.viewModels.MovieListViewModel
import com.utn.haveiseenit.activities.movies.viewModels.models.MovieModel
import com.utn.haveiseenit.services.APIService
import com.utn.haveiseenit.services.MovieResponse
import com.utn.haveiseenit.services.MoviesSearchResponse
import com.utn.haveiseenit.services.getRetrofit
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.Console
import kotlin.math.log


class MoviesActivity : AppCompatActivity(), ToolbarEvents {
    lateinit var acTextView: AutoCompleteTextView
    private var apiMovies: List<MovieResponse> = ArrayList()
    private var dbMovieIds: List<Long> = ArrayList()
    private lateinit var adapter: ArrayAdapter<String>
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)
        val moviesViewModel =
            ViewModelProvider(this).get(MovieListViewModel::class.java)
        moviesViewModel.getMovies().observe(this, Observer<List<MovieModel>> { moviesList ->
            dbMovieIds = moviesList.map { it.movie.tmdbId }
        })
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        adapter = ArrayAdapter(this, R.layout.search_suggestion_item, listOf())
        acTextView = findViewById(R.id.search_autocomplete)
        acTextView.threshold = 1
        acTextView.setAdapter(adapter)
        acTextView.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (acTextView.text.isNullOrEmpty()) {
                    acTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        null,
                        null,
                        getDrawable(R.drawable.ic_search_grey_24dp),
                        null
                    )
                } else {
                    acTextView.setCompoundDrawablesRelativeWithIntrinsicBounds(
                        null,
                        null,
                        getDrawable(R.drawable.ic_clear_grey_24dp),
                        null
                    )
                }
                val char = acTextView.text.lastOrNull()
                if (char != null && char == ' ') {
                    searchByName(acTextView.text.toString())
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })
        acTextView.setOnItemClickListener { adapterView: AdapterView<*>, _: View, i: Int, _: Long ->
            val selected = adapterView.getItemAtPosition(i) as String
            val movie = apiMovies.first { it.title == selected }

            onSearchItemSelected(movie)
        }
        acTextView.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                if (event?.rawX!! >= (acTextView.right - acTextView.compoundDrawablesRelative[2].bounds.width())) {
                    acTextView.setText("")
                    acTextView.requestFocus()
                    return true;
                }

                return v?.onTouchEvent(event) ?: true
            }
        })
    }

    override var onSearchItemSelected = { _: MovieResponse -> Unit }

    override fun requestSearchBarFocus() {
        acTextView.setText("")
        acTextView.requestFocus()
        acTextView.showDropDown()
        MovieLayoutHelpers.requestKeyboardOpen(this, acTextView)
    }

    override fun setSearchBarVisibility(isVisible: Boolean) {
        if (isVisible) {
            acTextView.visibility = View.VISIBLE
            acTextView.setText("")
        } else {
            acTextView.visibility = View.INVISIBLE
        }
    }

    private fun searchByName(query: String) {

        GlobalScope.launch(Dispatchers.Default) {
            try {
                val call = getRetrofit().create(APIService::class.java)
                    .getMoviesByKeyword("search/movie?api_key=486d247609821da0b98bb27f87b76be3&query=\"$query\"")
                    .execute()
                val response = call.body() as MoviesSearchResponse
                launch(Dispatchers.Main) {
                    apiMovies = response.results
                    adapter.clear()
                    adapter.addAll(apiMovies.filter { !dbMovieIds.contains(it.tmdbId) }.map { it.title })
                    adapter.notifyDataSetChanged()
                }
            } catch (ex: Throwable) {
                val asd = ex.message
            }
        }
    }
}

interface ToolbarEvents {
    var onSearchItemSelected: (MovieResponse) -> Unit
    fun requestSearchBarFocus()
    fun setSearchBarVisibility(isVisible: Boolean)
}