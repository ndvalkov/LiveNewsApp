package com.homeassignment.livenewsapp.data.repository

import com.homeassignment.livenewsapp.data.db.ArticleDao
import com.homeassignment.livenewsapp.data.db.ArticleEntity
import com.homeassignment.livenewsapp.data.db.FavoriteArticleDao
import com.homeassignment.livenewsapp.data.db.FavoriteArticleEntity
import javax.inject.Inject

class RoomRepository @Inject constructor(
    private val articleDao: ArticleDao,
    private val favoriteArticleDao: FavoriteArticleDao
)   {

    suspend fun insertAllArticles(articles: List<ArticleEntity>) {
        articleDao.insertAll(articles)
    }

    suspend fun getAllArticles(): List<ArticleEntity> {
        return articleDao.getAllArticles()
    }

    suspend fun deleteAllArticles() {
        articleDao.deleteAllArticles()
    }

    suspend fun insertFavoriteArticle(favoriteArticle: FavoriteArticleEntity) {
        favoriteArticleDao.insert(favoriteArticle)
    }

    suspend fun deleteFavoriteArticle(favoriteArticle: FavoriteArticleEntity) {
        favoriteArticleDao.delete(favoriteArticle)
    }

    suspend fun getAllFavoriteArticles(): List<FavoriteArticleEntity> {
        return favoriteArticleDao.getAllFavoriteArticles()
    }
}