package com.ium.easyreps.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.ium.easyreps.R
import com.ium.easyreps.adapter.TabHistoryAdapter
import com.ium.easyreps.util.State

class HistoryView : Fragment() {
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var tabAdapter: TabHistoryAdapter
    private lateinit var tabLayout: TabLayout
    private lateinit var mView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mView = inflater.inflate(R.layout.fragment_history_view, container, false)

        toolbar = mView.findViewById(R.id.appToolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setupTab()

        return mView
    }

    private fun setupTab() {
        tabAdapter =
            activity?.let { TabHistoryAdapter(it.supportFragmentManager, this.lifecycle) }!!
        context?.let { tabAdapter.initFragments(it) }
        val tabPager = mView.findViewById<ViewPager2?>(R.id.tabViewPager)
        tabPager.adapter = tabAdapter
        tabLayout = mView.findViewById(R.id.tabLayout)
        TabLayoutMediator(tabLayout, tabPager) { tab: TabLayout.Tab, position: Int ->
            when (position) {
                0 -> {
                    tab.text = State.ACTIVE.toString()
                }
                1 -> {
                    tab.text = State.DONE.toString()
                }
                2 -> {
                    tab.text = State.CANCELLED.toString()
                }
            }
        }.attach()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                findNavController().navigate(R.id.history_to_account)
                return true
            }
        }

        return false
    }
}