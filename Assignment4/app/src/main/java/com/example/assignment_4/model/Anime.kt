package com.example.assignment_4.model

data class Anime(
    val mal_id: Int,
    val title: String,
    val images: Images,
    val synopsis: String?,
    val episodes: Int?,
    val score: Double?
)

data class Images(
    val jpg: ImageUrl
)

data class ImageUrl(
    val image_url: String
)
