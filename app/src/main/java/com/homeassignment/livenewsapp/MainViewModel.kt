package com.homeassignment.livenewsapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.homeassignment.livenewsapp.data.DataStorage
import com.homeassignment.livenewsapp.data.db.Article
import com.homeassignment.livenewsapp.data.db.ArticleEntity
import com.homeassignment.livenewsapp.data.mappers.toArticle
import com.homeassignment.livenewsapp.data.repository.ArticlesRepository
import com.homeassignment.livenewsapp.data.repository.RoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

sealed class UiState {
    data object Loading : UiState()
    data object Update : UiState()
    data class Success(val data: String) : UiState()
    data class Error(val message: String) : UiState()
}

@HiltViewModel
class MainViewModel @Inject constructor(
    private val articlesRepository: ArticlesRepository,
    private val roomRepository: RoomRepository,
    private val dataStorage: DataStorage
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Success("Initial"))
    val uiState: StateFlow<UiState> = _uiState
//
//    private val _allArticles: MutableStateFlow<List<Article>> = MutableStateFlow(emptyList())
//    val allArticles: StateFlow<List<Article>> = _allArticles.asStateFlow()

    val articles = articlesRepository
        .getAllArticlesFromDb()
        .cachedIn(viewModelScope)

    init {
        // loadArticlesFromDb()
    }

    fun updateArticles() {
        viewModelScope.launch {
            try {
                val lastUpdateTime = dataStorage.getLastUpdate()
                val currentTime = System.currentTimeMillis()
                if (currentTime - lastUpdateTime > TimeUnit.HOURS.toMillis(1)) { // TimeUnit.HOURS.toMillis(1)
                    _uiState.value = UiState.Update

                    val newsResponse = articlesRepository.fetchTopHeadlines(BuildConfig.API_KEY)
                    if (newsResponse == null || newsResponse.status != "ok") {
                        val errorMessage = newsResponse?.message ?: "Failed to fetch articles"
                        _uiState.value = UiState.Error(errorMessage)
                        return@launch
                    }

                    dataStorage.saveLastUpdate(currentTime)

                    val articles = newsResponse.articles.map { it.toArticle() }
                    val articleEntities = articles.map { ArticleEntity(article = it) }
                    roomRepository.deleteAllArticles()
                    roomRepository.insertAllArticles(articleEntities)

                    // _allArticles.value = articles

                    _uiState.value = UiState.Success("Successfully updated")
                } else {
                    _uiState.value = UiState.Success("No updates needed")
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "An unknown error occurred")
            }
        }
    }

//    private fun loadArticlesFromDb() {
//        viewModelScope.launch {
//            val entities = roomRepository.getAllArticles()
//            val articles = entities.map { it.article }
//            _allArticles.value = articles
//        }
//    }
}