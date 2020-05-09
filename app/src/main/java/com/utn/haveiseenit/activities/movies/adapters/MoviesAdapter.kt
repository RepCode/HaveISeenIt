package com.utn.haveiseenit.activities.movies.adapters

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.utn.haveiseenit.R
import com.utn.haveiseenit.activities.movies.layoutHelpers.MovieLayoutHelpers
import com.utn.haveiseenit.activities.movies.viewModels.models.MovieModel
import com.utn.haveiseenit.entities.MovieStatuses

class MoviesAdapter(private val movies: List<MovieModel>, val adapterOnClick: (MovieModel) -> Unit) :
    RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder>() {

    class MoviesViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MoviesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_list_item, parent, false) as View

        return MoviesViewHolder(view)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        movies[position].loadPoster(holder.view.context) { drawable ->
            holder.view.findViewById<ImageView>(R.id.list_poster_image).setImageDrawable(drawable)
        }
        val titleText =
            if (movies[position].movie.title.length > 45) ("${movies[position].movie.title.substring(
                0,
                35
            )}...") else movies[position].movie.title
        holder.view.findViewById<TextView>(R.id.list_title_text).text =
            "$titleText (${movies[position].movie.year})"
        holder.view.findViewById<TextView>(R.id.list_director_text).text =
            holder.view.context.getString(R.string.list_director, movies[position].movie.director)
        val rating = movies[position].movie.rating
        if(rating == null){
            holder.view.findViewById<TextView>(R.id.list_score_text).visibility = View.INVISIBLE
        } else{
            holder.view.findViewById<TextView>(R.id.list_score_text).text =
                holder.view.context.getString(R.string.list_rating, rating)
        }
        setStatus(holder, movies[position].movie.status)
        holder.view.findViewById<CardView>(R.id.movie_card).setOnClickListener {
            adapterOnClick(movies[position])
        }
    }

    private fun setStatus(holder: MoviesViewHolder, state: Int) {
        val textView = holder.view.findViewById<TextView>(R.id.list_status_text)
        MovieLayoutHelpers.setStatus(state, textView, holder.view)
    }

    override fun getItemCount() = movies.size
}