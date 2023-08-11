package com.timmy.roomlibs.database.tables.testdata

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 *
 *     author: Timmy
 *     date  : 2023/08/11
 *     desc  : 測試用DAO
 *
 */

@Dao
interface PersonDao {
    @get:Query("SELECT * FROM PersonEntity ")
    val allData: List<PersonEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(personEntity: PersonEntity)

}