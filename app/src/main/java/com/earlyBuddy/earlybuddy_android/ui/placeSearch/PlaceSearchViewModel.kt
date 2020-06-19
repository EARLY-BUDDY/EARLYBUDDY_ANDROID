package com.earlyBuddy.earlybuddy_android.ui.placeSearch

import android.util.Log
import androidx.lifecycle.*
import com.earlyBuddy.earlybuddy_android.EarlyBuddyApplication
import com.earlyBuddy.earlybuddy_android.base.BaseViewModel
import com.earlyBuddy.earlybuddy_android.data.datasource.model.PlaceSearch
import com.earlyBuddy.earlybuddy_android.data.repository.PlaceSearchRepository
import io.reactivex.android.schedulers.AndroidSchedulers

class PlaceSearchViewModel(private val repository : PlaceSearchRepository) : BaseViewModel(EarlyBuddyApplication.getGlobalApplicationContext()) {

    private var _placeList = MutableLiveData<List<PlaceSearch>>()
    val placeList : LiveData<List<PlaceSearch>> get() = _placeList

//    private val context = EarlyBuddyApplication.getGlobalApplicationContext()
//    val recentPlaceDao = RecentPlaceDB.getDatabase(context, viewModelScope).recentPlaceDao()
//
//    fun insert(recentPlace : RecentPlaceEntity) = viewModelScope.launch(Dispatchers.IO) {
//        repository.insert(recentPlace)
//    }

    fun getPlaceSearchData(keyword: String, x:Double, y:Double){
        addDisposable(repository.searchPlace(keyword, x, y)
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
                _placeList.value = it.data
                Log.e("getPlaceRes 응답 성공 : ", placeList.value.toString())
            }){
                // 에러 블록
                // 네트워크 오류나 데이터 처리 오류 등
                // 작업이 정상적으로 완료되지 않았을 때 호출

                // onFailure
                Log.e("통신 실패 error : ", it.message!!)
            })
    }
}