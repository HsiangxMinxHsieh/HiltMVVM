package com.timmy.datastorelibs.di

import android.app.Application
import com.timmy.datastorelibs.repo.DataStoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun provideDataStoreRepository( ctx: Application): DataStoreRepository {
        return DataStoreRepository(ctx)
    }
}