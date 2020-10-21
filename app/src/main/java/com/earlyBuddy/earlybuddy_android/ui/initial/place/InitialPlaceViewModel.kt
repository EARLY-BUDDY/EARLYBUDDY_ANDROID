package com.earlyBuddy.earlybuddy_android.ui.initial.place

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.earlyBuddy.earlybuddy_android.base.BaseViewModel
import com.earlyBuddy.earlybuddy_android.data.datasource.model.FavoriteResponse
import com.earlyBuddy.earlybuddy_android.data.repository.InitialPlaceRepository
import com.earlyBuddy.earlybuddy_android.data.repository.MyPageRepository
import com.google.gson.JsonObject
import io.reactivex.android.schedulers.AndroidSchedulers

class InitialPlaceViewModel(
    private val repository: InitialPlaceRepository,
    private val myPageRepository: MyPageRepository
) : BaseViewModel() {

    val response = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()
    val favoriteList = MutableLiveData<FavoriteResponse>()

    fun registerFavoritePlaces(jsonObject: JsonObject) {
        loading.value = true
        addDisposable(
            repository.registerFavoritePlaces(jsonObject).observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {}.doOnTerminate {
                    loading.value = false
                }.subscribe({
                    response.value = true
                    Log.e("rrr", it.toString())
                }) {
                    response.value = false
                    Log.e("error", it.toString())
                }
        )

    }

    fun getFavoriteList() {
        addDisposable(myPageRepository.getFavoriteList().observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe{}.doOnTerminate {

        }
            .subscribe({
                favoriteList.value = it
            }) {

            }
        )
    }
}