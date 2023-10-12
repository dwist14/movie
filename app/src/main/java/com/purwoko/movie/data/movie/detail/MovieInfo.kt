package com.purwoko.movie.data.movie.detail

import com.purwoko.movie.data.movie.*

data class MovieInfo(
    val adult: String?,
    val backdrop_path: String?,
    val belongs_to_collection: BelongsToCollection?,
    val budget: String?,
    val genres: List<Genre>?,
    val homepage: String?,
    val id: Int?,
    val original_language: String?,
    val original_title: String?,
    val overview: String?,
    val popularity: String?,
    val poster_path: String?,
    val production_companies: List<ProductionCompanies>?,
    val production_countries: List<ProductionCountries>?,
    val release_date: String?,
    val revenue: String?,
    val runtime: String?,
    val spoken_languages: List<SpokenLanguages>?,
    val tagline: String?,
    val title: String?,
    val video: Boolean?,
    val vote_average: Double,
    val vote_count: Int?
)