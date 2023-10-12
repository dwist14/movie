package com.purwoko.movie.core.di

import com.purwoko.movie.core.api.ServiceApi
import com.purwoko.movie.data.movie.MovieResponse
import com.purwoko.movie.data.movie.detail.MovieInfo
import com.purwoko.movie.data.movie.review.MovieReviewResponse
import com.purwoko.movie.data.movie.video.MovieVideoResponse
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    private val serviceApi: ServiceApi
) {

    suspend fun getMovie(language: String, page: Int): MovieResponse {
        return serviceApi.getMovie(language, page)
    }

    suspend fun getMovieId(movieId: Int, language: String): MovieInfo {
        return serviceApi.getMovieId(movieId, language)
    }

    suspend fun getVideoMovieId(movieId: Int, language: String): MovieVideoResponse {
        return serviceApi.getVideoMovieId(movieId, language)
    }

    suspend fun getReviewMovieId(movieId: Int, language: String, page: Int): MovieReviewResponse {
        return serviceApi.getReviewMovieId(movieId, language, page)
    }

}