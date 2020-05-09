package com.utn.haveiseenit.activities.movies.fragments

import android.os.Bundle
import android.text.InputFilter
import android.text.Spanned
import android.view.*
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.utn.haveiseenit.R
import com.utn.haveiseenit.activities.movies.layoutHelpers.MovieLayoutHelpers
import com.utn.haveiseenit.activities.movies.viewModels.MovieDetailViewModel
import com.utn.haveiseenit.activities.movies.viewModels.models.MovieModel
import com.utn.haveiseenit.entities.MovieStatuses

class MovieDetailFragment() : Fragment() {
    private lateinit var v: View
    private lateinit var statusTextView: TextView
    private lateinit var movieDetailViewModel: MovieDetailViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        movieDetailViewModel =
            ViewModelProvider(requireActivity()).get(MovieDetailViewModel::class.java)
        movieDetailViewModel.getMovie()
            .observe(requireActivity(), Observer<MovieModel> { movieModel ->
                v.findViewById<TextView>(R.id.detail_title_text).text = movieModel.movie.title
                v.findViewById<TextView>(R.id.detail_year_text).text =
                    movieModel.movie.year.toString()
                v.findViewById<TextView>(R.id.detail_director_text).text = movieModel.movie.director
                v.findViewById<TextView>(R.id.detail_duration_text).text =
                    movieModel.movie.durationMin.toString() + " min"
                v.findViewById<TextView>(R.id.detail_score_text).text =
                    if (movieModel.movie.rating == null) "-" else movieModel.movie.rating.toString()
                statusTextView = v.findViewById(R.id.detail_status_text)
                MovieLayoutHelpers.setStatus(movieModel.movie.status!!, statusTextView, v!!)
                movieModel.loadPoster(requireContext()) { drawable ->
                    v.findViewById<ImageView>(R.id.detail_poster_image).setImageDrawable(drawable)
                }
            })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        v = inflater.inflate(R.layout.fragment_movie_detail, container, false)
        // Set listeners
        v.findViewById<TextView>(R.id.detail_status_text).setOnClickListener {
            changeMovieState()
        }
        v.findViewById<ImageView>(R.id.edit_state).setOnClickListener {
            changeMovieState()
        }
        v.findViewById<ImageView>(R.id.edit_score).setOnClickListener {
            changeMovieScore()
        }
        return v
    }

    private fun changeMovieState() {
        val statuses = arrayOf(
            StatusModel(getString(R.string.movie_status_to_watch), MovieStatuses.pending),
            StatusModel(getString(R.string.movie_status_started), MovieStatuses.started),
            StatusModel(getString(R.string.movie_status_seen), MovieStatuses.seen),
            StatusModel(getString(R.string.movie_status_in_review), MovieStatuses.inReview)
        )
        val builder = AlertDialog.Builder(requireContext(), R.style.Dialog)
        var index: Int? = null
        builder.setTitle(getString(R.string.dialog_title))
        builder.setSingleChoiceItems((statuses.map { it.displayName }).toTypedArray(), -1) { _, i ->
            index = i
        }

        builder.setPositiveButton(getString(R.string.dialog_close)) { _, _ ->
            if (index != null) {
                movieDetailViewModel.changeMovieStatus(statuses[index!!].statusName)
                MovieLayoutHelpers.setStatus(statuses[index!!].statusName, statusTextView, v!!)
            }
        }
        builder.show()
    }

    private fun changeMovieScore() {
        val editText = v.findViewById<EditText>(R.id.detail_score_text)
        editText.setText("")
        editText.filters = arrayOf<InputFilter>(InputFilterMinMax(0.0F, 10.0F))
        editText.isEnabled = true
        editText.requestFocus()
    }

    data class StatusModel(val displayName: String, val statusName: String)

    class InputFilterMinMax(min: Float, max: Float) : InputFilter {
        private var min: Float = 0.0F
        private var max: Float = 0.0F

        init {
            this.min = min
            this.max = max
        }

        override fun filter(
            source: CharSequence,
            start: Int,
            end: Int,
            dest: Spanned,
            dstart: Int,
            dend: Int
        ): CharSequence? {
            try {
                val input = (dest.subSequence(0, dstart).toString() + source + dest.subSequence(
                    dend,
                    dest.length
                ))
                if (isInRange(min, max, input.toFloat()) && validDecimalPlace(input))
                    return null
            } catch (nfe: NumberFormatException) {
            }
            return ""
        }

        private fun isInRange(a: Float, b: Float, c: Float): Boolean {
            return if (b > a) c in a..b else c in b..a
        }

        private fun validDecimalPlace(a: String): Boolean {
            val decimalPlace = a.split('.').getOrNull(index = 1)
            return if (decimalPlace == null) true else decimalPlace.length < 2
        }
    }
}