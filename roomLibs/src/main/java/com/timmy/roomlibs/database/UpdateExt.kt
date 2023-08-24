package com.timmy.roomlibs.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.timmymike.logtool.loge

object UpdateExt {

//
//    /**DB 範例版本號升級 20230811*/
//    val MIGRATION_1_2: Migration = object : Migration(1, 2) {
//        override fun migrate(db: SupportSQLiteDatabase) {
//            //DB 版本號升級
//            val map = mutableMapOf<String, String>()
//            map["id"] = "INTEGER"
//            map["name"] = "TEXT"
//            map["client_id"] = "TEXT"
//            map["updateTime"] = "INTEGER"
//            createTable(
//                db,
//                "PersonEntity",
//                map.keys.toTypedArray(), // Key 的 array
//                map.values.toTypedArray(), // Type 的 array
//                arrayOf("id") // PrimaryKey
//            )
//        }
//    }
//
//    /**DB 範例版本號升級 20230811 */
//    val MIGRATION_2_3: Migration = object : Migration(2, 3) {
//        override fun migrate(db: SupportSQLiteDatabase) {
//            //DB 版本號升級
//            deleteColumn(db, "SampleEntity", "client_id")
//        }
//    }
////
//    /**DB 範例版本號升級 20230811*/
//    val MIGRATION_3_4: Migration = object : Migration(3, 4) {
//        override fun migrate(db: SupportSQLiteDatabase) {
//            //DB 版本號升級
//            addColumn(db, "SampleEntity", "client_id", "TEXT")
//        }
//    }

    /**
     * 修改資料庫欄位的步驟：
     * 0.將Package table 內的 entity ， 依照想要的做修正 // 注意，若資料表有新增欄位，必須要可以為null。(先前的資料不存在)
     * 1.新增 MIGRATION 變數，並確定Migration(old,new) 的old和new是正確的
     * 2.將新增之 MIGRATION變數 加入至 availableMigration 中
     * 3.修改  [databaseVersion] 為 new Version
     * */

    val availableMigration = arrayOf<Migration>(/*MIGRATION_1_2, MIGRATION_2_3,  MIGRATION_3_4, MIGRATION_4_5*/)
    const val databaseVersion = 1

    /**
     * 新增資料表的步驟：
     * 0.在Package table 內，新增 entity、DAO
     * 1.在[com.timmy.roomlibs.database.AppDataBase]中，新增Dao、entities=[]的中括號內新增該類別。
     * 2.新增MIGRATION 變數，並確定Migration(old,new) 的old和new是正確的
     * 3.將新增之 MIGRATION變數 加入至 availableMigration 中
     * 4.修改 [databaseVersion] 為 new Version
     *
     * 後續使用：
     * 1.在[com.timmy.roomlibs.di.RoomModule]新增 provide Dao 方法
     * 2.在[com.timmy.roomlibs.repo]資料夾中，新增專用或者是其他Repo去引用此Dao的類別或方法
     * */

    /** 新增欄位 */
    private fun addColumn(database: SupportSQLiteDatabase, table: String, col: String, type: String = "TEXT") {
        database.execSQL("ALTER TABLE $table ADD COLUMN $col $type;")
    }

    /** 刪除欄位 */
    private fun deleteColumn(database: SupportSQLiteDatabase, table: String, col: String) {
        getColumnNamesAndTypes(database, table).let { columnsMap ->
            if (columnsMap.containsKey(col)) {
                val tempTableName = "temp_$table"
                val createTempTableStatement = buildCreateTableStatement(tempTableName, columnsMap, col)

                database.execSQL(createTempTableStatement)

                val columnNamesWithoutExcluded = columnsMap.keys.filter { it != col }
                val insertColumns = columnNamesWithoutExcluded.sortedByDescending { it }.joinToString(", ")
                database.execSQL("INSERT INTO $tempTableName ($insertColumns) SELECT $insertColumns FROM $table;")

                database.execSQL("DROP TABLE $table;")

                val createTableStatement = buildCreateTableStatement(table, columnsMap, col)
                database.execSQL(createTableStatement)

                database.execSQL("INSERT INTO $table ($insertColumns) SELECT $insertColumns FROM $tempTableName;")

                database.execSQL("DROP TABLE $tempTableName;")
            } else {
                loge("欄位 ${col} 於 資料表 ${table} 中不存在!")
            }
        }
    }

    private fun buildCreateTableStatement(tableName: String, columnsMap: Map<String, Triple<String, Boolean, Int>>, excludedColumn: String): String {
        val columnDefinitions = columnsMap.filterKeys { it != excludedColumn }.map { (name, triple) ->
            val (type, notNull, _) = triple
            "$name $type${if (notNull) " NOT NULL" else ""}"
        }
        return "CREATE TABLE $tableName (${columnDefinitions.joinToString(", ")},PRIMARY KEY (${columnsMap.filter { it.value.third != 0 }.keys.joinToString(", ")}));"
    }

    // 查詢指定表格的資訊，並返回一個包含欄位名稱和欄位類型的 Map。您可以在 deleteColumn 函式內使用這個 getColumnNamesAndTypes 函式來獲取要刪除的欄位的資訊，然後根據這些資訊進行適當的刪除操作。
    private fun getColumnNamesAndTypes(database: SupportSQLiteDatabase, tableName: String): MutableMap<String, Triple<String, Boolean, Int>> {

        val columnsMap = mutableMapOf<String, Triple<String, Boolean, Int>>()

        val cursor = database.query("PRAGMA table_info($tableName);")
        cursor.use { it ->
            while (it.moveToNext()) {
                val columnName = it.getString(1)
                val columnType = it.getString(2)
                val notNull = it.getInt(3) == 1 // 使用索引 3 獲取 not null 屬性
                val keyPosition = it.getInt(cursor.getColumnIndex("pk").takeIf { index -> index != -1 } ?: 0) // 使用索引 4 獲取 primaryKeyPosition 屬性
                columnsMap[columnName] = Triple(columnType, notNull, keyPosition)
            }
        }

        return columnsMap

    }


    /**
     * 新增 Table
     * 產生sql字串如下
     * CREATE TABLE IF NOT EXISTS ShoeMinuteRecord (
     *     date INTEGER NOT NULL DEFAULT 0,
     *     mac TEXT NOT NULL,
     *     steps TEXT NOT NULL,
     *     updateTime INTEGER NOT NULL DEFAULT 0,
     *     apiUpdateTime INTEGER NOT NULL DEFAULT 0,
     *     PRIMARY KEY (date, mac)
     * )
     * */
    private fun createTable(database: SupportSQLiteDatabase, table: String, col: Array<String>, type: Array<String>, pk: Array<String>) {
        var sql = ""
        sql += "CREATE TABLE IF NOT EXISTS $table ("
        for (i in col.indices) {
            sql += when {
                type[i] == "INTEGER" -> "${col[i]} INTEGER NOT NULL DEFAULT 0"
                type[i] == "REAL" -> "${col[i]} REAL NOT NULL DEFAULT 0"
                type[i] == "TEXT" -> "${col[i]} TEXT NOT NULL "
                else -> ""
            }
            if (i < col.size - 1) {
                sql += ","
            }
        }
        if (pk.isNotEmpty()) {
            sql += ",PRIMARY KEY ("
            for (i in pk.indices) {
                sql += pk[i]
                if (i < pk.size - 1) {
                    sql += ","
                }
            }
            sql += ")"
        }
        sql += ")"
//            loge("TAG", "sql = $sql")
        database.execSQL(sql)
    }
}