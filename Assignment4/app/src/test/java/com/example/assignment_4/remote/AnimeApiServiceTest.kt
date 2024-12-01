package com.example.assignment_4.remote

import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AnimeApiServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var apiService: AnimeApiService

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        apiService = Retrofit.Builder().baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(AnimeApiService::class.java)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `getTopAnime should return a list of top anime`() = runTest {
        // Given
        val mockResponse =
            MockResponse().setResponseCode(200).setBody(MockResponses.topAnimeResponse)
        mockWebServer.enqueue(mockResponse)

        // When
        val response = apiService.getTopAnime()

        // Then
        val request = mockWebServer.takeRequest()
        Assert.assertEquals("Request path should be /top/anime", "/top/anime", request.path)
        Assert.assertNotNull("Response should not be null", response)
        Assert.assertFalse("Response data should not be empty", response.data.isEmpty())
        Assert.assertEquals(
            "First anime title should be 'Cowboy Bebop'", "Cowboy Bebop", response.data[0].title
        )
    }

    @Test
    fun `getAnimeDetails should return anime details`() = runTest {
        // Given
        val animeId = 1
        val mockResponse =
            MockResponse().setResponseCode(200).setBody(MockResponses.animeDetailResponse)
        mockWebServer.enqueue(mockResponse)

        // When
        val response = apiService.getAnimeDetails(animeId)

        // Then
        val request = mockWebServer.takeRequest()
        Assert.assertEquals("Request path should be /anime/1", "/anime/$animeId", request.path)
        Assert.assertNotNull("Response should not be null", response)
        Assert.assertEquals(
            "Anime title should be 'Cowboy Bebop'", "Cowboy Bebop", response.data.title
        )
    }
}