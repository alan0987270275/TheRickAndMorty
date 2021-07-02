package com.example.therickandmorty.data.api

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import com.example.therickandmortyserver.CharacterListQuery

class ApiHelper(private val apolloClient: ApolloClient) {

    suspend fun getCharacter() = apolloClient.query(CharacterListQuery()).await()
}