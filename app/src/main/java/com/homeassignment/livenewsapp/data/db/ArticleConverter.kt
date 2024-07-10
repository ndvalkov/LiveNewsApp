package com.homeassignment.livenewsapp.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ArticleConverter {
    @TypeConverter
    fun fromArticle(article: Article): String {
        return Gson().toJson(article)
    }

    @TypeConverter
    fun toArticle(articleJson: String): Article {
        val type = object : TypeToken<Article>() {}.type
        return Gson().fromJson(articleJson, type)
    }
}