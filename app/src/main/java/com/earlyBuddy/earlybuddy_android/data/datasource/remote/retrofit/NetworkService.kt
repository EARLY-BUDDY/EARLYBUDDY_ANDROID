package com.example.android_study_favorite.datasource.remote.retrofit

import com.earlyBuddy.earlybuddy_android.data.datasource.model.HomeResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {
    @GET("/searchAddress")
    fun getAdress(
        @Query("addr") addr: String
    ): Observable<HomeResponse>
}