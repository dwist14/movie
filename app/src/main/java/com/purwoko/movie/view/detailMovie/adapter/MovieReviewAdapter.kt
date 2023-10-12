package com.purwoko.movie.view.detailMovie.adapter

import com.purwoko.movie.common.formatDate
import com.purwoko.movie.core.base.BaseBindingAdapter
import com.purwoko.movie.core.base.VBViewHolder
import com.purwoko.movie.data.movie.review.MovieReview
import com.purwoko.movie.databinding.MovieReviewItemBinding

class MovieReviewAdapter :
    BaseBindingAdapter<MovieReviewItemBinding, MovieReview>() {

    override fun convert(holder: VBViewHolder<MovieReviewItemBinding>, item: MovieReview) {
        val binding: MovieReviewItemBinding = holder.vb
        binding.tvAuthor.text = item.author
        binding.tvCreate.text = item.created_at?.formatDate()
        binding.tvContent.text = item.content
    }
}
