package com.utn.haveiseenit.activities.movies.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.utn.haveiseenit.entities.Movie

class MovieViewModel : ViewModel() {
    private val movies: MutableLiveData<List<Movie>> by lazy {
        MutableLiveData<List<Movie>>().also {
        }
    }

    fun getUsers(): LiveData<List<Movie>> {
        if(movies.value.isNullOrEmpty()){
            loadMovies()
        }
        return movies
    }

    private fun loadMovies() {

        movies.value = mutableListOf(Movie(1, 1, 1, "/oqhuGSl6gp22rIRo52IouPhV4hl.jpg", "Fight Club", "David Fincher", 8.9f, 1999 as Int, "PENDING"),
            Movie(1, 1, 1, "/cvvF42ALBMJMNFQvB5y9L9FTnI7.jpg", "Oldboy", "Chan-wook Park", 10f, 2003 as Int, "SEEN"),
            Movie(1, 1, 1, "/469mCKwBK7LUWoKoXjf1LaMnu9I.jpg", "American Psycho", "Mary Harron", 7.9f, 2000 as Int, "IN_REVIEW"),
            Movie(1, 1, 1, "/aJCpHDC6RoGz7d1Fzayl019xnxX.jpg", "Die Hard", "John McTiernan", 8.9f, 1988 as Int, "REVIEWED"),
            Movie(1, 1, 1, "/7ANTfqz1sWpa08y7D2dZnI88Hd2.jpg", "Se7en", "David Fincher", 9.5f, 1995 as Int, "STARTED"))
    }
}