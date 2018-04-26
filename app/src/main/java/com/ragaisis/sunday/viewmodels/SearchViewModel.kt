package com.ragaisis.sunday.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.ragaisis.sunday.api.SundayApi
import com.ragaisis.sunday.entities.AddressSuggestResponse
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SearchViewModel @Inject constructor(private val api: SundayApi) : ViewModel() {

    var addressSuggestions: MutableLiveData<AddressSuggestResponse> = MutableLiveData()
    var searchQuery: String? = null

    fun search(query: String) {
        searchQuery = query
        if (query.isNotEmpty()) {
            api.getSuggestedAddress(query)
                    .subscribeOn(Schedulers.io())
                    .subscribe { addressSuggestions.postValue(it) }
        }
    }
}