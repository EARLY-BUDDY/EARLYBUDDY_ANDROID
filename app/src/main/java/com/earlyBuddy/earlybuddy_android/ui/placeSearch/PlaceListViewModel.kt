package com.earlyBuddy.earlybuddy_android.ui.placeSearch

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.earlyBuddy.earlybuddy_android.base.BaseViewModel
import com.earlyBuddy.earlybuddy_android.data.datasource.model.PlaceSearch
import com.earlyBuddy.earlybuddy_android.data.repository.PlaceListRepository
import com.earlyBuddy.earlybuddy_android.data.repository.PlaceSearchRepository
import io.reactivex.android.schedulers.AndroidSchedulers

class PlaceListViewModel (private val repository: PlaceListRepository): BaseViewModel(){

    private var _placeList = MutableLiveData<List<PlaceSearch>>()
    val placeList : LiveData<List<PlaceSearch>> get() = _placeList

    init{
        getDummyPlaceList()
    }
    fun getPlaceSearchData(query: String){
        addDisposable(repository.searchPlace(query)
            .observeOn(AndroidSchedulers.mainThread())
            // 구독할 때 수행할 작업을 구현
            .doOnSubscribe {}
            // 스트림이 종료될 때 수행할 작업을 구현
            .doOnTerminate {}
            // 옵서버블을 구독
            .subscribe({
                // API를 통해 액세스 토큰을 정상적으로 받았을 때 처리할 작업을 구현
                // 작업 중 오류가 발생하면 이 블록은 호출되지 x

                // onResponse
                Log.e("getPlaceRes 응답 성공 : ", it.data.toString())
                _placeList.value = it.data
            }){
                // 에러 블록
                // 네트워크 오류나 데이터 처리 오류 등
                // 작업이 정상적으로 완료되지 않았을 때 호출

                // onFailure
                Log.e("통신 실패 error : ", it.message!!)
            })
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