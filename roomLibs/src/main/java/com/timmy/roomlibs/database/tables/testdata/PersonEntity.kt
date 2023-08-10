package com.timmy.roomlibs.database.tables.testdata

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * <pre>
 *     author: dhl
 *     date  : 2020/6/30
 *     desc  :
 * </pre>
 */

@Entity
class PersonEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val client_id: String = "",
    val updateTime: Long = System.currentTimeMillis()
)
