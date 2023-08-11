package com.timmy.hiltmvvm.ui

import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.viewModels
import com.timmy.hiltmvvm.R
import com.timmy.hiltmvvm.databinding.ActivitySampleBinding
import com.timmy.hiltmvvm.viewmodel.SampleViewModel
import com.timmymike.componenttool.BaseActivity
import com.timmymike.logtool.*
import com.timmymike.viewtool.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SampleActivity : BaseActivity<ActivitySampleBinding>() {

    private val viewModel: SampleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()

        val nameList = viewModel.getAssetListData()

        binding.tvTest.run {
            isClickable = true
            click {
                loge("1觸發點擊！")
                isSelected = !isSelected
                viewModel.savePerson(nameList.random())

                viewModel.getPersonData().toList().logeAll("測試資料輸出=>")
            }

//            setTextSize(14)
//            background = getRectangleBg(tldp = 40, trdp = 40, left = false, bgColorID = R.color.purple_200, strokeWidth = 10, strokeColorID = R.color.teal_200)
            30.dpToPx.let {
                setPadding(it, it, it, it)
            }
            loge("tv1佔比總寬度=>${this.getRealityWidth().toFloat() / getScreenWidthPixels()}")
            setClickTextColorStateById(android.R.color.holo_green_dark, android.R.color.holo_blue_dark, R.color.purple_700)

            background = setClickBgState(
                getRoundBg(500, android.R.color.holo_purple, android.R.color.black, 20),
                getRectangleBg(tldp = 40, trdp = 0, brdp = 40, left = false, bgColorID = android.R.color.black, strokeWidth = 10, strokeColorID = R.color.teal_200),
                getRectangleBg(tldp = 40, trdp = 0, brdp = 40, left = false, bottom = false, bgColorID = android.R.color.white, strokeWidth = 10, strokeColorID = R.color.purple_700),
            )
//            background = getRepeatDrawable(R.drawable.ic_launcher_foreground)
//            background = getTintedDrawable(R.drawable.ic_launcher_foreground, android.R.color.holo_green_dark)
//            setBackgroundResource(R.drawable.ic_class_checkbox_checked)
//            background = setPressedImage(R.drawable.ic_class_checkbox_checked,-1)
            setRippleBackground(android.R.color.holo_red_dark)

        }
        (binding.root as? ViewGroup)?.resetLayoutTextSize()
        viewModel.getLiveDataInRealm().observe(this) {
            logd("API取得結果是=>${it.toJson()}")

            viewModel.saveSampleDataToSP(it)
            loge("SharedPreference 儲存後取出的範例資料是=>${viewModel.getSampleDataFromSP()}")

            viewModel.saveSampleDataToDataStore(it)
            loge("DataStore 儲存後取出的範例資料是=>${viewModel.getSampleDataFromDataStore()}")

            viewModel.saveSampleDataToRoom(it.records.toJson().toDataBeanList())
            loge("Room 儲存後取出的範例資料數量是=>${viewModel.getRoomSampleDataSize()}")

        }
    }


    private fun initData() {
        viewModel.getData()
    }
}