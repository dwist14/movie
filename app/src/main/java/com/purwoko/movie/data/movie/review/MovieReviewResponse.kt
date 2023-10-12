package com.purwoko.movie.data.movie.review

data class MovieReviewResponse(
    val page: Int?,
    val results: List<MovieReview>,
    val total_pages: Int?
)