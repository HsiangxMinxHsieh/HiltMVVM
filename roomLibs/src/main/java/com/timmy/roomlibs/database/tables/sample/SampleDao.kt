package com.timmy.roomlibs.database.tables.sample

import androidx.room.*

/**
 * <pre>
 *     author: dhl
 *     date  : 2020/6/30
 *     desc  :
 * </pre>
 */

@Dao
interface SampleDao {


    @get:Query("SELECT * FROM SampleData ")
    val allData: List<SampleData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(sample: SampleData)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(sampleList: List<SampleData>)

    @Delete
    fun delete(sample: SampleData)

    @Query("DELETE FROM SampleData")
    fun deleteAll()

}