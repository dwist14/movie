package com.purwoko.movie.view.favorite.di

import androidx.lifecycle.viewModelScope
import com.purwoko.movie.core.base.BaseViewModel
import com.purwoko.movie.core.di.DBRepository
import com.purwoko.movie.core.di.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val dbRepository: DBRepository
) :
    BaseViewModel() {

    fun getAllMovies() = dbRepository.getAllMovies()

    fun deleteMovie(movieId: Int) {
        viewModelScope.launch {
            dbRepository.deleteMovie(movieId)
        }
    }

}