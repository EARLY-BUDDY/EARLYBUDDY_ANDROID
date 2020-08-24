package com.earlyBuddy.earlybuddy_android.ui.initial.place

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.earlyBuddy.earlybuddy_android.base.BaseViewModel
import com.earlyBuddy.earlybuddy_android.data.repository.InitialPlaceRepository
import com.google.gson.JsonObject
import io.reactivex.android.schedulers.AndroidSchedulers

class InitialPlaceViewModel(private val repository: InitialPlaceRepository) : BaseViewModel() {

    val response = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun registerFavoritePlaces(jsonObject: JsonObject) {
        loading.value = true
        addDisposable(
            repository.registerFavoritePlaces(jsonObject).observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {}.doOnTerminate {
                    loading.value = false
                }.subscribe({
                    response.value = true
                    Log.e("rrr",it.toString())
                }) {
                    response.value = false
                    Log.e("error", it.toString())
                }
        )

    }
}