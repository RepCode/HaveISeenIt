package com.utn.haveiseenit.activities.movies.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.AutoCompleteTextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

import com.utn.haveiseenit.R
import com.utn.haveiseenit.activities.movies.viewModels.MovieDetailViewModel

class MovieDetailContainerFragment : Fragment() {
    private lateinit var v: View
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var movieDetailViewModel: MovieDetailViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        movieDetailViewModel =
            ViewModelProvider(requireActivity()).get(MovieDetailViewModel::class.java)
        movieDetailViewModel.setMovie(MovieDetailContainerFragmentArgs.fromBundle(requireArguments()).movie)
    }

    override fun onStart() {
        super.onStart()

        viewPager.adapter = createCardAdapter()
        TabLayoutMediator(tabLayout, viewPager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                when (position) {
                    0 -> tab.text = getString(R.string.tab_info)
                    1 -> tab.text = getString(R.string.tab_review)
                    2 -> tab.text = getString(R.string.tab_notes)
                    else -> tab.text = "undefined"
                }
            }).attach()

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.edit_toolbar, menu)
        movieDetailViewModel.getIsEditMode().observe(requireActivity(), Observer<Boolean> { isEditMode ->
            menu.findItem(R.id.action_confirm)?.isVisible = isEditMode
            menu.findItem(R.id.action_cancel)?.isVisible = isEditMode
            (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(!isEditMode)
        })
        menu.findItem(R.id.action_confirm).setOnMenuItemClickListener {
            movieDetailViewModel.emitCommitChanges()
            true
        }
        menu.findItem(R.id.action_cancel).setOnMenuItemClickListener {
            movieDetailViewModel.emitDiscardChanges()
            true
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun createCardAdapter(): ViewPagerAdapter? {
        return ViewPagerAdapter(requireActivity())
    }

    class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {
        override fun createFragment(position: Int): Fragment {

            return when (position) {
                0 -> MovieDetailFragment()
                1 -> MovieReviewFragment()
                2 -> MovieNotesFragment()

                else -> MovieDetailFragment()
            }
        }

        override fun getItemCount(): Int {
            return TAB_COUNT
        }

        companion object {
            private const val TAB_COUNT = 3
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_movie_detail_container, container, false)
        tabLayout = v.findViewById(R.id.tab_layout)
        viewPager = v.findViewById(R.id.view_pager)

        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // hide search bar from toolbar
        activity?.findViewById<AutoCompleteTextView>(R.id.search_autocomplete)?.visibility =
            View.INVISIBLE
        // set navigation back
        val toolbar: Toolbar = (activity as AppCompatActivity).findViewById(R.id.toolbar)
        toolbar.setNavigationOnClickListener {
            v.findNavController().navigate(
                MovieDetailContainerFragmentDirections.actionMovieDetailContainerFragmentToMoviesListFragment()
            )
        }

        return v
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                v.findNavController().navigate(
                    MovieDetailContainerFragmentDirections.actionMovieDetailContainerFragmentToMoviesListFragment()
                )
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callback)
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onDestroyView() {
        movieDetailViewModel.clearEditMode()
        movieDetailViewModel.getMovieReview().removeObservers(requireActivity())
        super.onDestroyView()
    }
}
