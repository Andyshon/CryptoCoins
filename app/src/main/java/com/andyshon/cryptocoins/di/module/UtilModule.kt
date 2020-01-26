package com.andyshon.cryptocoins.di.module

import android.content.Context
import com.andyshon.cryptocoins.data.preference.PreferenceManager
import com.andyshon.cryptocoins.di.qualifier.ApplicationContext
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class UtilModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(
        @ApplicationContext context: Context,
        @Named("basic") gson: Gson
    ): PreferenceManager {
        return PreferenceManager(context, gson)
    }
}