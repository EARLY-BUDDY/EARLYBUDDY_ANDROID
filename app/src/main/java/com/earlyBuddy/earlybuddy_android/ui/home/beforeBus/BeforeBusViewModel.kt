package com.earlyBuddy.earlybuddy_android.ui.home.BeforeBusFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.earlyBuddy.earlybuddy_android.base.BaseViewModel

class BeforeBusViewModel : BaseViewModel() {
    private val _planTitle = MutableLiveData<String>()
    val planTitle: LiveData<String> get() = _planTitle

    fun getData() {
        _planTitle.value = "AsdasD"
    }

}