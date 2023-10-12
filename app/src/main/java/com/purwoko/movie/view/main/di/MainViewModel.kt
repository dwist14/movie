package com.purwoko.movie.view.main.di

import androidx.lifecycle.MutableLiveData
import com.purwoko.movie.common.ViewState
import com.purwoko.movie.common.setError
import com.purwoko.movie.common.setLoading
import com.purwoko.movie.common.setSuccess
import com.purwoko.movie.core.base.BaseViewModel
import com.purwoko.movie.data.movie.MovieResponse
import com.purwoko.movie.core.di.NetworkRepository
import com.purwoko.movie.utils.Constants.COUNTRY_CODE
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val networkRepository: NetworkRepository) :
    BaseViewModel() {

    val movieLiveData = MutableLiveData<ViewState<MovieResponse>>()
    val movieLoadMoreLiveData = MutableLiveData<ViewState<MovieResponse>>()

    fun getMovie(page: Int) {
        movieLiveData.setLoading()
        launchOnUi(
            onRequest = {
                val result = networkRepository.getMovie(COUNTRY_CODE, page)
                movieLiveData.setSuccess(result)
            },
            onError = {
                movieLiveData.setError(it)
            }
        )
    }

    fun getMovieLoadMore(page: Int) {
        movieLoadMoreLiveData.setLoading()
        launchOnUi(
            onRequest = {
                val result = networkRepository.getMovie(COUNTRY_CODE, page)
                movieLoadMoreLiveData.setSuccess(result)
            },
            onError = {
                movieLoadMoreLiveData.setError(it)
            }
        )
    }
}