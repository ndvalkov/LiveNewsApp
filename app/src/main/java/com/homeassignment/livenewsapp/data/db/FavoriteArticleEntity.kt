package com.homeassignment.livenewsapp.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_articles")
data class FavoriteArticleEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val article: Article
)