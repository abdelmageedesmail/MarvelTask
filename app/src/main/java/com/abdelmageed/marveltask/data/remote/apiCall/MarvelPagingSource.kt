package com.abdelmageed.marveltask.data.remote.apiCall

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.abdelmageed.marveltask.data.remote.response.ResultsItem

/**
 * A PagingSource implementation for fetching Marvel characters from the Marvel API.
 * This class handles loading data in pages, providing previous and next keys for navigation.
 *
 * @param apiClient The API client used to fetch characters from the Marvel API.
 */
class MarvelPagingSource(
    private val apiClient: MarvelCharactersApiCall
) : PagingSource<Int, ResultsItem>() {
    /**
     * This function defines the refresh key for the paging data source.
     * The refresh key is used to determine where to load data from when the user refreshes the data.
     *
     * In this implementation, the refresh key is calculated based on the current anchor position and the closest page to that position.
     * If the closest page has a previous key, it is incremented by 20 and returned as the refresh key.
     * If the closest page does not have a previous key but has a next key, it is decremented by 20 and returned as the refresh key.
     * If neither a previous key nor a next key is available, the refresh key is null.
     *
     * @param state The current paging state, which contains information about the loaded pages and the anchor position.
     * @return The refresh key, which is an integer value used to determine where to load data from when refreshing.
     */
    override fun getRefreshKey(state: PagingState<Int, ResultsItem>): Int? {
        return state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.prevKey?.plus(20)
                ?: state.closestPageToPosition(anchor)?.nextKey?.minus(20)
        }
    }

    /**
     * Loads a page of characters from the API.
     *
     * @param params The parameters for the load operation.
     *  - `key`: The offset of the page to load. If null, the first page will be loaded.
     *  - `loadSize`: The number of characters to load in the page.
     *
     * @return A `LoadResult` containing the loaded characters or an error.
     *  - `LoadResult.Page`: Contains the list of characters, the previous key (if available), and the next key (if available).
     *  - `LoadResult.Error`: Contains the exception that occurred during loading.
     */
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultsItem> {
        val offset = params.key ?: 0
        return try {
            val response = apiClient.getCharacters(offset, params.loadSize)
            val characters = response.data?.results
            LoadResult.Page(
                data = characters ?: emptyList(),
                prevKey = if (offset == 0) null else offset - params.loadSize,
                nextKey = if (characters?.isEmpty() == true) null else offset + params.loadSize
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}