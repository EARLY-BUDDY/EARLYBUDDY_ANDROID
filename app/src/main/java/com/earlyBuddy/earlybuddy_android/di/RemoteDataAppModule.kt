package com.earlyBuddy.earlybuddy_android.di

import com.earlyBuddy.earlybuddy_android.data.datasource.remote.RemoteDataSource
import com.earlyBuddy.earlybuddy_android.data.datasource.remote.RemoteDataSourceImpl
import org.koin.dsl.module

val remoteDataAppModule = module { single<RemoteDataSource> { RemoteDataSourceImpl() } }