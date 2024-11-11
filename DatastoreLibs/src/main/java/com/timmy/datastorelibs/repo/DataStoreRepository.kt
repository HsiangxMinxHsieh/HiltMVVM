package com.timmy.datastorelibs.repo

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.timmy.base.data.SampleDataFromAPI
import com.timmymike.logtool.toDataBean
import com.timmymike.logtool.toJson
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import java.io.IOException
import javax.inject.Inject

/**
 * 非同步或同步皆可的取值方法
 */

class DataStoreRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val Context._dataStore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE_NAME)

    val dataStore: DataStore<Preferences> = context._dataStore

    private suspend fun <T> saveData(key: Preferences.Key<T>, value: T) {
        dataStore.edit { mutablePreferences ->
            mutablePreferences[key] = value
        }
    }

    inline fun <reified T> readData(key: Preferences.Key<T>, defaultValue: T = T::class.java.newInstance()): Flow<T> =
        dataStore.data
            .catch { cause ->
                if (cause is IOException) {
                    cause.printStackTrace()
                    emit(emptyPreferences())
                } else {
                    throw cause
                }
            }
            .map { preferences ->
                preferences[key] ?: defaultValue
            }

    companion object {
        const val DATASTORE_NAME = "DataStore"

        // Key總覽

        val sampleStringKey = stringPreferencesKey("user_info_response_Key")
        val sampleDataKey = stringPreferencesKey("Sample_Data_Response_Key")
        val sampleLongKey = longPreferencesKey("Sample_Long_Key")
        val sampleIntKey = intPreferencesKey("Sample_Int_Key")
        val sampleBooleanKey = booleanPreferencesKey("Sample_boolean_Key")

    }

    // 儲存值總覽

    var sampleData: SampleDataFromAPI
        get() = runBlocking {
            kotlin.runCatching {
                readData(sampleDataKey).firstOrNull()
                    ?.toDataBean<SampleDataFromAPI>()
            }.getOrNull() ?: SampleDataFromAPI()
        }
        set(value) = runBlocking { saveData(sampleDataKey, value.toJson()) }


    private suspend fun getSampleString(): String {
        return readData(sampleStringKey, "default value").first()
    }

    suspend fun setSampleString(value: String) {
        saveData(sampleStringKey, value)
    }

    val getSampleDataFlow: Flow<String> = flow {
        emit(getSampleString())
    }
    var sampleString: String
        get() = runBlocking { readData(sampleStringKey).first() }
        set(value) = runBlocking { saveData(sampleStringKey, value) }

    var sampleBoolean: Boolean
        get() = runBlocking { readData(sampleBooleanKey).first() }
        set(value) = runBlocking { saveData(sampleBooleanKey, value) }

    var sampleInt: Int
        get() = runBlocking { readData(sampleIntKey).first() }
        set(value) = runBlocking { saveData(sampleIntKey, value) }


}