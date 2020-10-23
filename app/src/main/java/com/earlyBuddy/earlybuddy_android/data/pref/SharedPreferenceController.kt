package com.earlyBuddy.earlybuddy_android.data.pref

import android.content.Context

object SharedPreferenceController{

    private val USER_NAME = "MYKEY"
    private val myAuth = "myAuth"

    private val autoLogin = "autoLogin"

    fun setAuthorization(context: Context, authorization : String)
    {
        val pref = context.getSharedPreferences(USER_NAME, Context.MODE_PRIVATE) //현재 내 기기에서만 볼수 있는 데이터
        val editor = pref.edit()
        editor.putString(myAuth, authorization)
        editor.apply()
    }

    fun getAuthorization(context: Context) : String {
        val pref = context.getSharedPreferences(USER_NAME, Context.MODE_PRIVATE) //현재 내 기기에서만 볼수 있는 데이터
        return pref.getString(myAuth, "")!!
    }

    fun deleteAuthorization(context: Context) {
        val pref = context.getSharedPreferences(USER_NAME, Context.MODE_PRIVATE) //현재 내 기기에서만 볼수 있는 데이터
        val editor = pref.edit()
        editor.remove(myAuth)
        editor.apply()
    }

    fun setAutoLogin(context: Context, bool: Boolean){
        val pref = context.getSharedPreferences(autoLogin, Context.MODE_PRIVATE) //현재 내 기기에서만 볼수 있는 데이터
        val editor = pref.edit()
        editor.putBoolean(autoLogin, bool)
        editor.apply()
    }

    fun getAutoLogin(context: Context) : Boolean {
        val pref = context.getSharedPreferences(autoLogin, Context.MODE_PRIVATE) //현재 내 기기에서만 볼수 있는 데이터
        return pref.getBoolean(autoLogin, false)
    }

}