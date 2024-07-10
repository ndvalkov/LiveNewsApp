package com.homeassignment.livenewsapp.data.repository

import com.homeassignment.livenewsapp.data.remote.NewsApi
import com.homeassignment.livenewsapp.data.remote.NewsResponseDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ArticlesRepository @Inject constructor(
    private val newsApi: NewsApi
) {

    suspend fun getTopHeadlines(apiKey: String): NewsResponseDto? {
        return withContext(Dispatchers.IO) {
            try {
                newsApi.getTopHeadlines(sortBy = "popularity", apiKey = apiKey)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    suspend fun searchTopHeadlines(keyword: String, apiKey: String): NewsResponseDto? {
        return withContext(Dispatchers.IO) {
            try {
                newsApi.searchTopHeadlines(keyword = keyword, apiKey = apiKey)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    suspend fun searchTopHeadlinesWithRelevancy(keyword: String, apiKey: String): NewsResponseDto? {
        return withContext(Dispatchers.IO) {
            try {
                newsApi.searchTopHeadlinesWithRelevancy(keyword = keyword, sortBy = "relevancy", apiKey = apiKey)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

}