package com.abdelmageed.marveltask.domain

import androidx.paging.PagingData
import com.abdelmageed.marveltask.data.remote.response.MarvelCharacterResponse
import com.abdelmageed.marveltask.data.remote.response.ResultsItem
import com.abdelmageed.marveltask.data.remote.response.dto.MarvelCharactersDto
import com.abdelmageed.marveltask.data.utils.BaseResult
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun getAllCharacters(): Flow<PagingData<ResultsItem>>
}