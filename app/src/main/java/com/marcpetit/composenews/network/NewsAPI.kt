package com.marcpetit.composenews.network

import com.marcpetit.composenews.domainmodel.NewsAPIResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = "67d042f0af9c4d698e8e49d7461ee17e"

interface NewsAPI {

    @GET("top-headlines?apiKey=$API_KEY")
    suspend fun getTopHeadLines(@Query("country") country: String): Response<NewsAPIResponse>
}
