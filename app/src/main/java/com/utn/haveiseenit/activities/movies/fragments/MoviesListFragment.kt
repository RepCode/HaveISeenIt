package com.utn.haveiseenit.activities.movies.fragments

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.utn.haveiseenit.R
import com.utn.haveiseenit.activities.movies.adapters.MoviesAdapter

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

        viewManager = LinearLayoutManager(context)
        val list = arrayOf("qwe", "qwe", "qwe", "qwe", "qwe")
        viewAdapter = MoviesAdapter(list)
        if (list.isNotEmpty()) {
            v.findViewById<TextView>(R.id.empty_list_message).visibility = View.INVISIBLE
        }
        recyclerView = v.findViewById<RecyclerView>(R.id.movies_recycler_view).apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }
        return v
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.empty_toolbar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}
