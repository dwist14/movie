package com.purwoko.movie.core.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "movie")
data class MovieEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    val movie_id : Int?,
    val title: String?,
    val overview: String?,
    val poster_path: String?,
    val vote_average: Double?
)