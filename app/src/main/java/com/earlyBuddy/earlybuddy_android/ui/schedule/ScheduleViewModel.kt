package com.earlyBuddy.earlybuddy_android.ui.schedule

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.earlyBuddy.earlybuddy_android.EarlyBuddyApplication
import com.earlyBuddy.earlybuddy_android.base.BaseViewModel
import com.earlyBuddy.earlybuddy_android.data.datasource.model.DefaultResponse
import com.earlyBuddy.earlybuddy_android.data.datasource.model.ScheduleDetail
import com.earlyBuddy.earlybuddy_android.data.datasource.model.ScheduleDetailResponse
import com.earlyBuddy.earlybuddy_android.data.repository.CalendarRepository
import com.earlyBuddy.earlybuddy_android.data.repository.ScheduleRepository
import com.google.gson.JsonObject
import io.reactivex.android.schedulers.AndroidSchedulers

class ScheduleViewModel(
    private val repository : ScheduleRepository
) : BaseViewModel() {

    val scheduleDetail = MutableLiveData<ScheduleDetail>()
    val noticeRange = MutableLiveData<String>()
    val postSchedule = MutableLiveData<DefaultResponse>()
    val lottieVisible = MutableLiveData<Boolean>()

    fun getPathData(scheduleIdx: Int) {
        addDisposable(repository.getScheduleDetailData(scheduleIdx)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                lottieVisible.value = true
            }
            .doOnTerminate {
                lottieVisible.value = false
            }
            .subscribe({
                if (it.status == 200) {
                    scheduleDetail.value = it.data



                    noticeRange.value = it.data.scheduleInfo.noticeRange.toString() + "분 전"



                } else {
                    Log.e("getPathData status", it.status.toString())
                }

            }) {
                Log.e("통신 실패 error : ", it.message!!)
            })

    }

    fun postScheData(body: JsonObject){
        addDisposable(repository.postSchedule(body)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
            }.doOnTerminate {
            }.subscribe({
                postSchedule.value = it
                Log.e("getPathData status", it.status.toString())
            }) {
                Log.e("통신 실패 error : ", it.toString())
            })
    }

    fun deleteSchedule(scheduleIdx : Int){

        Log.e("scheduleIdx", scheduleIdx.toString())

        addDisposable(repository.deleteSchedule(scheduleIdx)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                if(it.status == 200){
                    Toast.makeText(EarlyBuddyApplication.globalApplication, "일정이 삭제되었습니다."
                        , Toast.LENGTH_SHORT).show()
                } else {
                    Log.e("deleteSchedule status", it.status.toString())
                }
            }) {
              Log.e("delete Schedule error", it.message)
            })
    }
}
