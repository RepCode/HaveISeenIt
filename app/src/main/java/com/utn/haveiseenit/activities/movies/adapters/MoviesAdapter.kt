package com.utn.haveiseenit.activities.movies.adapters

import android.graphics.ColorFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.utn.haveiseenit.R
import com.utn.haveiseenit.entities.Movie
import com.utn.haveiseenit.entities.MovieStatuses

class MoviesAdapter(private val movies: List<Movie>) :
    RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

    class MoviesViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val posterImage: ImageView = view.findViewById(R.id.list_poster_image)

        fun updateWithUrl(url: String) {
            val picasso = Picasso.get()
            picasso.load("https://image.tmdb.org/t/p/w92$url")
                .placeholder(R.drawable.poster_unavailable)
                .error(R.drawable.poster_unavailable)
                .into(posterImage)
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MoviesAdapter.MoviesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_list_item, parent, false) as View

        return MoviesViewHolder(view)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val titleText =
            if (movies[position].title.length > 45) ("${movies[position].title.substring(
                0,
                35
            )}...") else movies[position].title
        holder.view.findViewById<TextView>(R.id.list_title_text).text =
            "$titleText (${movies[position].year})"
        holder.view.findViewById<TextView>(R.id.list_director_text).text =
            holder.view.context.getString(R.string.list_director, movies[position].director)
        holder.view.findViewById<TextView>(R.id.list_score_text).text =
            holder.view.context.getString(R.string.list_rating, movies[position].rating)
        setStatus(holder, movies[position].status)
        holder.updateWithUrl(movies[position].imageURL)
    }

    private fun setStatus(holder: MoviesViewHolder, state: String) {
        val textView = holder.view.findViewById<TextView>(R.id.list_status_text)
        val context = holder.view.context
        when (state) {
            MovieStatuses.pending -> {
                textView.text =
                    context.getString(R.string.movie_status_to_watch)
                textView.setBackgroundColor(context.getColor(R.color.yellowColor))
            }
            MovieStatuses.started -> {
                textView.text =
                    context.getString(R.string.movie_status_started)
                textView.setBackgroundColor(context.getColor(R.color.orangeColor))
            }
            MovieStatuses.seen -> {
                textView.text =
                    context.getString(R.string.movie_status_seen)
                textView.setBackgroundColor(context.getColor(R.color.greenColor))
            }
            MovieStatuses.inReview -> {
                textView.text =
                    context.getString(R.string.movie_status_in_review)
                textView.setBackgroundColor(context.getColor(R.color.chillRedColor))
            }
            MovieStatuses.reviewed -> {
                textView.text =
                    context.getString(R.string.movie_status_reviewed)
                textView.setBackgroundColor(context.getColor(R.color.blueColor))
            }
        }
        textView.text
    }

    override fun getItemCount() = movies.size
}