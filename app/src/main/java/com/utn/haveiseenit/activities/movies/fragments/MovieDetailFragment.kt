package com.utn.haveiseenit.activities.movies.fragments

import android.os.Bundle
import android.view.*
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.utn.haveiseenit.R
import com.utn.haveiseenit.activities.movies.adapters.MoviesAdapter
import com.utn.haveiseenit.activities.movies.viewModels.MovieView
import com.utn.haveiseenit.activities.movies.viewModels.MovieViewModel
import com.utn.haveiseenit.entities.Movie
import kotlin.properties.Delegates

class MovieDetailFragment : Fragment() {
    private lateinit var v: View
    private lateinit var movieView: MovieView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val position = MovieDetailFragmentArgs.fromBundle(requireArguments()).position
        v = inflater.inflate(R.layout.fragment_movie_detail, container, false)
        val movieViewModel: MovieViewModel by viewModels()
        movieViewModel.getUsers().observe(requireActivity(), Observer<List<MovieView>> { movies ->
            movieView = movies[position]
            v.findViewById<TextView>(R.id.detail_title_text).text = movieView.movie.title
            v.findViewById<TextView>(R.id.detail_year_text).text = movieView.movie.year.toString()
            v.findViewById<TextView>(R.id.detail_director_text).text = movieView.movie.director
            v.findViewById<TextView>(R.id.detail_duration_text).text = movieView.movie.durationMin.toString() + " min"
            movieView.loadPoster(v.context) { drawable ->
                v.findViewById<ImageView>(R.id.detail_poster_image).setImageDrawable(drawable)
            }
        })
        // hide search bar from toolbar
        activity?.findViewById<AutoCompleteTextView>(R.id.search_autocomplete)?.visibility =
            View.INVISIBLE
        // set navigation back
        val toolbar: Toolbar = (activity as AppCompatActivity).findViewById(R.id.toolbar)
        toolbar.setNavigationOnClickListener {
            v.findNavController().navigate(
                MovieDetailFragmentDirections.actionMovieDetailFragmentToMoviesListFragment()
            )
        }
        return v
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.empty_toolbar, menu)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        super.onCreateOptionsMenu(menu, inflater)
    }
}
