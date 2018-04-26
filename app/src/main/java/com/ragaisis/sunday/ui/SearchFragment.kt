package com.ragaisis.sunday.ui

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.SearchView
import android.view.*
import com.ragaisis.sunday.MyApplication
import com.ragaisis.sunday.R
import com.ragaisis.sunday.helpers.ApplicationHelper

class SearchFragment : Fragment() {

    private var searchView: SearchView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity!!.application as MyApplication).mainComponent.inject(this)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false) as ViewGroup
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView?.maxWidth = ApplicationHelper.getScreenWidth(activity)
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView?.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        searchView?.setOnSearchClickListener {
            (activity as MainActivity).supportActionBar?.setDisplayShowTitleEnabled(false)
        }
        searchView?.setOnCloseListener({
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
