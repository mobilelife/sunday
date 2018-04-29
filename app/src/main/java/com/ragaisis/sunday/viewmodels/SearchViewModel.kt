package com.ragaisis.sunday.viewmodels

import android.app.SearchManager
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.database.MatrixCursor
import android.provider.BaseColumns
import com.ragaisis.sunday.MyApplication
import com.ragaisis.sunday.R
import com.ragaisis.sunday.api.SundayApi
import com.ragaisis.sunday.cursors.SuggestionCursorAdapter.Companion.COLUMN_TYPE
import com.ragaisis.sunday.cursors.SuggestionCursorAdapter.Companion.COLUMN_TYPE_HEADER
import com.ragaisis.sunday.entities.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SearchViewModel @Inject constructor(
        private val application: MyApplication,
        private val api: SundayApi) : ViewModel() {

    var addressSuggestions: MutableLiveData<MatrixCursor> = MutableLiveData()
    var suggestions: MutableLiveData<List<AddressDetailsHomes>> = MutableLiveData()
    var error: MutableLiveData<String> = MutableLiveData()
    var searchQuery: String? = null

    fun search(query: String) {
        searchQuery = query
        if (query.isNotEmpty()) {
            api.getSuggestedAddress(query)
                    .subscribeOn(Schedulers.io())
                    .subscribe { addressSuggestions.postValue(getCursor(it)) }
        }
    }

    fun getAddressDetails(query: String) {
        searchQuery = query
        api.getAddressDetails(AddressDetailsRequest(AddressDetailsAddress((query))))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError { error.postValue(application.getString(R.string.api_error)) }
                .subscribe { suggestions.value = it.homes }
    }

    private fun getCursor(suggestions: AddressSuggestResponse): MatrixCursor {
        var index = 10
        val columns = arrayOf(
                BaseColumns._ID,
                COLUMN_TYPE,
                SearchManager.SUGGEST_COLUMN_TEXT_1,
                SearchManager.SUGGEST_COLUMN_INTENT_DATA)
        val cursor = MatrixCursor(columns)
        if (suggestions.cities != null) {
            cursor.addRow(arrayOf(0, COLUMN_TYPE_HEADER, "Cities", "Cities"))
            for (i in 0 until suggestions.cities.size) {
                cursor.addRow(arrayOf(
                        Integer.toString(index),
                        "cities",
                        suggestions.cities.get(i).city,
                        suggestions.cities.get(i)))
                index++
            }
        }
        if (suggestions.listingAddresses != null) {
            cursor.addRow(arrayOf(1, COLUMN_TYPE_HEADER, "ListingAddresses", "ListingAddresses"))
            for (i in 0 until suggestions.listingAddresses.size) {
                cursor.addRow(arrayOf(
                        Integer.toString(index),
                        "listingAddresses",
                        suggestions.listingAddresses.get(i).displayText,
                        suggestions.listingAddresses.get(i)))
                index++
            }
        }
        if (suggestions.locations != null) {
            cursor.addRow(arrayOf(2, COLUMN_TYPE_HEADER, "Locations", "Locations"))
            for (i in 0 until suggestions.locations.size) {
                cursor.addRow(arrayOf(
                        Integer.toString(index),
                        "locations",
                        suggestions.locations.get(i).locationName,
                        suggestions.locations.get(i)))
                index++
            }
        }
        if (suggestions.streets != null) {
            cursor.addRow(arrayOf(3, COLUMN_TYPE_HEADER, "Streets", "Streets"))
            for (i in 0 until suggestions.streets.size) {
                cursor.addRow(arrayOf(
                        Integer.toString(index),
                        "streets",
                        suggestions.streets.get(i).streetName,
                        suggestions.streets.get(i)))
                index++
            }
        }
        return cursor
    }
}