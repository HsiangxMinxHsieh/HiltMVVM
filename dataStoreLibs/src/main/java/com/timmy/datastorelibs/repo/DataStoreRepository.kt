package com.timmy.datastorelibs.repo

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

/**
 * 非同步的取值方法
 */
class DataStoreRepository @Inject constructor(private val context: Application) {

    private val PREFERENCE_NAME = "DataStore"

    private val Context._dataStore: DataStore<Preferences> by preferencesDataStore(
        name = PREFERENCE_NAME,
    )

    val dataStore: DataStore<Preferences> = context._dataStore


    fun readData(): Flow<Any> =
        dataStore.data
            .catch {
                // 当读取数据遇到错误时，如果是 `IOException` 异常，发送一个 emptyPreferences，来重新使用
                // 但是如果是其他的异常，最好将它抛出去，不要隐藏问题
                if (it is IOException) {
                    it.printStackTrace()
                    emit(emptyPreferences())
                } else {
                    throw it
                }
            }.map { preferences ->
                preferences[PreferencesKeys.loginResponseKey] ?: false
            }

}

object PreferencesKeys {
    val loginResponseKey = stringPreferencesKey("login_response_key")
}