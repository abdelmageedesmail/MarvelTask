package com.abdelmageed.marveltask.domain

import com.abdelmageed.marveltask.data.remote.response.MarvelCharacterResponse
import com.abdelmageed.marveltask.data.utils.BaseResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InvokeCharactersUseCase @Inject constructor(private val homeRepository: HomeRepository) {

    suspend operator fun invoke(): Flow<BaseResult<MarvelCharacterResponse, String>> =
        homeRepository.getAllCharacters()

}