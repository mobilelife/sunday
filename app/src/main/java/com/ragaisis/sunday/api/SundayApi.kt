package com.ragaisis.sunday.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface SundayApi {

    @GET("homes/homes/address-suggest/{address}")
    fun getNewsByTopic(@Path("address") address : String) : Observable<Void>

}