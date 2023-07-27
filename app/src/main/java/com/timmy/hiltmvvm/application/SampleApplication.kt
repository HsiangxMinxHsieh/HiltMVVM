package com.timmy.hiltmvvm.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class SampleApplication : Application() {
    init {
        instance = this
    }

    companion object {
        private lateinit var instance: SampleApplication
    }

}