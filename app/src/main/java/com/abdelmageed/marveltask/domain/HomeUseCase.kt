package com.abdelmageed.marveltask.domain

import androidx.paging.PagingData
import com.abdelmageed.marveltask.data.remote.response.MarvelCharacterResponse
import com.abdelmageed.marveltask.data.remote.response.ResultsItem
import com.abdelmageed.marveltask.data.remote.response.dto.MarvelCharactersDto
import com.abdelmageed.marveltask.data.utils.BaseResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InvokeCharactersUseCase @Inject constructor(private val homeRepository: HomeRepository) {

    suspend operator fun invoke(): Flow<PagingData<ResultsItem>> =
        homeRepository.getAllCharacters()

}