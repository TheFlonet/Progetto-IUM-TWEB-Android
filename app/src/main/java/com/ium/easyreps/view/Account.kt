package com.ium.easyreps.view

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.ium.easyreps.R
import com.ium.easyreps.model.User
import com.ium.easyreps.util.Config
import com.ium.easyreps.util.NetworkUtil
import com.ium.easyreps.util.ServerRequest
import com.ium.easyreps.viewmodel.UserVM

class Account : Fragment() {
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var mView: View

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

        mView.findViewById<TextView>(R.id.userTextView).text = UserVM.user.value?.name

        mView.findViewById<Button>(R.id.historyButton).setOnClickListener {
            findNavController().navigate(R.id.account_to_history)
        }

        mView.findViewById<Button>(R.id.coursesButton).setOnClickListener {
            findNavController().navigate(R.id.account_to_courses)
        }

        mView.findViewById<Button>(R.id.logoutButton).setOnClickListener {
            if (NetworkUtil.checkConnection(context)) {
                context?.let { it1 ->
                    ServerRequest.logout(it1) { findNavController().navigate(R.id.account_to_login) }
                }
            } else {
                Toast.makeText(context, getString(R.string.no_connectivity), Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                context?.let { ServerRequest.logout(it) {} }
                activity?.finish()
                return true
            }
            R.id.aboutItem -> {
                findNavController().navigate(R.id.account_to_about)
                return true
            }
        }

        return false
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }
}
