package com.purwoko.movie.view.favorite

import android.os.Bundle
import com.purwoko.movie.common.linearLayoutManager
import com.purwoko.movie.common.toContent
import com.purwoko.movie.common.toEmpty
import com.purwoko.movie.core.base.BaseActivity
import com.purwoko.movie.databinding.FavoriteMovieActivityBinding
import com.purwoko.movie.view.favorite.di.FavoriteViewModel
import com.purwoko.movie.view.favorite.adapter.MovieFavoriteAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteMovieActivity :
    BaseActivity<FavoriteViewModel, FavoriteMovieActivityBinding>(FavoriteMovieActivityBinding::inflate) {

    private val mAdapter by lazy {
        MovieFavoriteAdapter() {
            if (it != 0) {
                viewModel.deleteMovie(it)
            }
        }
    }

    override fun onViewCreated(savedInstanceState: Bundle?) {
        binding.rvListMovie.apply {
            linearLayoutManager()
            adapter = mAdapter
        }
    }

    override fun observerViewModel() {
        viewModel.getAllMovies().onResult { data ->
            if (data.isNotEmpty()) {
                binding.stateLayout.toContent()
                mAdapter.setList(data)
            } else {
                binding.stateLayout.toEmpty()
            }
        }
    }
}