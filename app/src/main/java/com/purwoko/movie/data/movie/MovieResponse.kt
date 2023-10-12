package com.purwoko.movie.data.movie

data class MovieResponse(
    val results: List<Movie>,
    val page: Int,
    val total_pages: Int,
    val total_results: Int
)