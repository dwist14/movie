package com.purwoko.movie.core.api

import com.purwoko.movie.data.movie.MovieResponse
import com.purwoko.movie.data.movie.detail.MovieInfo
import com.purwoko.movie.data.movie.review.MovieReviewResponse
import com.purwoko.movie.data.movie.video.MovieVideoResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ServiceApi {

    @GET("3/movie/now_playing")
    suspend fun getMovie(
        @Query("language") language: String,
        @Query("page") page: Int,
    ): MovieResponse

    @GET("3/movie/{movie_id}")
    suspend fun getMovieId(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String,
    ): MovieInfo

    @GET("3/movie/{movie_id}/videos")
    suspend fun getVideoMovieId(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String,
    ): MovieVideoResponse

    @GET("3/movie/{movie_id}/reviews")
    suspend fun getReviewMovieId(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String,
        @Query("page") page: Int,
    ): MovieReviewResponse

}