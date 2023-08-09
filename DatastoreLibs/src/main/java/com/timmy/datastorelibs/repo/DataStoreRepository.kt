package com.timmy.datastorelibs.repo

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.timmymike.base.data.SampleDataFromAPI
import com.timmymike.logtool.toDataBean
import com.timmymike.logtool.toJson
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import java.io.IOException
import javax.inject.Inject

/**
 * 非同步或同步皆可的取值方法
 */
class DataStoreRepository @Inject constructor(context: Application) {

    private val Context._dataStore: DataStore<Preferences> by preferencesDataStore(
        name = PreferencesKeys.DATASTORE_NAME,
    )

    val dataStore: DataStore<Preferences> = context._dataStore

    var sampleData: SampleDataFromAPI
        get() = runBlocking {
            kotlin.runCatching {
                readData(PreferencesKeys.sampleDataKey).firstOrNull()
                    ?.toDataBean(SampleDataFromAPI::class.java)
            }.getOrNull() ?: SampleDataFromAPI()
        }
        set(value) = runBlocking { saveData(PreferencesKeys.sampleDataKey, value.toJson()) }

    var sampleString: String
        get() = runBlocking { readData(PreferencesKeys.sampleStringKey).first() }
        set(value) = runBlocking { saveData(PreferencesKeys.sampleStringKey, value) }

    var sampleBoolean: Boolean
        get() = runBlocking { readData(PreferencesKeys.sampleBooleanKey).first() }
        set(value) = runBlocking { saveData(PreferencesKeys.sampleBooleanKey, value) }

    var sampleInt: Int
        get() = runBlocking { readData(PreferencesKeys.sampleIntKey).first() }
        set(value) = runBlocking { saveData(PreferencesKeys.sampleIntKey, value) }

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

}

object PreferencesKeys {
    const val DATASTORE_NAME = "DataStore"

    val sampleStringKey = stringPreferencesKey("user_info_response_Key")
    val sampleDataKey = stringPreferencesKey("Sample_Data_Response_Key")
    val sampleLongKey = longPreferencesKey("Sample_Long_Key")
    val sampleIntKey = intPreferencesKey("Sample_Int_Key")
    val sampleBooleanKey = booleanPreferencesKey("Sample_boolean_Key")
}