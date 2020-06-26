package com.earlyBuddy.earlybuddy_android.di

import com.earlyBuddy.earlybuddy_android.ui.home.HomeViewModel
import com.earlyBuddy.earlybuddy_android.ui.pathSearch.PathViewModel
import com.earlyBuddy.earlybuddy_android.ui.placeSearch.PlaceSearchViewModel
import com.earlyBuddy.earlybuddy_android.ui.signUp.SignInViewModel
import com.earlyBuddy.earlybuddy_android.ui.signUp.SignUpViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelAppModule = module {
    viewModel { HomeViewModel() }
    viewModel { PlaceSearchViewModel(get()) }
    viewModel { SignInViewModel(get()) }
    viewModel { SignUpViewModel(get()) }
    viewModel { PathViewModel(get()) }
}