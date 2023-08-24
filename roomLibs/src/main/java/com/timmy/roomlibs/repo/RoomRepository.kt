package com.timmy.roomlibs.repo

import com.timmy.base.data.Record
import com.timmy.roomlibs.database.tables.sample.SampleDao
import com.timmy.roomlibs.database.tables.sample.SampleEntity
import com.timmy.roomlibs.database.tables.sample.insert
import com.timmy.roomlibs.database.tables.testdata.PersonDao
import com.timmy.roomlibs.database.tables.testdata.PersonEntity
import javax.inject.Inject

/**
 *     author: Timmy
 *     date  : 2023/08/09
 *     desc  : 同步的取值方法
 */
class RoomRepository @Inject constructor(
    private val personDao: PersonDao,
    private val sampleDao: SampleDao
) {
    fun insertPersonData(name: String) {
        val person = PersonEntity(
            name = "$name ${(0..100).random()}",
            updateTime = System.currentTimeMillis()
        )
        personDao.insert(person)
    }

    fun insertSampleDataListByRecord(dataList: List<Record>) {
        sampleDao.insert(dataList)
    }

    fun insertSampleDataListBySample(dataList: List<SampleEntity>) {
        sampleDao.insert(dataList)
    }

    fun getSampleDataList() = sampleDao.allData

    fun getPersonDataList() = personDao.allData


}