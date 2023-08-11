package com.timmy.roomlibs.repo

import com.timmy.roomlibs.database.tables.sample.SampleDao
import com.timmy.roomlibs.database.tables.sample.SampleData
import com.timmy.roomlibs.database.tables.testdata.PersonDao
import com.timmy.roomlibs.database.tables.testdata.PersonEntity
import javax.inject.Inject

class RoomRepo @Inject constructor(
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

    fun insertSampleDataList(dataList: List<SampleData>) {
        sampleDao.insert(dataList)
    }

    fun getSampleDataList() = sampleDao.allData

    fun getPersonDataList() = personDao.allData


}