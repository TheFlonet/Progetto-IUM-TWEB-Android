package com.ium.easyreps.view

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.ium.easyreps.R
import com.ium.easyreps.adapter.TabLessonsAdapter
import com.ium.easyreps.model.User
import com.ium.easyreps.util.Day
import com.ium.easyreps.viewmodel.UserVM

class CoursesView : Fragment() {
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var tabAdapter: TabLessonsAdapter
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
        mView = inflater.inflate(R.layout.fragment_courses_view, container, false) as View

        toolbar = mView.findViewById(R.id.appToolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setupTab()

        return mView
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.aboutItem -> {
                findNavController().navigate(R.id.course_to_about)
                return true
            }
            android.R.id.home -> {
                if (UserVM.user.value!!.isLogged)
                    findNavController().navigate(R.id.course_to_account)
                else
                    findNavController().navigate(R.id.course_to_login)

                return true
            }
        }

        return false
    }

    private fun setupTab() {
        tabAdapter =
            activity?.let {
                TabLessonsAdapter(
                    it.supportFragmentManager, this.lifecycle
                )
            }!!
        context?.let { tabAdapter.initFragments(it) }
        val tabPager = mView.findViewById<ViewPager2?>(R.id.tabViewPager)
        tabPager.adapter = tabAdapter
        tabLayout = mView.findViewById(R.id.tabLayout)
        TabLayoutMediator(tabLayout, tabPager) { tab: TabLayout.Tab, position: Int ->
            when (position) {
                0 -> {
                    tab.text = Day.MON.toString()
                }
                1 -> {
                    tab.text = Day.TUE.toString()
                }
                2 -> {
                    tab.text = Day.WED.toString()
                }
                3 -> {
                    tab.text = Day.THU.toString()
                }
                4 -> {
                    tab.text = Day.FRI.toString()
                }
            }
        }.attach()
    }
}