package com.marcpetit.composenews

import com.marcpetit.composenews.network.ApikeyInvalidException
import com.marcpetit.composenews.network.MissingApiKeyException
import com.marcpetit.composenews.network.NewsAPI
import com.marcpetit.composenews.network.NewsRepositoryImpl
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Assert.assertThrows
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.StandardCharsets

class NewsRepositoryTest {
    private val mockWebServer = MockWebServer()

    private val newsProvider = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .client(OkHttpClient.Builder().build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(NewsAPI::class.java)

    private val newsRepository = NewsRepositoryImpl(newsProvider)

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `Top headlines response is correct`() {
        mockWebServer.enqueueResponse("top_headlines.json")
        runBlocking {
            val articles = newsRepository.getNewsList("US")
            assertEquals(20, articles.size)
            assertEquals("Alexander Osipovich, Anna Hirtenstein, Dave Sebastian", articles[0].author)
            assertEquals("Holly Ellyatt", articles[1].author)
        }
    }

    @Test
    fun `Api key missing exception`() {
        mockWebServer.enqueueResponse("api_key_missing.json")
        assertThrows(MissingApiKeyException::class.java) {
            runBlocking {
                newsRepository.getNewsList("US")
            }
        }
    }

    @Test
    fun `Api key invalid exception`() {
        mockWebServer.enqueueResponse("api_key_invalid.json")
        assertThrows(ApikeyInvalidException::class.java) {
            runBlocking {
                newsRepository.getNewsList("US")
            }
        }
    }
}

fun MockWebServer.enqueueResponse(filePath: String) {
    val inputStream = javaClass.classLoader?.getResourceAsStream(filePath)
    val source = inputStream?.source()?.buffer()
    source?.let {
        enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(it.readString(StandardCharsets.UTF_8))
        )
    }
}
