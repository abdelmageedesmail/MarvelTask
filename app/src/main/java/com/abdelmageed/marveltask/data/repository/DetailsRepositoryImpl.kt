package com.abdelmageed.marveltask.data.repository

import com.abdelmageed.marveltask.data.remote.response.CharacterDetailsDataListResponse
import com.abdelmageed.marveltask.data.remote.response.MarvelCharacterResponse
import com.abdelmageed.marveltask.data.remote.response.SectionDetailsResponse
import com.abdelmageed.marveltask.data.remote.response.dto.CharacterDetailsListDto
import com.abdelmageed.marveltask.data.remote.response.dto.MarvelCharactersDto
import com.abdelmageed.marveltask.data.remote.response.dto.SectionImageDetailsDto
import com.abdelmageed.marveltask.data.utils.BaseResult
import com.abdelmageed.marveltask.domain.DetailsRepository
import com.abdelmageed.marveltask.extensions.getHash
import com.abdelmageed.marveltask.extensions.toCharacterDetailsDto
import com.abdelmageed.marveltask.extensions.toMarvelCharactersDto
import com.abdelmageed.marveltask.extensions.toSectionImagesDetailsDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Implementation of [DetailsRepository] that fetches character details and related data from the Marvel API.
 *
 * @param httpClient The HTTP client used for making API requests.
 * @param privateKey The private key for generating the API request hash.
 * @param publicKey The public key for authenticating API requests.
 */
class DetailsRepositoryImpl @Inject constructor(
    private val httpClient: HttpClient,
    private val privateKey: String,
    private val publicKey: String
) :
    DetailsRepository {
    private val timeStamp = System.currentTimeMillis()
    private var hash = ""

    init {
        hash = "$timeStamp${privateKey}${publicKey}".getHash()
    }

    /**
     * Retrieves the detailed information for a specific Marvel character.
     *
     * @param id The unique identifier of the character.
     * @return A [Flow] emitting a [BaseResult] containing either the [MarvelCharactersDto] for the character
     * or an error message represented as a [String].
     */
    override suspend fun getCharacterDetails(id: Int): Flow<BaseResult<MarvelCharactersDto, String>> =
        flow {
            try {
                val response =
                    httpClient.get("public/characters/$id?ts=$timeStamp&apikey=${publicKey}&hash=$hash")
                if (response.status.value == 200) {
                    val details =
                        (response.body() as MarvelCharacterResponse).data?.results?.map { it?.toMarvelCharactersDto() }
                            ?: emptyList()
                    emit(BaseResult.Success(details.first() ?: MarvelCharactersDto()))
                }
            } catch (e: Exception) {
                emit(BaseResult.Error(e.message.toString()))
            }
        }

    /**
     * Retrieves a list of comics related to a specific character.
     *
     * @param id The ID of the character.
     * @return A [Flow] emitting a [BaseResult] containing either a list of [CharacterDetailsListDto] objects or an error message.
     */
    override suspend fun getComics(
        id: Int
    ): Flow<BaseResult<List<CharacterDetailsListDto?>, String>> =
        flow {
            try {
                val response =
                    httpClient.get("public/characters/$id/comics?ts=$timeStamp&apikey=${publicKey}&hash=$hash")
                if (response.status.value == 200) {
                    val comics =
                        (response.body() as CharacterDetailsDataListResponse).data?.results?.map { it?.toCharacterDetailsDto() }
                            ?: emptyList()
                    emit(BaseResult.Success(comics))
                }
            } catch (e: Exception) {
                emit(BaseResult.Error(e.message.toString()))
            }
        }

    override suspend fun getEvents(
        id: Int
    ): Flow<BaseResult<List<CharacterDetailsListDto?>, String>> =
        flow {
            try {
                val response =
                    httpClient.get("public/characters/$id/events?ts=$timeStamp&apikey=${publicKey}&hash=$hash")
                if (response.status.value == 200) {
                    val comics =
                        (response.body() as CharacterDetailsDataListResponse).data?.results?.map { it?.toCharacterDetailsDto() }
                            ?: emptyList()
                    emit(BaseResult.Success(comics))
                }
            } catch (e: Exception) {
                emit(BaseResult.Error(e.message.toString()))
            }
        }

    override suspend fun getSeries(
        id: Int
    ): Flow<BaseResult<List<CharacterDetailsListDto?>, String>> =
        flow {
            try {
                val response =
                    httpClient.get("public/characters/$id/series?ts=$timeStamp&apikey=${publicKey}&hash=$hash")
                if (response.status.value == 200) {
                    val comics =
                        (response.body() as CharacterDetailsDataListResponse).data?.results?.map { it?.toCharacterDetailsDto() }
                            ?: emptyList()
                    emit(BaseResult.Success(comics))
                }
            } catch (e: Exception) {
                emit(BaseResult.Error(e.message.toString()))
            }
        }

    override suspend fun getStories(
        id: Int
    ): Flow<BaseResult<List<CharacterDetailsListDto?>, String>> =
        flow {
            try {
                val response =
                    httpClient.get("public/characters/$id/stories?ts=$timeStamp&apikey=${publicKey}&hash=$hash")
                if (response.status.value == 200) {
                    val comics =
                        (response.body() as CharacterDetailsDataListResponse).data?.results?.map { it?.toCharacterDetailsDto() }
                            ?: emptyList()
                    emit(BaseResult.Success(comics))
                }
            } catch (e: Exception) {
                emit(BaseResult.Error(e.message.toString()))
            }
        }

    override suspend fun getSectionImages(url: String): Flow<BaseResult<List<SectionImageDetailsDto?>, String>> =
        flow {
            try {
                val response =
                    httpClient.get("$url?ts=$timeStamp&apikey=${publicKey}&hash=$hash")
                if (response.status.value == 200) {
                    val results =
                        (response.body() as SectionDetailsResponse).data?.results?.map { it?.toSectionImagesDetailsDto() }
                            ?: emptyList()
                    emit(BaseResult.Success(results))
                }
            } catch (e: Exception) {
                emit(BaseResult.Error(e.message.toString()))
            }
        }
}