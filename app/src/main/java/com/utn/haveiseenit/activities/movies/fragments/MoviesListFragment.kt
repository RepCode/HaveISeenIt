package com.utn.haveiseenit.activities.movies.fragments

import android.os.Bundle
import android.view.*
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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

class MoviesListFragment : Fragment() {
    private lateinit var v: View
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        v = inflater.inflate(R.layout.fragment_movies_list, container, false)
        val movieViewModel: MovieViewModel by viewModels()
        movieViewModel.getUsers().observe(requireActivity(), Observer<List<MovieView>> { movies ->
            viewManager = LinearLayoutManager(context)
            viewAdapter = MoviesAdapter(movies) { position->
                v.findNavController().navigate(
                    MoviesListFragmentDirections.actionMoviesListFragmentToMovieDetailFragment(position)
                )
            }
            if (movies.isNotEmpty()) {
                v.findViewById<TextView>(R.id.empty_list_message).visibility = View.INVISIBLE
            }
            recyclerView = v.findViewById<RecyclerView>(R.id.movies_recycler_view).apply {
                layoutManager = viewManager
                adapter = viewAdapter
            }
        })
        activity?.findViewById<AutoCompleteTextView>(R.id.search_autocomplete)?.visibility =
            View.VISIBLE
        return v
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.empty_toolbar, menu)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        super.onCreateOptionsMenu(menu, inflater)
    }
}
