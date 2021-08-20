package org.matamem.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.matamem.DataPreferencesStore
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DataPreferences {

    @Provides
    @Singleton
    fun getSharedPreferences(application: Application) : DataPreferencesStore{
            return DataPreferencesStore(application)
    }

}