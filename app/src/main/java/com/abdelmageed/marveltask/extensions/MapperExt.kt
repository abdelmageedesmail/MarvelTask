package com.abdelmageed.marveltask.extensions

import com.abdelmageed.marveltask.data.remote.response.DetailsResultsItem
import com.abdelmageed.marveltask.data.remote.response.ResultsItem
import com.abdelmageed.marveltask.data.remote.response.SectionDetailsResponse
import com.abdelmageed.marveltask.data.remote.response.SectionDetailsResultsItem
import com.abdelmageed.marveltask.data.remote.response.UrlsItem
import com.abdelmageed.marveltask.data.remote.response.dto.CharacterDetailsListDto
import com.abdelmageed.marveltask.data.remote.response.dto.MarvelCharactersDto
import com.abdelmageed.marveltask.data.remote.response.dto.SectionImageDetailsDto
import com.abdelmageed.marveltask.data.remote.response.dto.UrlDto

fun ResultsItem.toMarvelCharactersDto(): MarvelCharactersDto =
    MarvelCharactersDto(
        id = id,
        title = name,
        imageUrl = "${thumbnail?.path}.${thumbnail?.extension}",
        description = description,
        resourceUri = resourceURI,
        urls = urls?.map { it?.toUrlsDto() ?: UrlDto() }
    )

fun UrlsItem.toUrlsDto(): UrlDto = UrlDto(
    type = type,
    url = url
)

fun DetailsResultsItem.toCharacterDetailsDto(): CharacterDetailsListDto = CharacterDetailsListDto(
    id = id,
    name = title,
    imageUrl = "${thumbnail?.path}.${thumbnail?.extension}",
    resourceUrl = resourceURI
)

fun SectionDetailsResultsItem.toSectionImagesDetailsDto() = SectionImageDetailsDto(
    images = images?.map { "${it?.path}.${it?.extension}" },
    name = title
)