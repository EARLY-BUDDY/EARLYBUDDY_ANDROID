package com.earlyBuddy.earlybuddy_android

import android.app.Application

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
    }

}