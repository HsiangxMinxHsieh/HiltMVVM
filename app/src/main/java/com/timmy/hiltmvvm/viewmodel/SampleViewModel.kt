package com.timmy.hiltmvvm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timmy.datastorelibs.repo.DataStoreRepository
import com.timmy.datastorelibs.repo.PreferencesKeys
import com.timmymike.assetslibs.repo.GetAPIRepository
import com.timmy.sharedpreferencelibs.data.SampleDataFromAPI
import com.timmy.sharedpreferencelibs.repo.SharedPreferencesRepository
import com.timmymike.assetslibs.repo.AssetsRepository
import com.timmymike.logtool.loge
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SampleViewModel @Inject constructor(
    private val spRepo: SharedPreferencesRepository,
    private val dsRepo: DataStoreRepository,
    private val asRepo: AssetsRepository,
    private val getAPIRepository: GetAPIRepository
) : ViewModel() {

    fun getData() {

        // DataStoreRepository
        viewModelScope.launch {
            dsRepo.saveData(PreferencesKeys.loginResponseKey, "ggg4417723")
            (dsRepo.readData(PreferencesKeys.loginResponseKey) as Flow<String>).collect { storedData -> loge("DataStore取得結果=>$storedData") }
        }

        // GetAPIRepository
        viewModelScope.launch {
            getAPIRepository.getDataFromAPI()

        }

        // AssetsRepository
        viewModelScope.launch {
            loge("assets取得類別結果=>${asRepo.getAssetsToClass<Any>("test.json")}")
            loge("assets取得內容結果=>${asRepo.getAssetsContent("test.json")}")
        }
    }

    // SharedPreferencesRepository
    fun saveSampleData(value: SampleDataFromAPI) {
        spRepo.sampleData = value
    }

    //GetAPIRepository
    fun getSampleData() = spRepo.sampleData

    fun getLiveDataInRealm() = getAPIRepository.getLiveDataInRealm()

}
