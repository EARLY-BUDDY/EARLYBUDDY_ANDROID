package com.earlyBuddy.earlybuddy_android.ui.placeSearch

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.earlyBuddy.earlybuddy_android.EarlyBuddyApplication
import com.earlyBuddy.earlybuddy_android.base.BaseViewModel
import com.earlyBuddy.earlybuddy_android.data.datasource.local.dao.RecentPlaceDao
import com.earlyBuddy.earlybuddy_android.data.datasource.local.database.RecentPlaceDB
import com.earlyBuddy.earlybuddy_android.data.datasource.local.entity.RecentPlaceEntity
import com.earlyBuddy.earlybuddy_android.data.repository.RecentPlaceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecentPlaceViewModel(private val repository: RecentPlaceRepository) : BaseViewModel() {


    private var _recentPlaceList = MutableLiveData<LiveData<List<RecentPlaceEntity>>>()
    val recentPlaceList : LiveData<LiveData<List<RecentPlaceEntity>>> get() = _recentPlaceList
    private val context = EarlyBuddyApplication.getGlobalApplicationContext()

    init {
        _recentPlaceList.value = repository.loadRecentPlace()
        Log.e("repository에서 recentPlace확인", repository.loadRecentPlace()?.value.toString())
    }

    fun insert(recentPlace : RecentPlaceEntity) = viewModelScope.launch(Dispatchers.IO) {
        Log.e("viewModel insert 실행 ", recentPlace.placeName)
        repository.insert(recentPlace)
    }
}
