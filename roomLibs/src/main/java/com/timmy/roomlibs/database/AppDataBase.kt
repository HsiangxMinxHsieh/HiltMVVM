package com.timmy.roomlibs.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.timmy.roomlibs.database.tables.sample.SampleDao
import com.timmy.roomlibs.database.tables.sample.SampleData

/**
 * <pre>
 *     author: dhl
 *     date  : 2020/6/30
 *     desc  :
 * </pre>
 */

@Database(
    entities = [SampleData::class],
    version = UpdateExt.databaseVersion, exportSchema = false
)
abstract class AppDataBase : RoomDatabase() {

    //    abstract fun personDao(): PersonDao
    abstract fun sampleDao(): SampleDao
}
