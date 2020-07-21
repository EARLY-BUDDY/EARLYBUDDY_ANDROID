package com.earlyBuddy.earlybuddy_android.ui.placeSearch

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.earlyBuddy.earlybuddy_android.EarlyBuddyApplication
import com.earlyBuddy.earlybuddy_android.base.BaseViewModel
import com.earlyBuddy.earlybuddy_android.data.datasource.model.Path
import com.earlyBuddy.earlybuddy_android.data.repository.SearchRouteRepository
import io.reactivex.android.schedulers.AndroidSchedulers

class PathViewModel(private val repository : SearchRouteRepository) : BaseViewModel() {

    private var _routeList = MutableLiveData<List<Path>>()
    val routeList : LiveData<List<Path>> get() = _routeList

    fun getRouteData(SX : Double, SY : Double, EX : Double, EY : Double, SearchPathType : Int){
        addDisposable(repository.getSearchRouteData(SX, SY, EX, EY, SearchPathType)
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
                _routeList.value = it.data.path
                Log.e("getRoute 응답 성공 : ", routeList.value.toString())
            }){
                // 에러 블록
                // 네트워크 오류나 데이터 처리 오류 등
                // 작업이 정상적으로 완료되지 않았을 때 호출

                // onFailure
                Log.e("통신 실패 error : ", it.message!!)
            })
    }
}