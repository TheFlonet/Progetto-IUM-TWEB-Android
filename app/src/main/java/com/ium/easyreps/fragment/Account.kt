package com.ium.easyreps.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.ium.easyreps.R

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

        mView.findViewById<Button>(R.id.historyButton).setOnClickListener {
            findNavController().navigate(R.id.account_to_history)
        }

        mView.findViewById<Button>(R.id.logoutButton).setOnClickListener {
            findNavController().navigate(R.id.account_to_login)
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