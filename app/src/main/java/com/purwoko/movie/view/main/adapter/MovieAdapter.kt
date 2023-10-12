package com.purwoko.movie.view.main.adapter

import com.chad.library.adapter.base.module.LoadMoreModule
import com.purwoko.movie.common.loadImage
import com.purwoko.movie.core.base.BaseBindingAdapter
import com.purwoko.movie.core.base.VBViewHolder
import com.purwoko.movie.data.movie.Movie
import com.purwoko.movie.databinding.MovieItemBinding
import com.purwoko.movie.utils.Constants.URL_IMAGE

class MovieAdapter(private val onItemClick: (Movie) -> Unit) :
    BaseBindingAdapter<MovieItemBinding, Movie>(), LoadMoreModule {

    override fun convert(holder: VBViewHolder<MovieItemBinding>, item: Movie) {
        val binding: MovieItemBinding = holder.vb
        binding.ivMovie.loadImage(URL_IMAGE + item.poster_path)
        binding.item.setOnClickListener {
            onItemClick.invoke(item)
        }
    }
}
