package com.timmy.roomlibs.database.tables.sample

import androidx.room.*
import com.timmy.base.data.Record

/**
 *     author: Timmy
 *     date  : 2023/08/11
 *     desc  : 示範用DAO
 */

@Dao
interface SampleDao {


    @get:Query("SELECT * FROM SampleEntity ")
    val allData: List<SampleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(sample: SampleEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(sampleList: List<SampleEntity>)

    @Delete
    fun delete(sample: SampleEntity)

    @Query("DELETE FROM SampleEntity")
    fun deleteAll()

}

// 傳統作法：將傳入的列表的內容一個一個轉為目標Entity
fun SampleDao.insert(recordList: List<Record>) {
    this.insert(mutableListOf<SampleEntity>().apply {
        recordList.forEach {
            add(
                SampleEntity(
                    site = it.site,
                    county = it.county,
                    pm25 = it.pm25,
                    datacreationdate = it.datacreationdate,
                    itemunit = it.itemunit,
                )
            )
        }
    })
}