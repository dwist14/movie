package com.purwoko.movie.view.detailMovie.adapter

import com.purwoko.movie.common.loadImage
import com.purwoko.movie.common.thumbnailYoutube
import com.purwoko.movie.core.base.BaseBindingAdapter
import com.purwoko.movie.core.base.VBViewHolder
import com.purwoko.movie.data.movie.video.MovieVideo
import com.purwoko.movie.databinding.MovieTrailerItemBinding

class MovieTrailerAdapter(private val onItemClick: (String) -> Unit) :
    BaseBindingAdapter<MovieTrailerItemBinding, MovieVideo>() {

    override fun convert(holder: VBViewHolder<MovieTrailerItemBinding>, item: MovieVideo) {
        val binding: MovieTrailerItemBinding = holder.vb
        binding.tvMovieTrailer.text = item.name
        item.key?.thumbnailYoutube()?.let { binding.ivMovieTrailer.loadImage(it) }
        binding.item.setOnClickListener {
            onItemClick.invoke(item.key.orEmpty())
        }
    }
}
