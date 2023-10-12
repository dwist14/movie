package com.purwoko.movie.view.favorite.adapter

import com.chad.library.adapter.base.module.LoadMoreModule
import com.purwoko.movie.common.loadImage
import com.purwoko.movie.common.orZero
import com.purwoko.movie.core.base.BaseBindingAdapter
import com.purwoko.movie.core.base.VBViewHolder
import com.purwoko.movie.data.movie.Movie
import com.purwoko.movie.databinding.MovieFavoriteItemBinding
import com.purwoko.movie.databinding.MovieItemBinding
import com.purwoko.movie.core.db.entity.MovieEntity
import com.purwoko.movie.utils.Constants.URL_IMAGE
import kotlin.math.roundToInt

class MovieFavoriteAdapter(private val onItemClick: (Int) -> Unit) :
    BaseBindingAdapter<MovieFavoriteItemBinding, MovieEntity>(), LoadMoreModule {

    override fun convert(holder: VBViewHolder<MovieFavoriteItemBinding>, item: MovieEntity) {
        val binding: MovieFavoriteItemBinding = holder.vb
        binding.ivMovie.loadImage(URL_IMAGE + item.poster_path)
        binding.tvDetailMovieTitle.text = item.title
        binding.tvDetailMovieDesc.text = item.overview
        val avg = (item.vote_average?.times(10.0))?.roundToInt()?.div(10.0)
        binding.tvRating.text = "$avg"
        binding.rating.rating = avg?.toFloat()?.div(2) ?: 0.toFloat()

        binding.ivDelete.setOnClickListener {
            onItemClick.invoke(item.movie_id.orZero())
        }
    }
}
