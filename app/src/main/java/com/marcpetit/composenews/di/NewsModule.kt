package com.marcpetit.composenews.di

import com.marcpetit.composenews.network.NewsAPI
import com.marcpetit.composenews.network.NewsRepository
import com.marcpetit.composenews.network.NewsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NewsModule {
    @Singleton
    @Provides
    fun provideNewsAPI(retrofit: Retrofit): NewsAPI = retrofit.create(NewsAPI::class.java)

    @Singleton
    @Provides
    fun provideNewsRepository(newsAPI: NewsAPI): NewsRepository = NewsRepositoryImpl(newsAPI)
}
