package com.timmy.roomlibs.database.tables.sample

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SampleData")
data class SampleData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val item1: String = "",
    val item2: String = "",
    val value1: String = "",
    val value2: String = "",
    val value3: String = "",
    val value4: String = "",
    val value5: String = "",
    val value6: String = "",
    val value7: String = ""
)
