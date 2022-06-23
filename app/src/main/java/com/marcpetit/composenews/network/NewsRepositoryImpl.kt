package com.marcpetit.composenews.network

import com.marcpetit.composenews.domainmodel.News
import java.lang.Exception
import javax.inject.Inject

private const val API_KEY_MISSING = "apiKeyMissing"
private const val API_KEY_INVALID = "apiKeyInvalid"

class NewsRepositoryImpl @Inject constructor(
    private val newsAPI: NewsAPI
) : NewsRepository {

    private var news: List<News> = emptyList()

    override suspend fun getNewsList(country: String): List<News> {
        val apiResponse = newsAPI.getTopHeadLines(country).body()
        apiResponse?.let {
            if (it.status == "error") {
                when (it.code) {
                    API_KEY_MISSING -> throw MissingApiKeyException
                    API_KEY_INVALID -> throw ApikeyInvalidException
                    else -> throw Exception()
                }
            }
        }
        news = apiResponse?.articles ?: emptyList()
        return news
    }

    override suspend fun getNews(title: String): News = news.first { it.title == title }
}

object MissingApiKeyException : Exception()
object ApikeyInvalidException : Exception()
