package com.abdelmageed.marveltask.domain

import com.abdelmageed.marveltask.data.remote.response.dto.MarvelCharactersDto
import com.abdelmageed.marveltask.data.utils.BaseResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InvokeSearchUseCase @Inject constructor(private val searchRepository: SearchRepository) {
    suspend operator fun invoke(query: String): Flow<BaseResult<List<MarvelCharactersDto?>, String>> =
        searchRepository.searchCharacter(query)
}