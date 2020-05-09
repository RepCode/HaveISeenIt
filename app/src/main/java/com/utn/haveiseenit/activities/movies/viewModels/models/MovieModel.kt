package com.utn.haveiseenit.activities.movies.viewModels.models

import android.content.Context
import android.graphics.drawable.Drawable
import coil.Coil
import coil.request.LoadRequest
import com.utn.haveiseenit.entities.Movie
import java.io.Serializable

class MovieModel(val movie: Movie):Serializable {
    private var posterDrawable: Drawable? = null

    fun loadPoster(context: Context, onPosterLoaded: (drawable: Drawable) -> Unit) {
        if(posterDrawable != null){
            return onPosterLoaded(posterDrawable!!)
        }
        val request = LoadRequest.Builder(context)
            .data("https://image.tmdb.org/t/p/w92${movie.imageURL}")
            .target { drawable ->
                posterDrawable = drawable
                onPosterLoaded(drawable)
            }
            .build()
        Coil.execute(request)
    }
}