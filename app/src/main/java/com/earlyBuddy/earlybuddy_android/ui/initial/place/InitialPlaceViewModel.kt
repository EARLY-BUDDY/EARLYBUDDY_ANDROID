package com.earlyBuddy.earlybuddy_android.ui.initial.place

import androidx.lifecycle.MutableLiveData
import com.earlyBuddy.earlybuddy_android.base.BaseViewModel
import com.earlyBuddy.earlybuddy_android.data.repository.InitialPlaceRepository
import com.google.gson.JsonObject
import io.reactivex.android.schedulers.AndroidSchedulers

class InitialPlaceViewModel(private val repository: InitialPlaceRepository) : BaseViewModel() {

    val response = MutableLiveData<Boolean>()

    fun registerFavoritePlaces(jsonObject: JsonObject) {
        addDisposable(
            repository.registerFavoritePlaces(jsonObject).observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {}.doOnTerminate {}.subscribe({
                    response.value = true
                }) {
                    response.value = false
                }
        )

    }
}