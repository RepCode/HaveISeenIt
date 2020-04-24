package com.utn.haveiseenit.activities.movies.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.utn.haveiseenit.R

class MoviesListFragment : Fragment() {
    private lateinit var v: View
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
        return v
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.empty_toolbar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}
