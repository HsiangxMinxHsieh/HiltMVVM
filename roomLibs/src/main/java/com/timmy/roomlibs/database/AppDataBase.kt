package com.timmy.roomlibs.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.timmy.roomlibs.database.tables.sample.SampleDao
import com.timmy.roomlibs.database.tables.sample.SampleEntity
import com.timmy.roomlibs.database.tables.testdata.PersonDao
import com.timmy.roomlibs.database.tables.testdata.PersonEntity

/**
 *     author: Timmy
 *     date  : 2023/08/11
 *     desc  : AppDataBase，宣告為注入的實體
 *
 */

@Database(
    entities = [PersonEntity::class, SampleEntity::class],
    version = UpdateExt.databaseVersion, exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {

    abstract fun personDao(): PersonDao
    abstract fun sampleDao(): SampleDao
}
