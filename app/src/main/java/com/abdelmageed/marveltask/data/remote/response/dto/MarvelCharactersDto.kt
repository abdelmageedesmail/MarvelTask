package com.abdelmageed.marveltask.data.remote.response.dto

data class MarvelCharactersDto(
    val id: Int? = null,
    val title: String? = null,
    val imageUrl: String? = null,
    val description: String? = null,
    val resourceUri: String? = null,
    val urls: List<UrlDto>? = null,
)

data class UrlDto(
    val type: String? = null,
    val url: String? = null,
)