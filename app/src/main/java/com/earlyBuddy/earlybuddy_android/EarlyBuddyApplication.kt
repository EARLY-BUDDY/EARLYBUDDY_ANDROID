package com.earlyBuddy.earlybuddy_android

import android.app.Application
import com.earlyBuddy.earlybuddy_android.di.remoteDataAppModule
import com.earlyBuddy.earlybuddy_android.di.repositoryAppModule
import com.earlyBuddy.earlybuddy_android.di.viewModelAppModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class EarlyBuddyApplication : Application() {

    companion object{
        lateinit var  globalApplication: EarlyBuddyApplication
        lateinit var instance : EarlyBuddyApplication

        fun getGlobalApplicationContext(): EarlyBuddyApplication {
            return instance
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        globalApplication = this

        startKoin {

            androidContext(this@EarlyBuddyApplication)
            modules(listOf(
                remoteDataAppModule,
                repositoryAppModule,
                viewModelAppModule
            ))}
    }

}