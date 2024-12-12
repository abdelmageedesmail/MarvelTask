package com.abdelmageed.marveltask.data.repository

import com.abdelmageed.marveltask.data.remote.response.MarvelCharacterResponse
import com.abdelmageed.marveltask.data.utils.BaseResult
import com.abdelmageed.marveltask.domain.HomeRepository
import com.abdelmageed.marveltask.extensions.getHash
import com.abdelmageed.marveltask.utils.ConstantsUrls
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.parameters
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(private val httpClient: HttpClient) : HomeRepository {
    override suspend fun getAllCharacters(): Flow<BaseResult<MarvelCharacterResponse, String>> =
        flow {
            val timeStamp = System.currentTimeMillis()
            val hash = "$timeStamp${ConstantsUrls.privateKey}${ConstantsUrls.publicKey}".getHash()
            try {
                val response =
                    httpClient.get("public/characters?ts=$timeStamp&apikey=${ConstantsUrls.publicKey}&hash=$hash")
//                {
//                    parameters {
//                        append("ts", "$timeStamp")
//                        append("apikey", ConstantsUrls.publicKey)
//                        append("hash", hash)
//                    }
//                }
                if (response.status.value == 200) {
                    emit(BaseResult.Success(response.body()))
                }
            } catch (e: Exception) {
                emit(BaseResult.Error(e.message.toString()))
            }
        }
}