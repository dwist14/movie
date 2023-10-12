package com.purwoko.movie.core.di

import androidx.lifecycle.LiveData
import com.purwoko.movie.data.movie.Movie
import com.purwoko.movie.core.db.AppDatabase
import com.purwoko.movie.core.db.entity.MovieEntity
import com.purwoko.movie.core.di.Transformer.convertMovieModelToMovieEntity
import javax.inject.Inject

class DBRepository @Inject constructor(private val appDatabase: AppDatabase) {

    suspend fun insertMovie(movie: Movie): Long {
        return appDatabase.movieDao()
            .insert(convertMovieModelToMovieEntity(movie))
    }

    fun getAllMovies(): LiveData<List<MovieEntity>> {
        return appDatabase.movieDao().getAllMovies()
    }

    fun deleteMovie(movieId: Int) {
        appDatabase.movieDao().deleteMovieById(movieId)
    }

    fun getCountMovie(movieId: Int):  LiveData<Int> {
        return appDatabase.movieDao().getCount(movieId)
    }
}