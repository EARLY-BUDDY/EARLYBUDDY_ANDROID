package com.earlyBuddy.earlybuddy_android.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.earlyBuddy.earlybuddy_android.base.BaseViewModel
import com.earlyBuddy.earlybuddy_android.data.datasource.model.PlaceSearch

class MainViewModel : BaseViewModel(){

    private var _placeList = MutableLiveData<MutableList<PlaceSearch>>()
    val placeList : LiveData<MutableList<PlaceSearch>> get() = _placeList


    fun getData(){
//        addDisposable()
    }

    init {
        getDummyPlaceList()
    }

    fun getDummyPlaceList(){
        val dummy = arrayListOf<PlaceSearch>(
            PlaceSearch(
                "장소이름1",
                "주소이름1",
                "도로명이름1"
            ),
            PlaceSearch(
                "장소이름2",
                "주소이름2",
                "도로명이름2"
            ),
            PlaceSearch(
                "장소이름3",
                "주소이름3",
                "도로명이름3"
            )
        )

        _placeList.postValue(dummy)
    }

}