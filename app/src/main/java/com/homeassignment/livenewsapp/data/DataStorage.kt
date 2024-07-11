package com.homeassignment.livenewsapp.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

class DataStorage(private val context: Context) {

    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("dataStore")
        val LAST_UPDATE = longPreferencesKey("LAST_UPDATE")
    }

    suspend fun getLastUpdate(): Long {
        val prefs = context.dataStore.data.first()
        return prefs[LAST_UPDATE] ?: 0L
    }

    suspend fun saveLastUpdate(timestamp: Long) {
        context.dataStore.edit { prefs ->
            prefs[LAST_UPDATE] = timestamp
        }
    }
}