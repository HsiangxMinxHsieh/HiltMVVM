package com.timmy.roomlibs.database.tables.sample

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SampleEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val site: String = "",
    val county: String = "",
    val pm25: String = "",
    val datacreationdate: String = "",
    val itemunit: String = ""
)
