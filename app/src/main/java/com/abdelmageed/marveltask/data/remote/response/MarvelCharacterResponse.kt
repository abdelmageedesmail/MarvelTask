package com.abdelmageed.marveltask.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class MarvelCharacterResponse(
    val copyright: String? = null,
    val code: Int? = null,
    val data: Data? = null,
    val attributionHTML: String? = null,
    val attributionText: String? = null,
    val etag: String? = null,
    val status: String? = null
)

@Serializable
data class Comics(
    val collectionURI: String? = null,
    val available: Int? = null,
    val returned: Int? = null,
    val items: List<ItemsItem?>? = null
)

@Serializable
data class ResultsItem(
    val thumbnail: Thumbnail? = null,
    val urls: List<UrlsItem?>? = null,
    val stories: Stories? = null,
    val series: Series? = null,
    val comics: Comics? = null,
    val name: String? = null,
    val description: String? = null,
    val modified: String? = null,
    val id: Int? = null,
    val resourceURI: String? = null,
    val events: Events? = null
)

@Serializable
data class Thumbnail(
    val path: String? = null,
    val extension: String? = null
)

@Serializable
data class UrlsItem(
    val type: String? = null,
    val url: String? = null
)

@Serializable
data class Stories(
    val collectionURI: String? = null,
    val available: Int? = null,
    val returned: Int? = null,
    val items: List<ItemsItem?>? = null
)

@Serializable
data class Events(
    val collectionURI: String? = null,
    val available: Int? = null,
    val returned: Int? = null,
    val items: List<ItemsItem?>? = null
)

@Serializable
data class ItemsItem(
    val name: String? = null,
    val resourceURI: String? = null,
    val type: String? = null
)

@Serializable
data class Data(
    val total: Int? = null,
    val offset: Int? = null,
    val limit: Int? = null,
    val count: Int? = null,
    val results: List<ResultsItem?>? = null
)

@Serializable
data class Series(
    val collectionURI: String? = null,
    val available: Int? = null,
    val returned: Int? = null,
    val items: List<ItemsItem?>? = null
)