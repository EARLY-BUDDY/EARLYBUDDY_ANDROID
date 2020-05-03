package com.earlyBuddy.earlybuddy_android.data.datasource.remote.retrofit

import com.earlyBuddy.earlybuddy_android.data.datasource.model.HomeResponse

import com.earlyBuddy.earlybuddy_android.data.datasource.model.PlaceResponse

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {
    @GET("/searchAddress")
    fun getAdress(
        @Query("addr") addr: String
    ): Observable<HomeResponse>

    @GET("/searchAddress")
    fun getPlaceData(
        @Query("addr") addr: String
    ): Observable<PlaceResponse>
}