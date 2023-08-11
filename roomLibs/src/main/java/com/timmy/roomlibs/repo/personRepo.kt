package com.timmy.roomlibs.repo

import com.timmy.roomlibs.database.tables.testdata.PersonDao
import com.timmy.roomlibs.database.tables.testdata.PersonEntity
import javax.inject.Inject

class PersonRepo @Inject constructor(
    private val personDao: PersonDao,
) {
    fun insertPersonData(name: String) {
        val person = PersonEntity(
            name = "$name ${(0..100).random()}",
            updateTime = System.currentTimeMillis()
        )
        personDao.insert(person)
    }

    fun getPersonDataList() = personDao.allData

}