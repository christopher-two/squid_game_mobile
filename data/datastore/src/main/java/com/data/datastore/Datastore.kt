package com.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class Datastore(
    private val context: Context
) {
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    companion object {
        const val NUMBER_CLASS: String = "numberClass"
    }

    fun getSession(): Flow<String> {
        val key = stringPreferencesKey(NUMBER_CLASS)
        return context.dataStore.data.map { it[key] ?: "" }
    }

    suspend fun setSession(number: String) {
        val key = stringPreferencesKey(NUMBER_CLASS)
        context.dataStore.edit { it[key] = number }
    }
}