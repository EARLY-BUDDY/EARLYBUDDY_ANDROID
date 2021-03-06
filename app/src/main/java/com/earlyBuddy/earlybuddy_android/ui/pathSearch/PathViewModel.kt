package com.earlyBuddy.earlybuddy_android.ui.pathSearch

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.earlyBuddy.earlybuddy_android.base.BaseViewModel
import com.earlyBuddy.earlybuddy_android.data.datasource.local.entity.RecentPathEntity
import com.earlyBuddy.earlybuddy_android.data.datasource.model.Path
import com.earlyBuddy.earlybuddy_android.data.repository.SearchRouteRepository
import io.reactivex.android.schedulers.AndroidSchedulers

class PathViewModel(private val repository : SearchRouteRepository) : BaseViewModel() {

    val routes: LiveData<List<RecentPathEntity>> = repository.loadRecentPath()
    val loading = MutableLiveData<Boolean>()

    fun insert(recentPath : RecentPathEntity) {
        repository.insert(recentPath)
    }

    fun delete(recentPath : RecentPathEntity) {
        repository.delete(recentPath)
    }

    var routeArrayList = ArrayList<Path>()
    var _routeList = MutableLiveData<ArrayList<Path>>()
    val routeList : LiveData<ArrayList<Path>> get() = _routeList
    val routeFlag = MutableLiveData<Boolean>()



    fun getRouteData(SX : Double, SY : Double, EX : Double, EY : Double, SearchPathType : Int, scheduleStartTime: String){
        loading.value = true
        addDisposable(repository.getSearchRouteData(SX, SY, EX, EY, SearchPathType, scheduleStartTime)
            .observeOn(AndroidSchedulers.mainThread())
            // 구독할 때 수행할 작업을 구현
            .doOnSubscribe {
                loading.value = false
            }
            // 스트림이 종료될 때 수행할 작업을 구현
            .doOnTerminate {
            }
            // 옵서버블을 구독
            .subscribe({
                // API를 통해 액세스 토큰을 정상적으로 받았을 때 처리할 작업을 구현
                // 작업 중 오류가 발생하면 이 블록은 호출되지 x

                if(it.status==200){
                    routeFlag.value = true
                    routeArrayList = it.data.path
                    _routeList.value = it.data.path
//                    Log.e("getRoute 응답 성공 : ", routeList.value.toString())
                }else if(it.status==404){
                    routeFlag.value = false
                }

            }){
                // 에러 블록
                // 네트워크 오류나 데이터 처리 오류 등
                // 작업이 정상적으로 완료되지 않았을 때 호출

                // onFailure
                Log.e("통신 실패 error : ", it.message!!)
            })
    }

}