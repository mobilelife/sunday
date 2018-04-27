package com.ragaisis.sunday.api

import com.ragaisis.sunday.entities.AddressDetailsRequest
import com.ragaisis.sunday.entities.AddressDetailsResponse
import com.ragaisis.sunday.entities.AddressSuggestResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SundayApi {

    @GET("homes/homes/address-suggest/{address}")
    fun getSuggestedAddress(@Path("address") address : String) : Observable<AddressSuggestResponse>

    @POST("homes/v2/homes/search")
    fun getAddressDetails(@Body address : AddressDetailsRequest) : Observable<AddressDetailsResponse>
}