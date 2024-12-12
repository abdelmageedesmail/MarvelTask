package com.abdelmageed.marveltask.domain

import com.abdelmageed.marveltask.data.remote.response.MarvelCharacterResponse
import com.abdelmageed.marveltask.data.utils.BaseResult
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun getAllCharacters(): Flow<BaseResult<MarvelCharacterResponse, String>>
}