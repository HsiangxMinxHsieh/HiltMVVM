package com.timmy.hiltmvvm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.timmy.hiltmvvm.data.SampleRepository
import com.timmy.sharedpreferencelibs.data.SampleDataFromAPI
import com.timmy.sharedpreferencelibs.repo.SharedPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SampleViewModel @Inject constructor(private val spRepo: SharedPreferencesRepository, private val sampleRepository: SampleRepository) : ViewModel() {

    fun getData() {
        sampleRepository.init()
        viewModelScope.launch {
            sampleRepository.getDataFromAPI()
        }
    }

    fun saveSampleData(value: SampleDataFromAPI) {
        spRepo.sampleData = value
    }

    fun plusCountAndGet() = spRepo.plusAndGetSampleCount()

    fun getSampleData() = spRepo.sampleData

    fun getLiveDataInRealm() = sampleRepository.getLiveDataInRealm()

}