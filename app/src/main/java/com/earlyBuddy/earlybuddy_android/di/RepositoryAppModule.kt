package com.earlyBuddy.earlybuddy_android.di

import com.earlyBuddy.earlybuddy_android.data.repository.*
import org.koin.dsl.module

val repositoryAppModule = module {

    single { PlaceListRepository(get()) }
    single { PlaceSearchRepository(get()) }
    single { SignInRepository(get()) }
    single { SignUpRepository(get()) }
    single { SearchRouteRepository(get()) }
}