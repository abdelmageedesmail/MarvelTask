package com.abdelmageed.marveltask.data.repository

import com.abdelmageed.marveltask.data.remote.response.MarvelCharacterResponse
import com.abdelmageed.marveltask.data.remote.response.dto.MarvelCharactersDto
import com.abdelmageed.marveltask.data.utils.BaseResult
import com.abdelmageed.marveltask.domain.SearchRepository
import com.abdelmageed.marveltask.extensions.getHash
import com.abdelmageed.marveltask.extensions.toMarvelCharactersDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val httpClient: HttpClient,
    private val privateKey: String,
    private val publicKey: String
) :
    SearchRepository {
    override suspend fun searchCharacter(query: String): Flow<BaseResult<List<MarvelCharactersDto>, String>> =
        flow {
            val timeStamp = System.currentTimeMillis()
            val hash = "$timeStamp${privateKey}${publicKey}".getHash()
            try {
                val response =
                    httpClient.get("public/characters?ts=$timeStamp&apikey=${publicKey}&hash=$hash") {
                        parameter("nameStartsWith", query)
                    }
                val result =
                    (response.body<MarvelCharacterResponse>()).data?.results?.map { it.toMarvelCharactersDto() }
                emit(BaseResult.Success(result ?: emptyList()))
            } catch (e: Exception) {
                emit(BaseResult.Error(e.message.toString()))
            }
        }
}