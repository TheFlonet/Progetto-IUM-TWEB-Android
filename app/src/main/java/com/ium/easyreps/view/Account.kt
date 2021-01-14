package com.ium.easyreps.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.ium.easyreps.R
import com.ium.easyreps.model.User
import com.ium.easyreps.util.Config
import com.ium.easyreps.util.NetworkUtil
import com.ium.easyreps.viewmodel.UserVM

class Account : Fragment() {
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var mView: View
    private val model: UserVM by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mView = inflater.inflate(R.layout.fragment_account, container, false)

        toolbar = mView.findViewById(R.id.appToolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mView.findViewById<Button>(R.id.historyButton).setOnClickListener {
            findNavController().navigate(R.id.account_to_history)
        }

        mView.findViewById<Button>(R.id.logoutButton).setOnClickListener {
            logoutRequest()
            if (model.currentUser.value?.isLogged == false)
                findNavController().navigate(R.id.account_to_login)
        }
    }

    private fun logoutRequest() {
        if (NetworkUtil.checkConnection(context)) {
            val logout = JsonObjectRequest(
                Request.Method.GET,
                "${Config.ip}:${Config.port}/${Config.servlet}?action=${Config.logout}",
                null,
                { model.currentUser.value = User() },
                {
                    Toast.makeText(context, getString(R.string.error_logout), Toast.LENGTH_LONG)
                        .show()
                })
            Volley.newRequestQueue(context).add(logout)
        } else {
            Toast.makeText(context, getString(R.string.no_connectivity), Toast.LENGTH_LONG).show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                findNavController().navigate(R.id.account_to_courses)
                return true
            }
        }

        return false
    }
}