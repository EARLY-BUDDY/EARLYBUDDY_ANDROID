package com.earlyBuddy.earlybuddy_android.ui.home.going

import androidx.lifecycle.MutableLiveData
import com.earlyBuddy.earlybuddy_android.base.BaseViewModel
import com.earlyBuddy.earlybuddy_android.data.datasource.model.HomeResponse

class GoingViewModel : BaseViewModel() {
    val homeResponse = MutableLiveData<HomeResponse>()

    fun getDate(tempHomeResponse: HomeResponse) {
        homeResponse.value = tempHomeResponse
    }
}