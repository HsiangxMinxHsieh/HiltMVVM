package com.timmy.roomlibs.database.tables.testdata

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.timmymike.timetool.toDate
import com.timmymike.timetool.toString

/**
 *     author: Timmy
 *     date  : 2023/08/11
 *     desc  : 測試用資料Entity
 */

@Entity
data class PersonEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
//    val client_id: String? = "",
    val updateTime: Long = System.currentTimeMillis()
) {
    override fun toString(): String {
        return "PersonEntity(id=$id, name=$name, updateTime=${updateTime.toDate().toString("yyyy/MM/dd HH:mm:ss")}"
    }
}
