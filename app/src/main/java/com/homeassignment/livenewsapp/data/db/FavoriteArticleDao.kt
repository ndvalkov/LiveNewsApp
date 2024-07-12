package com.homeassignment.livenewsapp.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favoriteArticle: FavoriteArticleEntity)

    @Delete
    suspend fun delete(favoriteArticle: FavoriteArticleEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(favorites: List<FavoriteArticleEntity>)

    @Query("DELETE FROM favorite_articles")
    suspend fun deleteAll()

    @Query("SELECT * FROM favorite_articles")
    suspend fun getAllFavoriteArticles(): List<FavoriteArticleEntity>
}