package com.purwoko.movie.view.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.purwoko.movie.R
import com.purwoko.movie.common.*
import com.purwoko.movie.core.base.BaseActivity
import com.purwoko.movie.databinding.ActivityMainBinding
import com.purwoko.movie.utils.Constants
import com.purwoko.movie.utils.LogData
import com.purwoko.movie.view.detailMovie.DetailMovieActivity
import com.purwoko.movie.view.favorite.FavoriteMovieActivity
import com.purwoko.movie.view.main.adapter.MovieAdapter
import com.purwoko.movie.view.main.di.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity :
    BaseActivity<MainViewModel, ActivityMainBinding>(ActivityMainBinding::inflate) {

    var page = 1
    private val mAdapter by lazy {
        MovieAdapter() {
            startActivity<DetailMovieActivity> {
                putExtra(Constants.MOVIE_ID, it)
            }
        }
    }

    override fun onViewCreated(savedInstanceState: Bundle?) {
        binding.rvListMovie.apply {
            gridLayoutManager(2)
            adapter = mAdapter
        }

        fetchData()
    }

    private fun fetchData() {
        viewModel.getMovie(page)
    }

    private fun canLoadMore() {
        mAdapter.let {
            it.loadMoreModule.isAutoLoadMore = true
            it.loadMoreModule.isEnableLoadMoreIfNotFullPage = false
            it.loadMoreModule.setOnLoadMoreListener {
                viewModel.getMovieLoadMore(page)
            }
        }
    }

    override fun observerViewModel() {
        viewModel.movieLiveData.onResult { state ->
            when (state) {
                is ViewState.Success -> {
                    if (state.data.results.isNotEmpty()) {
                        binding.stateLayout.toContent()
                        mAdapter.setList(state.data.results)
                        if (state.data.total_pages != page) {
                            page++
                            canLoadMore()
                        }
                    } else {
                        binding.stateLayout.toEmpty()
                    }
                }
                is ViewState.Failed -> {
                    LogData("ERROR " + state.message)
                    binding.stateLayout.toError {
                        fetchData()
                    }
                }
                is ViewState.Loading -> {
                    binding.stateLayout.toLoading()
                }
            }
        }

        viewModel.movieLoadMoreLiveData.onResult { state ->
            when (state) {
                is ViewState.Loading -> {}

                is ViewState.Success -> {
                    mAdapter.addData(state.data.results)
                    mAdapter.loadMoreModule.loadMoreComplete()
                    if (state.data.total_pages != page) {
                        page++
                        canLoadMore()
                    } else {
                        mAdapter.loadMoreModule.loadMoreEnd()
                    }
                }

                is ViewState.Failed -> {
                    mAdapter.loadMoreModule.loadMoreFail()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_fav -> startActivity<FavoriteMovieActivity> { }
        }
        return super.onOptionsItemSelected(item)
    }
}