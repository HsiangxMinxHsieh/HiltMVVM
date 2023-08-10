package com.timmy.roomlibs.database.tables.testdata

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

/**
 * <pre>
 *     author: dhl
 *     date  : 2020/6/30
 *     desc  :
 * </pre>
 */

@Dao
interface PersonDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(personEntity: PersonEntity)

}