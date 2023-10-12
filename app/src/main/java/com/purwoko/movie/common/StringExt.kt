package com.purwoko.movie.common


fun String.thumbnailYoutube(): String {
    return "https://img.youtube.com/vi/${this}/0.jpg"
}