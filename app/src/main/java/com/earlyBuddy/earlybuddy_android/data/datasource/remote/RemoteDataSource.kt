package com.earlyBuddy.earlybuddy_android.data.datasource.remote

import com.earlyBuddy.earlybuddy_android.data.datasource.model.PlaceResponse
import io.reactivex.Observable

interface RemoteDataSource {
    fun searchPlace(query: String) : Observable<PlaceResponse>
}