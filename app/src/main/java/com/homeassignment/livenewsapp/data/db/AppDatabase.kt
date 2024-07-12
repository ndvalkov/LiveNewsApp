package com.homeassignment.livenewsapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [ArticleEntity::class, FavoriteArticleEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(ArticleConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun articlesDao(): ArticleDao
    abstract fun favoritesDao(): FavoriteArticleDao
}