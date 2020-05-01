package com.utn.haveiseenit.activities.movies.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

import com.utn.haveiseenit.R

class MovieDetailContainerFragment : Fragment() {
    private lateinit var v: View
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private var movieIndex: Int? = null

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

    private fun createCardAdapter(): ViewPagerAdapter? {
        return ViewPagerAdapter(movieIndex!!, requireActivity())
    }

    class ViewPagerAdapter(private val movieIndex: Int, fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {
        override fun createFragment(position: Int): Fragment {

            return when(position){
                0 -> MovieDetailFragment(movieIndex)
                1 -> MovieDetailFragment(movieIndex)
                2 -> MovieDetailFragment(movieIndex)

                else -> MovieDetailFragment(movieIndex)
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

        movieIndex = MovieDetailContainerFragmentArgs.fromBundle(requireArguments()).position

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
}
