package com.example.therickandmorty

import com.apollographql.apollo.ApolloClient

val apolloClient: ApolloClient = ApolloClient.builder()
    .serverUrl("https://rickandmortyapi.com/graphql")
    .build()