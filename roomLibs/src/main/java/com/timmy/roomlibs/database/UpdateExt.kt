package com.timmy.roomlibs.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.timmymike.logtool.loge
import com.timmymike.logtool.toJsonAndLoge

object UpdateExt {
    /**DB 範例版本號升級 20230810*/
    val MIGRATION_3_4: Migration = object : Migration(3, 4) {
        override fun migrate(db: SupportSQLiteDatabase) {
            //DB 版本號升級
            val map = mutableMapOf<String, String>()
            map["id"] = "INTEGER"
            map["name"] = "TEXT"
            map["updateTime"] = "INTEGER"
            createTable(
                db,
                "PersonEntity",
                map.keys.toTypedArray(), // Key 的 array
                map.values.toTypedArray(), // Type 的 array
                arrayOf("id") // PrimaryKey
            )
        }
    }


    /**DB 範例版本號升級 20230810*/
    val MIGRATION_2_3: Migration = object : Migration(2, 3) {
        override fun migrate(db: SupportSQLiteDatabase) {
            //DB 版本號升級
            deleteColumn(db, "PersonEntity", "client_id", arrayOf("id"))
        }
    }

//    /**DB 範例版本號升級 20230810 */
//    val MIGRATION_1_2: Migration = object : Migration(1, 2) {
//        override fun migrate(db: SupportSQLiteDatabase) {
//            //DB 版本號升級
//            addColumn(db, "PersonEntity", "client_id", "TEXT")
//        }
//    }

    /**DB 範例版本號升級 20230810 */
    val MIGRATION_1_2: Migration = object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            //DB 版本號升級
//            getColumnNamesAndTypes(db, "SampleData")
            deleteColumn(db, "SampleData", "id", arrayOf("item1", "item2"))
        }
    }


    /** 新增欄位 */
    private fun addColumn(database: SupportSQLiteDatabase, table: String, col: String, type: String = "TEXT") {
        database.execSQL("ALTER TABLE $table ADD COLUMN $col $type;")
    }

    /** 刪除欄位 */
    private fun deleteColumn(database: SupportSQLiteDatabase, table: String, col: String) {
        database.execSQL("ALTER TABLE $table DROP COLUMN $col;")
    }

    /** 刪除欄位 */
    private fun deleteColumn(database: SupportSQLiteDatabase, tableName: String, columnName: String, newPrimaryKey: Array<String>) {
        val columnsMap = getColumnNamesAndTypes(database, tableName)

        if (columnsMap.containsKey(columnName)) {
            val tempTableName = "temp_$tableName"
            val createTempTableStatement = buildCreateTableStatement(tempTableName, columnsMap, columnName, newPrimaryKey)

            // 創建臨時表
            database.execSQL(createTempTableStatement)

            // 複製資料到臨時表，排除要刪除的欄位
            val columnNamesWithoutExcluded = columnsMap.keys.filter { it != columnName }
            val insertColumns = columnNamesWithoutExcluded.sortedByDescending { it }.joinToString(", ")
            database.execSQL("INSERT INTO $tempTableName ($insertColumns) SELECT $insertColumns FROM $tableName;")

            // 刪除原始表
            database.execSQL("DROP TABLE $tableName;")

            // 重新建立原始表
            val createTableStatement = buildCreateTableStatement(tableName, columnsMap, columnName, newPrimaryKey)
            database.execSQL(createTableStatement)

            // 複製資料回原始表
//            val insertColumnsOriginal = columnsMap.keys.joinToString(", ")
            database.execSQL("INSERT INTO $tableName ($insertColumns) SELECT $insertColumns FROM $tempTableName;")

            // 刪除臨時表
            database.execSQL("DROP TABLE $tempTableName;")
        } else {
            // 如果欄位不存在，可能需要處理錯誤或提供其他處理邏輯
        }
    }

    private fun buildCreateTableStatement(tableName: String, columnsMap: Map<String, Triple<String, Boolean, Int>>, excludedColumn: String, newPrimaryKey: Array<String>): String {
        val columnDefinitions = columnsMap.filterKeys { it != excludedColumn }.map { (name, pair) ->
            val (type, notNull, keyPos) = pair
            "$name $type${if (notNull) " NOT NULL" else ""}"
        }
        return "CREATE TABLE $tableName (${columnDefinitions.joinToString(", ")}, PRIMARY KEY (${newPrimaryKey.joinToString(",")}));".apply { loge("即將執行Create的statement=>$this") }

    }

    // 查詢指定表格的資訊，並返回一個包含欄位名稱和欄位類型的 Map。您可以在 deleteColumn 函式內使用這個 getColumnNamesAndTypes 函式來獲取要刪除的欄位的資訊，然後根據這些資訊進行適當的刪除操作。
    private fun getColumnNamesAndTypes(database: SupportSQLiteDatabase, tableName: String): MutableMap<String, Triple<String, Boolean, Int>> {

        val columnsMap = mutableMapOf<String, Triple<String, Boolean, Int>>()

        val cursor = database.query("PRAGMA table_info($tableName);")
        cursor.use {
            while (it.moveToNext()) {
                val columnName = it.getString(1)
                val columnType = it.getString(2)
                val notNull = it.getInt(3) == 1 // 使用索引 3 獲取 not null 屬性
                val keyPosition = it.getInt(5) // 使用索引 4 獲取 primaryKeyPosition 屬性
                columnsMap[columnName] = Triple(columnType, notNull, keyPosition)

            }
        }

        return columnsMap.apply {
            this.toJsonAndLoge("key,value->")
//            logeKeyAll("testKey->")
//            logeValueAll("testValue->")
        }

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