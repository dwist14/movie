package com.purwoko.movie.view.detailMovie.di

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.purwoko.movie.common.ViewState
import com.purwoko.movie.common.setError
import com.purwoko.movie.common.setLoading
import com.purwoko.movie.common.setSuccess
import com.purwoko.movie.core.base.BaseViewModel
import com.purwoko.movie.core.di.DBRepository
import com.purwoko.movie.core.di.NetworkRepository
import com.purwoko.movie.data.movie.Movie
import com.purwoko.movie.data.movie.detail.MovieInfo
import com.purwoko.movie.data.movie.review.MovieReviewResponse
import com.purwoko.movie.data.movie.video.MovieVideoResponse
import com.purwoko.movie.utils.Constants.COUNTRY_CODE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val dbRepository: DBRepository
) :
    BaseViewModel() {

    val movieInfoLiveData = MutableLiveData<ViewState<MovieInfo>>()
    val movieVideoLiveData = MutableLiveData<ViewState<MovieVideoResponse>>()
    val movieReviewLiveData = MutableLiveData<ViewState<MovieReviewResponse>>()

    fun getMovieId(movieId: Int) {
        movieInfoLiveData.setLoading()
        launchOnUi(
            onRequest = {
                val result = networkRepository.getMovieId(movieId, COUNTRY_CODE)
                movieInfoLiveData.setSuccess(result)
            },
            onError = {
                movieInfoLiveData.setError(it)
            }
        )
    }

    fun getVideoMovieId(movieId: Int) {
        movieVideoLiveData.setLoading()
        launchOnUi(
            onRequest = {
                val result = networkRepository.getVideoMovieId(movieId, COUNTRY_CODE)
                movieVideoLiveData.setSuccess(result)
            },
            onError = {
                movieVideoLiveData.setError(it)
            }
        )
    }

    fun getReviewMovieId(movieId: Int, page: Int) {
        movieReviewLiveData.setLoading()
        launchOnUi(
            onRequest = {
                val result = networkRepository.getReviewMovieId(movieId, COUNTRY_CODE, page)
                movieReviewLiveData.setSuccess(result)
            },
            onError = {
                movieReviewLiveData.setError(it)
            }
        )
    }

    fun insertMovie(movie: Movie) {
        viewModelScope.launch {
            dbRepository.insertMovie(movie)
        }
    }

    fun getCountMovie(movieId: Int) = dbRepository.getCountMovie(movieId)

    fun deleteMovie(movieId: Int) {
        viewModelScope.launch {
            dbRepository.deleteMovie(movieId)
        }
    }
}