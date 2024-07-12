package com.homeassignment.livenewsapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.compose.LazyPagingItems
import com.homeassignment.livenewsapp.data.DataStorage
import com.homeassignment.livenewsapp.data.db.ArticleEntity
import com.homeassignment.livenewsapp.data.db.FavoriteArticleEntity
import com.homeassignment.livenewsapp.data.mappers.toArticle
import com.homeassignment.livenewsapp.data.remote.NewsResponseDto
import com.homeassignment.livenewsapp.data.repository.ArticlesRepository
import com.homeassignment.livenewsapp.data.repository.RoomRepository
import com.homeassignment.livenewsapp.ui.search.SearchAction
import com.homeassignment.livenewsapp.ui.sort.SortAction
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

    private val _favorites: MutableStateFlow<List<FavoriteArticleEntity>> = MutableStateFlow(emptyList())
    val favorites: StateFlow<List<FavoriteArticleEntity>> = _favorites.asStateFlow()

    private val _sorted = MutableStateFlow(false)
    val sorted: StateFlow<Boolean> = _sorted.asStateFlow()

    val articles = articlesRepository
        .getAllArticlesFromDb()
        .cachedIn(viewModelScope)

    val sortedArticles = articlesRepository
        .getSortedArticlesFromDb()
        .cachedIn(viewModelScope)

    init {
        getFavoritesFromDb()
    }

    override fun onCleared() {
        super.onCleared()
    }

    fun updateArticles() {
        viewModelScope.launch {
            try {
                val lastUpdateTime = dataStorage.getLastUpdate()
                val currentTime = System.currentTimeMillis()
                if (currentTime - lastUpdateTime > TimeUnit.HOURS.toMillis(1)) {
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

    fun toggleFavorite(title: String, articles: LazyPagingItems<ArticleEntity>) {
        val toggled = findArticleByTitle(title, articles)
        toggled?.let { articleEntity ->
            val currentFavorites = _favorites.value.toMutableList()
            val element = currentFavorites.find { it.article.title == title }
            if (element != null) {
                currentFavorites.remove(element)
                viewModelScope.launch {
                    roomRepository.deleteFavorite(element)
                }
            } else {
                val favoriteArticleEntity = FavoriteArticleEntity(article = articleEntity.article)
                currentFavorites.add(favoriteArticleEntity)
                viewModelScope.launch {
                    roomRepository.insertFavorite(favoriteArticleEntity)
                }
            }

            _favorites.value = currentFavorites
        }
    }

    fun onSortArticles(sortAction: SortAction) {
        _sorted.value = sortAction == SortAction.DATE
    }

    // Retrieve the entity from the currently collected paged items
    private fun findArticleByTitle(title: String, articles: LazyPagingItems<ArticleEntity>): ArticleEntity? {
        for (index in 0 until articles.itemCount) {
            val articleEntity = articles[index]
            if (articleEntity?.article?.title == title) {
                return articleEntity
            }
        }
        return null
    }

    private fun getFavoritesFromDb() {
        viewModelScope.launch {
            val favorites = roomRepository.getAllFavoriteArticles()
            _favorites.value = favorites
        }
    }

    fun onSearchArticles(searchAction: SearchAction, query: String) {
        if (query.trim().isEmpty()) {
            return
        }

        viewModelScope.launch {
            try {
                val newsResponse: NewsResponseDto? = when (searchAction) {
                    SearchAction.EXACT_MATCH -> {
                        articlesRepository.searchTopHeadlines(query, BuildConfig.API_KEY)
                    }

                    SearchAction.BY_RELEVANCY -> {
                        articlesRepository.searchTopHeadlinesWithRelevancy(
                            query,
                            BuildConfig.API_KEY
                        )
                    }
                }

                if (newsResponse == null || newsResponse.status != "ok") {
                    val errorMessage = newsResponse?.message ?: "Failed to search articles"
                    _uiState.value = UiState.Error(errorMessage)
                    return@launch
                }

                val articles = newsResponse.articles.map { it.toArticle() }
                val articleEntities = articles.map { ArticleEntity(article = it) }
                roomRepository.deleteAllArticles()
                roomRepository.insertAllArticles(articleEntities)

                // _allArticles.value = articles

                _uiState.value = UiState.Success("Successfully searched")
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "An unknown error occurred")
            }
        }
    }

//    fun saveFavorites() {
//        viewModelScope.launch(Dispatchers.IO) {
//            try {
//                roomRepository.deleteAllFavorites()
//                roomRepository.insertAllFavorites(favorites = _favorites.value)
//                println("Insert count: ${_favorites.value.size}")
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }
//    }
}