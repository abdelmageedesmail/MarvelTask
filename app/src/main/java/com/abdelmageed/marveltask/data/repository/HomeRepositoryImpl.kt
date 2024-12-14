package com.abdelmageed.marveltask.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.abdelmageed.marveltask.data.remote.apiCall.MarvelCharactersApiCall
import com.abdelmageed.marveltask.data.remote.apiCall.MarvelPagingSource
import com.abdelmageed.marveltask.data.remote.response.ResultsItem
import com.abdelmageed.marveltask.domain.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


/**
 * Repository implementation for fetching and managing Marvel characters data.

 * This class uses the `MarvelCharactersApiCall` to retrieve data from the API

 * and provides a flow of `PagingData<ResultsItem>` for displaying the characters

 * in a paginated manner.

 */
class HomeRepositoryImpl @Inject constructor(
    private val apiClient: MarvelCharactersApiCall
) :
    HomeRepository {
    override suspend fun getAllCharacters(): Flow<PagingData<ResultsItem>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { MarvelPagingSource(apiClient) }
        ).flow
    }
}