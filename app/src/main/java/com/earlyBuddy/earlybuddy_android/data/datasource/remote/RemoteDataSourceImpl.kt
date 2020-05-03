package com.earlyBuddy.earlybuddy_android.data.datasource.remote

import android.annotation.SuppressLint
import com.earlyBuddy.earlybuddy_android.data.datasource.remote.retrofit.NetworkServiceImpl
import io.reactivex.Observable

class RemoteDataSourceImpl : RemoteDataSource {
    val api = NetworkServiceImpl.SERVICE


}