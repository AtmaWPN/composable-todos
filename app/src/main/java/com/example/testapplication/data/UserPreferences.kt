package com.example.testapplication.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "settings")

private val HOME_LIST_ID_KEY = intPreferencesKey("home_list_id_key")

fun getHomeTodoList(context: Context): Flow<Int> = context.dataStore.data
    .map { settings ->
        // No type safety.
        settings[HOME_LIST_ID_KEY] ?: -1
    }

suspend fun setHomeList(newListId: Int, context: Context) {
    context.dataStore.edit { settings ->
        settings[HOME_LIST_ID_KEY] = newListId
    }
}
