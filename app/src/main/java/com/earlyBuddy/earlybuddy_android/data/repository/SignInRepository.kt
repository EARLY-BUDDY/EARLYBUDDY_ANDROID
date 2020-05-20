package com.earlyBuddy.earlybuddy_android.data.repository

import com.earlyBuddy.earlybuddy_android.data.datasource.remote.RemoteDataSource
import com.earlyBuddy.earlybuddy_android.data.datasource.remote.RemoteDataSourceImpl

class SignInRepository{
    val remoteDataSource : RemoteDataSource = RemoteDataSourceImpl()
}