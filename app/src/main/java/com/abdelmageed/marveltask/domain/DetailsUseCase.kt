package com.abdelmageed.marveltask.domain

import com.abdelmageed.marveltask.data.remote.response.dto.CharacterDetailsListDto
import com.abdelmageed.marveltask.data.remote.response.dto.MarvelCharactersDto
import com.abdelmageed.marveltask.data.remote.response.dto.SectionImageDetailsDto
import com.abdelmageed.marveltask.data.utils.BaseResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InvokeDetailsUseCase @Inject constructor(private val detailsRepository: DetailsRepository) {

    suspend fun invokeCharacterDetails(id: Int): Flow<BaseResult<MarvelCharactersDto, String>> =
        detailsRepository.getCharacterDetails(id)

    suspend fun invokeComics(
        id: Int
    ): Flow<BaseResult<List<CharacterDetailsListDto?>, String>> =
        detailsRepository.getComics(id)

    suspend fun invokeSeries(
        id: Int
    ): Flow<BaseResult<List<CharacterDetailsListDto?>, String>> =
        detailsRepository.getSeries(id)

    suspend fun invokeStories(
        id: Int
    ): Flow<BaseResult<List<CharacterDetailsListDto?>, String>> =
        detailsRepository.getStories(id)

    suspend fun invokeEvents(
        id: Int
    ): Flow<BaseResult<List<CharacterDetailsListDto?>, String>> =
        detailsRepository.getEvents(id)
}

class InvokeSectionDetails @Inject constructor(private val detailsRepository: DetailsRepository) {
    suspend fun invokeSectionImages(url: String): Flow<BaseResult<List<SectionImageDetailsDto?>, String>> =
        detailsRepository.getSectionImages(url)

}