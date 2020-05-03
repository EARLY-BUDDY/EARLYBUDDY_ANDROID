package com.earlyBuddy.earlybuddy_android.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.earlyBuddy.earlybuddy_android.base.BaseViewModel
import com.earlyBuddy.earlybuddy_android.data.datasource.model.PlaceSearch
import io.reactivex.android.schedulers.AndroidSchedulers

class MainViewModel : BaseViewModel(){

    private var _placeList = MutableLiveData<MutableList<PlaceSearch>>()
    val placeList : LiveData<MutableList<PlaceSearch>> get() = _placeList


    fun getData(){
//        addDisposable(signUpRepository.signUp(body)
//            .observeOn(AndroidSchedulers.mainThread())
//            // 구독할 때 수행할 작업을 구현
//            .doOnSubscribe {}
//            // 스트림이 종료될 때 수행할 작업을 구현
//            .doOnTerminate {}
//            // 옵서버블을 구독
//            .subscribe({
//                // API를 통해 액세스 토큰을 정상적으로 받았을 때 처리할 작업을 구현
//                // 작업 중 오류가 발생하면 이 블록은 호출되지 x
//
//                // onResponse
//                Log.e("postUserData 응답 성공 : ", it.message)
//                _idCheck.postValue(true)
//                _userIdx.value = it.idx
//            }){
//                // 에러 블록
//                // 네트워크 오류나 데이터 처리 오류 등
//                // 작업이 정상적으로 완료되지 않았을 때 호출
//
//                // onFailure
//                Log.e("통신 실패 error : ", it.message!!)
//                _netWork.value = Unit
//            })
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