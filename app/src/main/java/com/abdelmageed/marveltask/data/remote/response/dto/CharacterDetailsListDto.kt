package com.abdelmageed.marveltask.data.remote.response.dto

import kotlinx.serialization.Serializable

@Serializable
data class CharacterDetailsListDto(
    val id: Int? = null,
    val name: String? = null,
    val imageUrl: String? = null,
    val resourceUrl: String? = null
)
