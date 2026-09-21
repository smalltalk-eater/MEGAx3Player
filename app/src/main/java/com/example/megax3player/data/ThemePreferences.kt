package com.example.megax3player.data

import android.content.Context
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(
    name = "user_settings"
)

class ThemePreferences(
    private val context: Context
) {

    private companion object {
        val DARK_THEME = booleanPreferencesKey(
            name = "dark_theme"
        )
    }

    val darkTheme: Flow<Boolean?> =
        context.dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[DARK_THEME]
            }

    suspend fun saveDarkTheme(
        darkTheme: Boolean
    ) {
        context.dataStore.edit { preferences ->
            preferences[DARK_THEME] = darkTheme
        }
    }
}