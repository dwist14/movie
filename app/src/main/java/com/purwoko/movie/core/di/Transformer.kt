package com.purwoko.movie.core.di

import com.purwoko.movie.data.movie.Movie
import com.purwoko.movie.core.db.entity.MovieEntity

/*
* This is a transformer class
* We cannot just use our model classes for inserting or getting data from db
* There might be a time when variables might be added or removed in model classes
* So it is always a better practice to have transformer classes
* */
object Transformer {

    fun convertMovieModelToMovieEntity(movie: Movie): MovieEntity {
        return MovieEntity(
            title = movie.title,
            movie_id = movie.id,
            overview = movie.overview,
            poster_path = movie.poster_path,
            vote_average = movie.vote_average
        )
    }

}