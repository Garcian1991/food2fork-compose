package com.codingwithmitch.food2forkcompose.di

import android.content.Context
import com.codingwithmitch.food2forkcompose.datastore.SettingsProvider
import com.codingwithmitch.food2forkcompose.datastore.SettingsProviderImpl
import com.codingwithmitch.food2forkcompose.presentation.BaseApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): BaseApplication {
        return app as BaseApplication
    }

    @Singleton
    @Provides
    fun provideSettingsProvider(@ApplicationContext app: Context): SettingsProvider {
        return SettingsProviderImpl(app)
    }

}