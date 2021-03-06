package com.earlyBuddy.earlybuddy_android.ui.signUp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.earlyBuddy.earlybuddy_android.EarlyBuddyApplication
import com.earlyBuddy.earlybuddy_android.base.BaseViewModel
import com.earlyBuddy.earlybuddy_android.data.datasource.model.SignInResponse
import com.earlyBuddy.earlybuddy_android.data.pref.SharedPreferenceController
import com.earlyBuddy.earlybuddy_android.data.repository.SignInRepository
import com.google.gson.JsonObject
import io.reactivex.android.schedulers.AndroidSchedulers

class SignInViewModel(private val repository : SignInRepository) : BaseViewModel(){

    var signInResponse = MutableLiveData<SignInResponse>()

    private var _userIdx = MutableLiveData<Int>()
    val userIdx: LiveData<Int> get() = _userIdx

    fun postSignIn(body: JsonObject) {
        //디스포저블(Disposable)은 옵서버가 옵서버블을 구독할 때 생성되는 객체
        addDisposable(
            repository.signIn(body)
                .observeOn(AndroidSchedulers.mainThread())
                // 구독할 때 수행할 작업을 구현
                .doOnSubscribe {}
                // 스트림이 종료될 때 수행할 작업을 구현
                .doOnTerminate {}
                // 옵서버블을 구독
                .subscribe({
                    // API를 통해 액세스 토큰을 정상적으로 받았을 때 처리할 작업을 구현
                    // 작업 중 오류가 발생하면 이 블록은 호출되지 x
                    Log.e("token is ", it.toString())
                    Log.e("signin status", it.status.toString())
                    signInResponse.postValue(it)
                    if (it.status == 200) {
                        SharedPreferenceController.setAuthorization(
                            EarlyBuddyApplication.getGlobalApplicationContext(),
                            it.data!!.jwt
                        )
                        if (it.data!!.userName != null) {
                            SharedPreferenceController.setNickName(
                                EarlyBuddyApplication.getGlobalApplicationContext(),
                                it.data!!.userName
                            )
                        }
                    }

                }) {
                    // 에러 블록
                    // 네트워크 오류나 데이터 처리 오류 등
                    // 작업이 정상적으로 완료되지 않았을 때 호출

                    // onFailure
                    Log.e("통신 실패 error : ", it.toString()!!)
                })
    }
}