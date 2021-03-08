package com.ium.easyreps.adapter

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.ium.easyreps.R
import com.ium.easyreps.util.Config
import com.ium.easyreps.model.Course
import com.ium.easyreps.model.Reservation
import com.ium.easyreps.model.Teacher
import com.ium.easyreps.model.User
import com.ium.easyreps.util.Day
import com.ium.easyreps.util.ServerRequest
import com.ium.easyreps.util.State
import com.ium.easyreps.view.HistoryList
import com.ium.easyreps.viewmodel.UserVM
import org.json.JSONObject

class TabHistoryAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    private var fragments = ArrayList<HistoryList>(3)
    private var user = UserVM.user.value

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    fun initFragments(context: Context) {
        ServerRequest.getHistory(context)
    }
}
