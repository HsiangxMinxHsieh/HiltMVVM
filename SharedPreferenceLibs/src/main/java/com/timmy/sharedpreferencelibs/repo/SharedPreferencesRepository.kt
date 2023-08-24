package com.timmy.sharedpreferencelibs.repo

import android.content.Context
import com.timmy.base.data.SampleDataFromAPI
import com.timmymike.logtool.toDataBean
import com.timmymike.logtool.toJson
import javax.inject.Inject

/**
 *     author: Timmy
 *     date  : 2023/08/09
 *     desc  : 同步的取值方法
 */
class SharedPreferencesRepository @Inject constructor(context: Context) {


//      加密的 SharedPreferences
//    private val sp = EncryptedSharedPreferences.create(
//        context,
//        "Encrypted_$PREFERENCE_NAME",
//        MasterKey.Builder(context).setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build(),
//        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
//        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
//    )

    //  可直接看到內容的 SharedPreferences
    private val sp = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFERENCE_NAME = "Preferences"

        // Key總覽
//        private const val SETTING_DEVICE_TOKEN = "SETTING_DEVICE_TOKEN"
        private const val SAMPLE_INFO = "SAMPLE_INFO"
    }


    // 儲存值總覽
//    var token: String
//        get() = sp.getString(SETTING_DEVICE_TOKEN, "") ?: ""
//        set(value) = sp.edit().putString(SETTING_DEVICE_TOKEN, value).apply()
//
//    var isSwitch: Boolean
//        get() = sp.getBoolean(SETTING_DEVICE_TOKEN, false)
//        set(value) = sp.edit().putBoolean(SETTING_DEVICE_TOKEN, value).apply()

    var sampleData: SampleDataFromAPI
        get() = kotlin.runCatching { sp.getString(SAMPLE_INFO, "{}")?.toDataBean<SampleDataFromAPI>() }.getOrNull() ?: SampleDataFromAPI()
        set(value) = sp.edit().putString(SAMPLE_INFO, value.toJson()).apply()
//
//    var sampleCount: Int
//        get() = sp.getInt(SETTING_DEVICE_TOKEN, 0)
//        set(value) = sp.edit().putInt(SETTING_DEVICE_TOKEN, value).apply()

}