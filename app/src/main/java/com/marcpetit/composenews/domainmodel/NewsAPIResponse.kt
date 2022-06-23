package com.marcpetit.composenews.domainmodel

data class NewsAPIResponse(
    val status: String? = null,
    val code: String? = null,
    val articles: List<News>? = null
)
