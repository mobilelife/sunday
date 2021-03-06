package com.ragaisis.sunday.ui

import android.app.SearchManager
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.*
import android.widget.Toast
import com.ragaisis.sunday.MyApplication
import com.ragaisis.sunday.R
import com.ragaisis.sunday.adapters.SuggestionAdapter
import com.ragaisis.sunday.cursors.SuggestionCursorAdapter
import com.ragaisis.sunday.helpers.ApplicationHelper
import com.ragaisis.sunday.viewmodels.SearchViewModel
import kotlinx.android.synthetic.main.fragment_search.*
import javax.inject.Inject


class SearchFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: SearchViewModel
    private lateinit var suggestionAdapter: SuggestionCursorAdapter
    private lateinit var searchView: SearchView
    private lateinit var adapter: SuggestionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity!!.application as MyApplication).mainComponent.inject(this)
        setHasOptionsMenu(true)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel::class.java)
        initViewModels()
    }

    private fun initViewModels() {
        viewModel.addressSuggestions.observe(this, Observer {
            suggestionAdapter.swapCursor(it)
        })
        viewModel.error.observe(this, Observer {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.error.value = null
        })
        viewModel.suggestions.observe(this, Observer {
            adapter.items = it
            updateListVisibility()
        })
    }

    private fun updateListVisibility() {
        if (adapter.items != null && adapter.items!!.isNotEmpty()) {
            emptyPlaceholderTextView.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        } else {
            emptyPlaceholderTextView.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false) as ViewGroup
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        emptyPlaceholderTextView.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
        val list = viewModel.suggestions.value
        adapter = SuggestionAdapter(context!!, if (list != null) list else emptyList())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        updateListVisibility()
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.maxWidth = ApplicationHelper.getScreenWidth(activity)
        suggestionAdapter = SuggestionCursorAdapter(context, null, true)
        searchView.suggestionsAdapter = suggestionAdapter
        searchView.setOnSuggestionListener(object : SearchView.OnSuggestionListener {
            override fun onSuggestionSelect(position: Int): Boolean {
                return false
            }

            override fun onSuggestionClick(position: Int): Boolean {
                val suggestions = viewModel.addressSuggestions.value
                if (suggestions != null) {
                    val query = suggestions.getString(suggestions.getColumnIndex(SearchManager.SUGGEST_COLUMN_TEXT_1))
                    searchView.setQuery(query, true)
                    viewModel.getAddressDetails(query)
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
