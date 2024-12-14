package com.abdelmageed.marveltask.domain

import com.abdelmageed.marveltask.data.remote.response.dto.CharacterDetailsListDto
import com.abdelmageed.marveltask.data.remote.response.dto.MarvelCharactersDto
import com.abdelmageed.marveltask.data.remote.response.dto.SectionImageDetailsDto
import com.abdelmageed.marveltask.data.utils.BaseResult
import kotlinx.coroutines.flow.Flow

interface DetailsRepository {

    suspend fun getCharacterDetails(id: Int): Flow<BaseResult<MarvelCharactersDto, String>>

    suspend fun getComics(
        id: Int
    ): Flow<BaseResult<List<CharacterDetailsListDto?>, String>>

    suspend fun getEvents(
        id: Int
    ): Flow<BaseResult<List<CharacterDetailsListDto?>, String>>

    suspend fun getSeries(
        id: Int
    ): Flow<BaseResult<List<CharacterDetailsListDto?>, String>>

    suspend fun getStories(
        id: Int
    ): Flow<BaseResult<List<CharacterDetailsListDto?>, String>>

    suspend fun getSectionImages(url: String): Flow<BaseResult<List<SectionImageDetailsDto?>, String>>
}