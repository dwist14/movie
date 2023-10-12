package com.purwoko.movie.view.detailMovie

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.purwoko.movie.R
import com.purwoko.movie.common.*
import com.purwoko.movie.core.base.BaseActivity
import com.purwoko.movie.data.movie.Movie
import com.purwoko.movie.data.movie.detail.MovieInfo
import com.purwoko.movie.databinding.ActivityDetailMovieBinding
import com.purwoko.movie.utils.Constants
import com.purwoko.movie.utils.LogData
import com.purwoko.movie.view.detailMovie.adapter.MovieReviewAdapter
import com.purwoko.movie.view.detailMovie.adapter.MovieTrailerAdapter
import com.purwoko.movie.view.detailMovie.di.DetailMovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.roundToInt

@AndroidEntryPoint
class DetailMovieActivity : BaseActivity<DetailMovieViewModel, ActivityDetailMovieBinding>(
    ActivityDetailMovieBinding::inflate
) {

    var movie: Movie? = null

    var page = 1
    private val mAdapterReview by lazy {
        MovieReviewAdapter()
    }

    private val mAdapterTrailer by lazy {
        MovieTrailerAdapter() {
            startActivity<PlayerActivity>() {
                putExtra("key", it)
            }
        }
    }

    override fun onViewCreated(savedInstanceState: Bundle?) {
        initToolbar(binding.toolbar)
        movie = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(Constants.MOVIE_ID, Movie::class.java)
        } else {
            intent.getParcelableExtra(Constants.MOVIE_ID)
        }

        binding.rvListReview.apply {
            linearLayoutManager()
            adapter = mAdapterReview
        }

        binding.rvListTrailer.apply {
            linearLayoutManager(RecyclerView.HORIZONTAL)
            adapter = mAdapterTrailer
        }

        viewModel.getMovieId(movie?.id.orZero())
        viewModel.getReviewMovieId(movie?.id.orZero(), page)
        viewModel.getVideoMovieId(movie?.id.orZero())
    }

    override fun observerViewModel() {
        viewModel.movieInfoLiveData.onResult { state ->
            when (state) {
                is ViewState.Success -> {
                    binding.stateLayout.toContent()
                    updateUI(state.data)
                }
                is ViewState.Failed -> {
                    binding.stateLayout.toError {
                        viewModel.getMovieId(movie?.id.orZero())
                    }
                }
                is ViewState.Loading -> {
                    binding.stateLayout.toLoading()
                }
            }
        }

        viewModel.movieVideoLiveData.onResult { state ->
            when (state) {
                is ViewState.Success -> {
                    binding.llTrailer.isVisible = state.data.results.isNotEmpty()
                    if (state.data.results.isNotEmpty()) {
                        mAdapterTrailer.setList(state.data.results)
                    }
                }
                is ViewState.Failed -> {
                    LogData("ERROR " + state.message)
                }
                is ViewState.Loading -> {
                    LogData("LOADING..")
                }
            }
        }

        viewModel.movieReviewLiveData.onResult { state ->
            when (state) {
                is ViewState.Success -> {
                    binding.llReview.isVisible = state.data.results.isNotEmpty()
                    if (state.data.results.isNotEmpty()) {
                        mAdapterReview.setList(state.data.results)
                    }
                }
                is ViewState.Failed -> {
                    LogData("ERROR " + state.message)
                }
                is ViewState.Loading -> {
                    LogData("LOADING..")
                }
            }
        }

        viewModel.getCountMovie(movie?.id.orZero()).onResult { data ->
            if (data >= 1) {
                binding.saveFab.setImageResource(R.drawable.ic_baseline_favorite_24)
            } else {
                binding.saveFab.setImageResource(R.drawable.ic_baseline_favorite_border_24)
            }

            binding.saveFab.setOnClickListener {
                if (data >= 1) {
                    if (movie != null) {
                        viewModel.deleteMovie(movie?.id.orZero())
                    }
                    Snackbar.make(binding.root, "Hapus favorit berhasil", Snackbar.LENGTH_LONG)
                        .show()
                } else {
                    if (movie != null) {
                        viewModel.insertMovie(movie!!)
                    }
                    Snackbar.make(binding.root, "Tambah ke favorit berhasil", Snackbar.LENGTH_LONG)
                        .show()
                }

            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateUI(data: MovieInfo) {
        binding.ivDetailMovieCover.loadImage(Constants.URL_IMAGE + data.backdrop_path)
        binding.tvDetailMovieTitle.text = data.title
        binding.tvDetailMovieDesc.text = data.overview
        val avg = (data.vote_average * 10.0).roundToInt() / 10.0
        binding.tvRating.text = "$avg"
        binding.rating.rating = avg.toFloat() / 2
    }

}