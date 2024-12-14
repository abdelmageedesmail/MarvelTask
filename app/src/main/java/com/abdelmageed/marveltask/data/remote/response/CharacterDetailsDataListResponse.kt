package com.abdelmageed.marveltask.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class CharacterDetailsDataListResponse(
    val copyright: String? = null,
    val code: Int? = null,
    val data: DetailsData? = null,
    val attributionHTML: String? = null,
    val attributionText: String? = null,
    val etag: String? = null,
    val status: String? = null
)

@Serializable
data class DetailsData(
    val total: Int? = null,
    val offset: Int? = null,
    val limit: Int? = null,
    val count: Int? = null,
    val results: List<DetailsResultsItem?>? = null
)

@Serializable
data class Previous(
    val name: String? = null,
    val resourceURI: String? = null
)


@Serializable
data class Next(
    val name: String? = null,
    val resourceURI: String? = null
)

@Serializable
data class Characters(
    val collectionURI: String? = null,
    val available: Int? = null,
    val returned: Int? = null,
    val items: List<ItemsItem?>? = null
)

@Serializable
data class Creators(
    val collectionURI: String? = null,
    val available: Int? = null,
    val returned: Int? = null,
    val items: List<ItemsItem?>? = null
)

@Serializable
data class DetailsResultsItem(
//	val next: Any? = null,
    val thumbnail: Thumbnail? = null,
    val stories: Stories? = null,
//	val previous: Any? = null,
    val creators: Creators? = null,
    val comics: Comics? = null,
    val startYear: Int? = null,
    val rating: String? = null,
//	val description: Any? = null,
    val resourceURI: String? = null,
    val title: String? = null,
    val type: String? = null,
    val endYear: Int? = null,
    val characters: Characters? = null,
    val urls: List<UrlsItem?>? = null,
    val modified: String? = null,
    val id: Int? = null,
    val events: Events? = null
)

