package com.utn.haveiseenit.activities.movies.layoutHelpers

import android.app.Activity
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import com.utn.haveiseenit.R
import com.utn.haveiseenit.entities.MovieStatuses

object MovieLayoutHelpers {
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

    fun getManualEditableMovieStatusesArray(context: Context): Array<StatusModel> {
        return arrayOf(
            StatusModel(
                context.getString(R.string.movie_status_to_watch),
                MovieStatuses.pending
            ),
            StatusModel(
                context.getString(R.string.movie_status_started),
                MovieStatuses.started
            ),
            StatusModel(
                context.getString(R.string.movie_status_seen),
                MovieStatuses.seen
            )
        )
    }

    private fun getAllMovieStatusesArray(context: Context): Array<StatusModel> {
        return arrayOf(
            StatusModel(
                context.getString(R.string.movie_status_to_watch),
                MovieStatuses.pending
            ),
            StatusModel(
                context.getString(R.string.movie_status_started),
                MovieStatuses.started
            ),
            StatusModel(
                context.getString(R.string.movie_status_seen),
                MovieStatuses.seen
            ),
            StatusModel(
                context.getString(R.string.movie_status_in_review),
                MovieStatuses.inReview
            ),
            StatusModel(
                context.getString(R.string.movie_status_reviewed),
                MovieStatuses.reviewed
            )
        )
    }

    fun getMovieStatusDisplayName(context: Context, statusId: Int): String {
        return getAllMovieStatusesArray(context).first { it.statusName == statusId }.displayName
    }

    fun requestKeyboardOpen(activity: Activity, view:View){
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }
}

data class StatusModel(val displayName: String, val statusName: Int)