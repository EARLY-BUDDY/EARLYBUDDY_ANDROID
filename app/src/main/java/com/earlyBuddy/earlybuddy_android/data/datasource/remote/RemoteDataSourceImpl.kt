package com.example.android_study_favorite.datasource.remote

import android.annotation.SuppressLint
import com.example.android_study_favorite.datasource.remote.retrofit.NetworkServiceImpl
import io.reactivex.Observable

class RemoteDataSourceImpl : RemoteDataSource {
    val api = NetworkServiceImpl.SERVICE


}