package com.ragaisis.sunday.api

import com.ragaisis.sunday.entities.AddressSuggestResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface SundayApi {

    @GET("homes/homes/address-suggest/{address}")
    fun getSuggestedAddress(@Path("address") address : String) : Observable<AddressSuggestResponse>

}