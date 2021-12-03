package com.codingwithmitch.food2forkcompose.datastore

import android.content.Context
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile

abstract class DataStoreProvider(
    context: Context,
    fileName: String = DEFAULT_SETTINGS_FILE_NAME
) {
    private val appContext = context.applicationContext

    protected val dataStore by lazy {
        PreferenceDataStoreFactory.create(
            produceFile = { appContext.preferencesDataStoreFile(fileName) },
            migrations = listOf(
                SharedPreferencesMigration(appContext, fileName)
            )
        )
    }

    companion object {
        const val DEFAULT_SETTINGS_FILE_NAME = "settings"
    }
}