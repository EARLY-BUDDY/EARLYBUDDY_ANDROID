package com.earlyBuddy.earlybuddy_android.ui.initial.nickname

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.earlyBuddy.earlybuddy_android.base.BaseViewModel
import com.earlyBuddy.earlybuddy_android.data.datasource.model.NickNameResponse
import com.earlyBuddy.earlybuddy_android.data.repository.NickNameRepository
import com.google.gson.JsonObject
import io.reactivex.android.schedulers.AndroidSchedulers

class NickNameViewModel(private val repository: NickNameRepository) : BaseViewModel() {
    val nickNameResponse = MutableLiveData<NickNameResponse>()

    fun putUserNickName(jsonObject: JsonObject) {
        addDisposable(
            repository.putUserNickName(jsonObject).observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {}.doOnTerminate {

                }.subscribe({
                    nickNameResponse.value = it

                }) {
                    Log.e("error", it.toString())
                }
        )
    }

}