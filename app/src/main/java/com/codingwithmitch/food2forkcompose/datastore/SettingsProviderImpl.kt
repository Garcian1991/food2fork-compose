package com.codingwithmitch.food2forkcompose.datastore

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class SettingsProviderImpl(
    context: Context
) : DataStoreProvider(context = context), SettingsProvider {

    override val isDarkTheme = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[KEY_IS_DARK_THEME] ?: false
        }

    override suspend fun switchToDarkTheme(isDarkTheme: Boolean) {
        dataStore.edit { preferences ->
            preferences[KEY_IS_DARK_THEME] = isDarkTheme
        }
    }

    companion object {
        private const val IS_DARK_THEME = "isDarkTheme"

        private val KEY_IS_DARK_THEME = booleanPreferencesKey(IS_DARK_THEME)
    }
}