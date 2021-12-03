package com.codingwithmitch.food2forkcompose.datastore

import kotlinx.coroutines.flow.Flow

interface SettingsProvider {
    val isDarkTheme: Flow<Boolean>
    suspend fun switchToDarkTheme(isDarkTheme: Boolean)
}