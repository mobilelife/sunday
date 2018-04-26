package com.ragaisis.sunday.entities

class AddressSuggestResponse(
        val cities: List<AddressSuggestCity>?,
        val listingAddresses: List<AddressSuggestListingAddress>?,
        val locations: List<AddressSuggestLocation>?,
        val streets: List<AddressSuggestStreet>?
)
