package com.marcpetit.composenews.utils

import com.marcpetit.composenews.di.NewsModule
import com.marcpetit.composenews.domainmodel.News
import com.marcpetit.composenews.network.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NewsModule::class]
)
class MockNewsModule {
    @Provides
    @Singleton
    fun provideNewsRepository(): NewsRepository =
        object : NewsRepository {

            val news = listOf(
                News(
                    "foo",
                    "foo1",
                    "foo2",
                    "foo3",
                    "foo4",
                ),
                News(
                    "bar",
                    "bar1",
                    "bar2",
                    "bar3",
                    "bar4",
                )
            )
            override suspend fun getNewsList(country: String): List<News> = news
            override suspend fun getNews(title: String): News = news[0]
        }
}
