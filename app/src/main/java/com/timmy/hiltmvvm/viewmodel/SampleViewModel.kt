package com.timmy.hiltmvvm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timmy.assetslibs.repo.AssetsRepository
import com.timmy.assetslibs.repo.GetAPIRepository
import com.timmy.base.data.SampleDataFromAPI
import com.timmy.datastorelibs.repo.DataStoreRepository
import com.timmy.roomlibs.database.tables.sample.SampleData
import com.timmy.roomlibs.repo.PersonRepo
import com.timmy.roomlibs.repo.RoomRepo
import com.timmy.sharedpreferencelibs.repo.SharedPreferencesRepository
import com.timmymike.logtool.loge
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SampleViewModel @Inject constructor(
    private val roomRepo: RoomRepo,
    private val personRepo: PersonRepo,
    private val spRepo: SharedPreferencesRepository,
    private val dsRepo: DataStoreRepository,
    private val asRepo: AssetsRepository,
    private val getAPIRepository: GetAPIRepository
) : ViewModel() {

    fun getData() {

        // DataStoreRepository 非同步方案
        viewModelScope.launch {
            dsRepo.sampleString = "bbbggg4417723"
            loge("DataStore取得同步結果=>[${dsRepo.sampleString}]")
            (dsRepo.readData(DataStoreRepository.sampleStringKey, "Default Value") as Flow<String>).collect { storedData -> loge("DataStore取得非同步結果=>[$storedData]") }
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
    fun saveSampleDataToSP(value: SampleDataFromAPI) {
        spRepo.sampleData = value
    }

    fun getSampleDataFromSP() = spRepo.sampleData


    // DataStoreRepository 同步方案
    fun saveSampleDataToDataStore(value: SampleDataFromAPI) {
        dsRepo.sampleData = value
    }

    fun getSampleDataFromDataStore() = dsRepo.sampleData

    // GetAPIRepository Live Data
    fun getLiveDataInRealm() = getAPIRepository.getLiveDataInRealm()

    // Room Database
    fun savePerson(name: String) = personRepo.insertPersonData(name)

    fun saveSampleDataToRoom(value: List<SampleData>) = roomRepo.insertSampleDataList(value)

    fun getRoomSampleDataSize() = roomRepo.getSampleDataList().size

    fun getPersonData() = personRepo.getPersonDataList()

}
