package com.homeassignment.livenewsapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    // Get All (sorted by popularity)
    // https://newsapi.org/v2/top-headlines?country=us&sortBy=popularity&apiKey=API_KEY
    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String = DEFAULT_COUNTRY,
        @Query("sortBy") sortBy: String,
        @Query("apiKey") apiKey: String
    ): NewsResponseDto

    // Search by keyword
    // https://newsapi.org/v2/top-headlines?country=us&q=keyword&apiKey=API_KEY
    @GET("top-headlines")
    suspend fun searchTopHeadlines(
        @Query("country") country: String = DEFAULT_COUNTRY,
        @Query("q") keyword: String,
        @Query("apiKey") apiKey: String
    ): NewsResponseDto


    // Search by relevancy to keyword
    // https://newsapi.org/v2/top-headlines?country=us&q=keyword&sortBy=relevancy&apiKey=API_KEY
    @GET("top-headlines")
    suspend fun searchTopHeadlinesWithRelevancy(
        @Query("country") country: String = DEFAULT_COUNTRY,
        @Query("q") keyword: String,
        @Query("sortBy") sortBy: String,
        @Query("apiKey") apiKey: String
    ): NewsResponseDto

    companion object {
        const val BASE_URL = "https://newsapi.org/v2/"
        const val DEFAULT_COUNTRY = "us"
    }
}