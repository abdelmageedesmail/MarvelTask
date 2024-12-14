package com.abdelmageed.marveltask.domain

import com.abdelmageed.marveltask.data.remote.response.dto.MarvelCharactersDto
import com.abdelmageed.marveltask.data.utils.BaseResult
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun searchCharacter(query: String): Flow<BaseResult<List<MarvelCharactersDto?>, String>>
}