package com.utn.haveiseenit.activities.movies.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar

import com.utn.haveiseenit.R
import com.utn.haveiseenit.activities.movies.viewModels.NewMovieViewModel
import com.utn.haveiseenit.database.MovieDao
import com.utn.haveiseenit.database.appDatabase
import com.utn.haveiseenit.forms.Form
import com.utn.haveiseenit.services.UserAuthentication

class NewMovieFragment : Fragment() {
    private lateinit var movieDao: MovieDao
    private lateinit var v: View
    private lateinit var viewModel: NewMovieViewModel
    private lateinit var titleView: TextView
    private lateinit var yearView: TextView
    private lateinit var directorView: TextView
    private lateinit var durationView: TextView
    private lateinit var posterView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_new_movie, container, false)
        // hide search bar from toolbar
        activity?.findViewById<AutoCompleteTextView>(R.id.search_autocomplete)?.visibility =
            View.INVISIBLE
        // set navigation back
        val toolbar: Toolbar = (activity as AppCompatActivity).findViewById(R.id.toolbar)
        toolbar.setNavigationOnClickListener {
            v.findNavController().navigate(
                NewMovieFragmentDirections.actionNewMovieFragmentToMoviesListFragment()
            )
        }
        initViewElements()
        return v
    }

    override fun onStart() {
        super.onStart()
        movieDao = appDatabase.getAppDataBase(v.context)!!.movieDao()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(NewMovieViewModel::class.java)
        val movie = NewMovieFragmentArgs.fromBundle(requireArguments()).movie
        viewModel.setMovieData(movie)
        titleView.text = viewModel.getTitle()
        yearView.text = viewModel.getYearText()
        viewModel.loadPoster(requireContext()) { image ->
            posterView.setImageDrawable(image)
        }
        viewModel.getCredits { director ->
            directorView.text = director
        }
        viewModel.getDetails { duration ->
            durationView.text = duration.toString()
        }
        v.findViewById<Button>(R.id.add_movie_button).setOnClickListener {
            movieDao.insertMovie(viewModel.getDBMovie())
            Snackbar.make(
                v,
                getString(R.string.movie_added_message),
                Snackbar.LENGTH_SHORT
            ).show()
            v.findNavController().navigate(
                NewMovieFragmentDirections.actionNewMovieFragmentToMoviesListFragment()
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.empty_toolbar, menu)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun initViewElements() {
        titleView = v.findViewById(R.id.detail_title_text)
        yearView = v.findViewById(R.id.detail_year_text)
        directorView = v.findViewById(R.id.detail_director_text)
        durationView = v.findViewById(R.id.detail_duration_text)
        posterView = v.findViewById(R.id.detail_poster_image)
    }
}
