package com.marcpetit.composenews.network

import com.marcpetit.composenews.domainmodel.News

interface NewsRepository {
    suspend fun getNewsList(country: String): List<News>
    suspend fun getNews(title: String): News
}
