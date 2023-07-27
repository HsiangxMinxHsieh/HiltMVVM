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
 * <pre>
 *     author: dhl
 *     date  : 2020/10/2
 *     desc  :
 * </pre>
 */

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {

    @Provides
    @Singleton
    fun provideSharedPreferencesRepository(@ApplicationContext ctx: Context): SharedPreferencesRepository {
        return SharedPreferencesRepository(ctx)
    }

}