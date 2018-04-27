package com.ragaisis.sunday.entities

open class AddressDetailsResponse(
        val homes: List<AddressDetailsHomes>?,
        val totalListedHomes: Int?,
        val viewport: String?
)