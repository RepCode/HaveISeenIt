package com.utn.haveiseenit.activities.movies.fragments

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.utn.haveiseenit.R
import com.utn.haveiseenit.activities.movies.ToolbarEvents
import com.utn.haveiseenit.activities.movies.adapters.MoviesAdapter
import com.utn.haveiseenit.activities.movies.viewModels.MovieListViewModel
import com.utn.haveiseenit.activities.movies.viewModels.models.MovieModel
import com.utn.haveiseenit.services.MovieResponse

class MoviesListFragment : Fragment() {
    private lateinit var v: View
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var toolbar: Menu? = null
    private lateinit var moviesViewModel: MovieListViewModel

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
        viewManager = LinearLayoutManager(context)
        viewAdapter = MoviesAdapter(requireActivity()) { movie ->
            navigateToMovieDetail(movie)
        }
        moviesViewModel =
            ViewModelProvider(requireActivity()).get(MovieListViewModel::class.java)
        moviesViewModel.hasMovies().observe(requireActivity(), Observer<Boolean> { hasMovies ->
            if (hasMovies) {
                v.findViewById<TextView>(R.id.empty_list_message).visibility = View.INVISIBLE
            }
        })
        moviesViewModel.hasSelectedMovies()
            .observe(requireActivity(), Observer<Boolean> { hasSelection ->
                onMovieSelectionChange(hasSelection)
            })
        setRecyclerView()

        (activity as ToolbarEvents).setSearchBarVisibility(true)

        (activity as ToolbarEvents).onSearchItemSelected = { movie: MovieResponse ->
            val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(v.windowToken, 0)
            v.findNavController().navigate(
                MoviesListFragmentDirections.actionMoviesListFragmentToNewMovieFragment(movie)
            )
        }

        v.findViewById<FloatingActionButton>(R.id.new_movie_button).setOnClickListener {
            (activity as ToolbarEvents).requestSearchBarFocus()
        }
        return v
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_toolbar, menu)
        toolbar = menu
        onMovieSelectionChange(false) // hide delete button
        toolbar!!.findItem(R.id.action_delete).setOnMenuItemClickListener {
            val builder = AlertDialog.Builder(requireContext(), R.style.Dialog)
            builder.setTitle(getString(R.string.delete_dialog_title))
            builder.setPositiveButton(getString(R.string.dialog_close)) { _, _ ->
                moviesViewModel.deleteMovies()
                Snackbar.make(
                    v,
                    getString(R.string.movie_deleted_message),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
            builder.setNegativeButton(getString(R.string.dialog_cancel)) { _, _ ->
            }
            builder.show()

            true
        }
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun navigateToMovieDetail(movieModel: MovieModel) {
        v.findNavController().navigate(
            MoviesListFragmentDirections.actionMoviesListFragmentToMovieDetailContainerFragment(
                movieModel
            )
        )
    }

    private fun setRecyclerView() {
        v.findViewById<RecyclerView>(R.id.notes_recycler_view).apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    private val onMovieSelectionChange = { hasSelection: Boolean ->
        toolbar?.findItem(R.id.action_delete)?.isVisible = hasSelection
        (activity as ToolbarEvents).setSearchBarVisibility(!hasSelection)
    }
}
