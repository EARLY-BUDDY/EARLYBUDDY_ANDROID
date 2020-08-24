package com.earlyBuddy.earlybuddy_android.ui.signUp

import android.os.Handler
import android.util.Log
import com.earlyBuddy.earlybuddy_android.data.datasource.remote.RemoteDataSourceImpl
import com.earlyBuddy.earlybuddy_android.data.repository.SignInRepository
import com.earlyBuddy.earlybuddy_android.di.remoteDataAppModule
import com.earlyBuddy.earlybuddy_android.di.repositoryAppModule
import com.earlyBuddy.earlybuddy_android.di.viewModelAppModule
import com.earlyBuddy.earlybuddy_android.ui.Loading
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import org.json.JSONObject
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.test.KoinTest
import org.mockito.MockitoAnnotations

class SignInActivityTest : KoinTest {

    val signInRepository = SignInRepository(RemoteDataSourceImpl())
    val signInViewModel: SignInViewModel = SignInViewModel(signInRepository)
    val jsonObject: JSONObject = JSONObject()

    @Before
    fun setUp() {
        startKoin {

            MockitoAnnotations.initMocks(this)
            modules(
                listOf(
                    remoteDataAppModule,
                    repositoryAppModule,
                    viewModelAppModule
                )
            )
        }
        jsonObject.put("userId", "test1")
        jsonObject.put("userPw", "test2")
        jsonObject.put("deviceToken", "1")

    }

    @After
    fun tearDown() {
        println(signInViewModel.sss.toString())
    }

    @Test
    fun test() {
        val body = JsonParser.parseString(jsonObject.toString()) as JsonObject
        println(body.toString())

        signInViewModel.postSignIn(body)
        val handler = Handler()
        handler.postDelayed({
            Log.e("로딩", "끝ㅠㅠㅠㅠㅠㅠ")
            assertEquals(signInViewModel.signInCheck.value, true)
        }, 2000)

    }
}