package com.purwoko.movie.core.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.purwoko.movie.core.db.entity.MovieEntity

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movieEntity: MovieEntity):Long

    @Query("SELECT * FROM movie")
    fun getAllMovies():LiveData<List<MovieEntity>>

    @Delete
    suspend fun delete(movieEntity: MovieEntity)

    @Query("DELETE FROM movie WHERE movie_id IN (:movieId)")
    fun deleteMovieById(movieId: Int)

    @Query("SELECT COUNT(*) FROM movie WHERE movie_id = :movieId")
    fun getCount(movieId: Int): LiveData<Int>
}