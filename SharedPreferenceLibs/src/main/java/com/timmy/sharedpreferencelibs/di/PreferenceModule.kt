package com.timmy.sharedpreferencelibs.di

import android.content.Context
import com.timmy.sharedpreferencelibs.repo.SharedPreferencesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 *
 *     author: Timmy
 *     date  : 2023/08/11
 *     desc  : 注入 SharedPreferencesRepository 的 Module
 *
 */

@Module
@InstallIn(SingletonComponent::class)
class PreferenceModule {

    @Provides
    @Singleton
    fun provideSharedPreferencesRepository(@ApplicationContext ctx: Context): SharedPreferencesRepository {
        return SharedPreferencesRepository(ctx)
    }

}