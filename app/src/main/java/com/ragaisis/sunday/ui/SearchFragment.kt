package com.ragaisis.sunday.ui

import android.app.SearchManager
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.database.MatrixCursor
import android.os.Bundle
import android.provider.BaseColumns
import android.support.v4.app.Fragment
import android.support.v4.widget.SimpleCursorAdapter
import android.support.v7.widget.SearchView
import android.view.*
import com.ragaisis.sunday.MyApplication
import com.ragaisis.sunday.R
import com.ragaisis.sunday.entities.AddressSuggestResponse
import com.ragaisis.sunday.helpers.ApplicationHelper
import com.ragaisis.sunday.viewmodels.SearchViewModel
import javax.inject.Inject


class SearchFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: SearchViewModel
    private lateinit var suggestionAdapter: SimpleCursorAdapter
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity!!.application as MyApplication).mainComponent.inject(this)
        setHasOptionsMenu(true)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel::class.java)
        viewModel.addressSuggestions.observe(this, Observer {
            if (it != null) {
                onAddressSuggestion(it)
            }
        })
    }

    private fun onAddressSuggestion(suggestions: AddressSuggestResponse) {
        if (suggestions.listingAddresses != null) {
            val columns = arrayOf(
                    BaseColumns._ID,
                    SearchManager.SUGGEST_COLUMN_TEXT_1,
                    SearchManager.SUGGEST_COLUMN_INTENT_DATA)
            val cursor = MatrixCursor(columns)
            for (i in 0 until suggestions.listingAddresses.size) {
                cursor.addRow(arrayOf(
                        Integer.toString(i),
                        suggestions.listingAddresses.get(i).streetName,
                        suggestions.listingAddresses.get(i)))
            }
            suggestionAdapter.swapCursor(cursor)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false) as ViewGroup
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.maxWidth = ApplicationHelper.getScreenWidth(activity)
        suggestionAdapter = SimpleCursorAdapter(context,
                R.layout.view_search_suggestion,
                null,
                arrayOf(SearchManager.SUGGEST_COLUMN_TEXT_1),
                intArrayOf(android.R.id.text1),
                0)
        searchView.suggestionsAdapter = suggestionAdapter
        searchView.setOnSuggestionListener(object : SearchView.OnSuggestionListener {
            override fun onSuggestionSelect(position: Int): Boolean {
                return false
            }

            override fun onSuggestionClick(position: Int): Boolean {
                val suggestions = viewModel.addressSuggestions.value
                if (suggestions?.listingAddresses != null) {
                    searchView.setQuery(suggestions.listingAddresses.get(position).streetName, true)
                }
                searchView.clearFocus()
                return true
            }
        })
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.search(newText)
                return true
            }
        })
        searchView.setOnSearchClickListener {
            (activity as MainActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        }
        searchView.setOnCloseListener({
            (activity as MainActivity).supportActionBar?.setDisplayShowTitleEnabled(true)
            false
        })
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.clearFocus()
        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
        super.onCreateOptionsMenu(menu, inflater)
    }

}
