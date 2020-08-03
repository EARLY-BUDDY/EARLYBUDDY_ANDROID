package com.earlyBuddy.earlybuddy_android.base

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.earlyBuddy.earlybuddy_android.EarlyBuddyApplication
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel() : ViewModel() {

    val ealryBuddyApplication = EarlyBuddyApplication.getGlobalApplicationContext()

    private val compositeDisposable = CompositeDisposable()

    fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

}