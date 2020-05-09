package com.utn.haveiseenit.activities.movies.layoutHelpers

import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.TextView
import com.utn.haveiseenit.R
import com.utn.haveiseenit.entities.MovieStatuses

object MovieLayoutHelpers{
    fun setStatus(status: Int, textView: TextView, v: View) {
        when (status) {
            MovieStatuses.pending -> {
                textView.text =
                    v.context.getString(R.string.movie_status_to_watch)
                val drawable = textView.background as GradientDrawable
                drawable.setColor(v.context.getColor(R.color.yellowColor));
            }
            MovieStatuses.started -> {
                textView.text =
                    v.context.getString(R.string.movie_status_started)
                val drawable = textView.background as GradientDrawable
                drawable.setColor(v.context.getColor(R.color.orangeColor));
            }
            MovieStatuses.seen -> {
                textView.text =
                    v.context.getString(R.string.movie_status_seen)
                val drawable = textView.background as GradientDrawable
                drawable.setColor(v.context.getColor(R.color.greenColor));
            }
            MovieStatuses.inReview -> {
                textView.text =
                    v.context.getString(R.string.movie_status_in_review)
                val drawable = textView.background as GradientDrawable
                drawable.setColor(v.context.getColor(R.color.chillRedColor));
            }
            MovieStatuses.reviewed -> {
                textView.text =
                    v.context.getString(R.string.movie_status_reviewed)
                val drawable = textView.background as GradientDrawable
                drawable.setColor(v.context.getColor(R.color.blueColor));
            }
        }
    }
}