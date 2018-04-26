package com.ragaisis.sunday.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import com.ragaisis.sunday.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        transitionFragment(SearchFragment())
    }

    private fun transitionFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_placeholder_layout, fragment)
                .commit()
        supportFragmentManager.executePendingTransactions()
    }
}
