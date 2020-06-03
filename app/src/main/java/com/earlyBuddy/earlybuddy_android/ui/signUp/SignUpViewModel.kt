package com.earlyBuddy.earlybuddy_android.ui.signUp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.earlyBuddy.earlybuddy_android.base.BaseViewModel
import com.earlyBuddy.earlybuddy_android.data.repository.SignUpRepository
import com.google.gson.JsonObject
import io.reactivex.android.schedulers.AndroidSchedulers

class SignUpViewModel(private val repository : SignUpRepository) : BaseViewModel(){

    private var _netWork = MutableLiveData<Unit>()
    val netWork : LiveData<Unit> get() = _netWork

    private var _signUpCheck = MutableLiveData<Boolean>()
    val signUpCheck : LiveData<Boolean> get() = _signUpCheck

    fun postSignUp(body:JsonObject){
        addDisposable(disposable = repository.signUp(body)
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
                Log.e("postUserData 응답 성공 : ", it.message)
                if(it.status==200)
                    _signUpCheck.postValue(true)
//                else if(it.status==400)
//                    _signUpCheck.postValue(false)
            }){
                // 에러 블록
                // 네트워크 오류나 데이터 처리 오류 등
                // 작업이 정상적으로 완료되지 않았을 때 호출

                // onFailure
//                Log.e("통신 실패 error : ", it.message!!)
//                _netWork.value = Unit
                _signUpCheck.postValue(false)
            })
    }
}
