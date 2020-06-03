package com.earlyBuddy.earlybuddy_android.di

import com.earlyBuddy.earlybuddy_android.data.repository.*
import org.koin.dsl.module

val repositoryAppModule = module {


    single { PlaceListRepository(get()) }
    single { PlaceSearchRepository(get()) }
    single { RecentPlaceRepository(get()) }
    single { SignInRepository(get()) }
    single { SignUpRepository(get()) }
}