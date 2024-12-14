package com.abdelmageed.marveltask.data.remote.response

import kotlinx.serialization.Serializable

@Serializable
data class SectionDetailsResponse(
    val copyright: String? = null,
    val code: Int? = null,
    val data: SectionDetailsData? = null,
    val attributionHTML: String? = null,
    val attributionText: String? = null,
    val etag: String? = null,
    val status: String? = null
)

@Serializable
data class DatesItem(
    val date: String? = null,
    val type: String? = null
)

@Serializable
data class TextObjectsItem(
    val language: String? = null,
    val text: String? = null,
    val type: String? = null
)

@Serializable
data class SectionDetailsResultsItem(
    val creators: Creators? = null,
    val issueNumber: Int? = null,
    val isbn: String? = null,
    val description: String? = null,
    val title: String? = null,
    val diamondCode: String? = null,
    val characters: Characters? = null,
    val urls: List<UrlsItem?>? = null,
    val ean: String? = null,
    val modified: String? = null,
    val id: Int? = null,
    val prices: List<PricesItem?>? = null,
    val events: Events? = null,
    val pageCount: Int? = null,
    val thumbnail: Thumbnail? = null,
    val images: List<ImagesItem?>? = null,
    val stories: Stories? = null,
    val textObjects: List<TextObjectsItem?>? = null,
    val digitalId: Int? = null,
    val format: String? = null,
    val upc: String? = null,
    val dates: List<DatesItem?>? = null,
    val resourceURI: String? = null,
    val variantDescription: String? = null,
    val issn: String? = null,
    val series: Series? = null
)

@Serializable
data class ImagesItem(
    val path: String? = null,
    val extension: String? = null
)

@Serializable
data class PricesItem(
    val price: Float? = null,
    val type: String? = null
)

@Serializable
data class SectionDetailsData(
    val total: Int? = null,
    val offset: Int? = null,
    val limit: Int? = null,
    val count: Int? = null,
    val results: List<SectionDetailsResultsItem?> = emptyList()
)

