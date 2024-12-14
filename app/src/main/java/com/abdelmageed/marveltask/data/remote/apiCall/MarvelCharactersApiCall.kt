package com.abdelmageed.marveltask.data.remote.apiCall

import com.abdelmageed.marveltask.data.remote.response.MarvelCharacterResponse
import com.abdelmageed.marveltask.extensions.getHash
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.url

/**
 * A class responsible for making API calls to retrieve Marvel character data.
 *
 * This class uses the provided `HttpClient`, `privateKey`, and `publicKey`
 * to construct and execute requests to the Marvel API. It handles generating
 * the required timestamp and hash for authentication.
 *
 * @param client The `HttpClient` used to make API requests.
 * @param privateKey The Marvel API private key.
 * @param publicKey The Marvel API public key.
 */
class MarvelCharactersApiCall(
    private val client: HttpClient,
    private val privateKey: String,
    private val publicKey: String
) {
    suspend fun getCharacters(offset: Int, limit: Int): MarvelCharacterResponse {
        val timeStamp = System.currentTimeMillis()
        val hash = "$timeStamp${privateKey}${publicKey}".getHash()
        return client.get {
            url("public/characters?ts=$timeStamp&apikey=$publicKey&hash=$hash&offset=$offset")
        }.body()
    }
}