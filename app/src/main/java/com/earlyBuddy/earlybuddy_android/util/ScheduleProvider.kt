package com.earlyBuddy.earlybuddy_android.util

import io.reactivex.Scheduler

interface SchedulerProvider {
    fun io(): Scheduler
    fun mainThread(): Scheduler
}