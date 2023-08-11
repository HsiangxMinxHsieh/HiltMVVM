package com.timmy.roomlibs.di

import android.app.Application
import androidx.room.Room
import com.timmy.roomlibs.database.AppDataBase
import com.timmy.roomlibs.database.tables.sample.SampleDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * <pre>
 *     author: dhl
 *     date  : 2020/6/30
 *     desc  :
 * </pre>
 */

@Module
@InstallIn(SingletonComponent::class)
// 这里使用了 SingletonComponent，因此 RoomModule 會绑定到 Application 的生命周期內。
object RoomModule {

    /**
     * @Provides 常用于被 @Module 注解标记类的内部的方法，并提供依赖项对象。
     * @Singleton 提供单例
     */
    @Provides
    @Singleton
    fun provideAppDataBase(application: Application): AppDataBase {
        return Room
            .databaseBuilder(application, AppDataBase::class.java, "hiltMvvm.db")
//            .addMigrations(*UpdateExt.availableMigration) // 容許已上線的資料庫的更新 // 注意，若資料表有新增欄位，必須要可以為null。
            .fallbackToDestructiveMigration()// 破壞性更新(更新db版本號 會把資料庫都清空重建) 如果要自己 addMigrations 的話不用這行
            .allowMainThreadQueries()
            .build()
    }

//    @Provides
//    @Singleton
//    fun providePersonDao(appDatabase: AppDataBase): PersonDao {
//        return appDatabase.personDao()
//    }

    @Provides
    @Singleton
    fun provideSampleDao(appDatabase: AppDataBase): SampleDao {
        return appDatabase.sampleDao()
    }

}
