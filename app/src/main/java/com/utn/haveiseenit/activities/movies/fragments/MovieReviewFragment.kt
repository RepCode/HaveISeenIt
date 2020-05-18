package com.utn.haveiseenit.activities.movies.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText

import com.utn.haveiseenit.R
import com.utn.haveiseenit.activities.movies.viewModels.MovieDetailViewModel
import com.utn.haveiseenit.activities.movies.viewModels.models.MovieModel
import com.utn.haveiseenit.entities.MovieStatuses
import com.utn.haveiseenit.entities.Review

class MovieReviewFragment : Fragment() {
    private lateinit var v: View
    private lateinit var movieDetailViewModel: MovieDetailViewModel
    private var review: Review? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        movieDetailViewModel =
            ViewModelProvider(requireActivity()).get(MovieDetailViewModel::class.java)
        movieDetailViewModel.getMovieReview()
            .observe(requireActivity(), Observer { review ->
                this.review = review
                v.findViewById<TextInputEditText>(R.id.review_text).setText(this.review?.text)
            })
        movieDetailViewModel.getMovie()
            .observe(requireActivity(), Observer<MovieModel> { movieModel ->
                v.findViewById<Switch>(R.id.review_finished_switch).isChecked =
                    movieModel.movie.status == MovieStatuses.reviewed
                movieDetailViewModel.onCommitChanges().observe(requireActivity(), Observer<Unit>
                {
                    if (movieDetailViewModel.getIsEditMode().value!! &&
                        !v.findViewById<TextInputEditText>(R.id.review_text).text.toString().isNullOrEmpty()
                    ) {
                        if (review == null) {
                            review = Review(
                                0,
                                movieModel.movie.id,
                                v.findViewById<TextInputEditText>(R.id.review_text).text.toString()
                            )
                            movieDetailViewModel.addReview(review!!)
                        } else {
                            review!!.text =
                                v.findViewById<TextInputEditText>(R.id.review_text).text.toString()
                            movieDetailViewModel.updateReview(review!!)
                        }
                        movieDetailViewModel.clearEditMode()
                    }
                })
                movieDetailViewModel.onDiscardChanges().observe(requireActivity(), Observer<Unit>
                {
                    v.findViewById<TextInputEditText>(R.id.review_text).setText(this.review?.text)
                    movieDetailViewModel.clearEditMode()
                })
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_movie_review, container, false)
        v.findViewById<Switch>(R.id.review_finished_switch).setOnClickListener {
            if (v.findViewById<Switch>(R.id.review_finished_switch).isChecked) {
                movieDetailViewModel.changeMovieStatus(MovieStatuses.reviewed)
            } else {
                movieDetailViewModel.changeMovieStatus(MovieStatuses.inReview)
            }
        }
        v.findViewById<TextInputEditText>(R.id.review_text).addTextChangedListener {
            val reviewText = v.findViewById<TextInputEditText>(R.id.review_text)
            val text = reviewText?.text?.toString()
            if (reviewText.isCursorVisible) {
                if ((review == null && !text.isNullOrEmpty()) || (review != null && reviewText.text.toString() != review!!.text)) {
                    movieDetailViewModel.setEditMode()
                }
            }
        }
        return v
    }
}
