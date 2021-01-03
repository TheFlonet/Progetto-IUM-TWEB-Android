package com.ium.easyreps.view

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.ium.easyreps.R

class Login : Fragment() {
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
        mView = inflater.inflate(R.layout.fragment_login, container, false)

        toolbar = mView.findViewById(R.id.appToolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mView.findViewById<Button>(R.id.loginButton).setOnClickListener {
            val username =
                mView.findViewById<AppCompatEditText>(R.id.usernameEditTxt).text.toString()
            val password =
                mView.findViewById<AppCompatEditText>(R.id.passwordEditTxt).text.toString()
            if (canLog(username.trim(), password.trim())) {
                findNavController().navigate(R.id.login_to_account)
            }
        }

        return mView
    }

    private fun canLog(username: String, password: String): Boolean {
        val usernameField = mView.findViewById<TextInputLayout>(R.id.usernameTextInput)
        val passwordField = mView.findViewById<TextInputLayout>(R.id.passwordTextInput)
        usernameField.isErrorEnabled = false
        passwordField.isErrorEnabled = false

        if (!areInputValid(username, password)) return false
        if (!isPhoneConnected()) return false

        if (false) { // TODO richiesta al server
            usernameField.isErrorEnabled = true
            usernameField.error = getString(R.string.wrong_credential)
            passwordField.isErrorEnabled = true
            passwordField.error = getString(R.string.wrong_credential)
            return false
        }

        return true
    }

    private fun areInputValid(username: String, password: String): Boolean {
        val passwordPattern = ("^" +
                "(?=.*[0-9])" +         //at least 1 digit
                "(?=.*[a-z])" +         //at least 1 lower case letter
                "(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{6,}" +               //at least 4 characters
                "$").toRegex()
        val usernameField = mView.findViewById<TextInputLayout>(R.id.usernameTextInput)
        val passwordField = mView.findViewById<TextInputLayout>(R.id.passwordTextInput)

        if (username == "") {
            usernameField.isErrorEnabled = true
            usernameField.error = getString(R.string.username_null_error)
            return false
        }
        if (!username.all { it.isLetterOrDigit() }) {
            usernameField.isErrorEnabled = true
            usernameField.error = getString(R.string.username_invalid_error)
            return false
        }
        if (password == "") {
            passwordField.isErrorEnabled = true
            passwordField.error = getString(R.string.password_null_error)
            return false
        }
        if (!password.matches(passwordPattern)) {
            passwordField.isErrorEnabled = true
            passwordField.error = getString(R.string.password_invalid_error)
            return false
        }

        return true
    }

    private fun isPhoneConnected(): Boolean {
        val usernameField = mView.findViewById<TextInputLayout>(R.id.usernameTextInput)
        val passwordField = mView.findViewById<TextInputLayout>(R.id.passwordTextInput)
        val connectivityManager =
            context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        val isConnected = networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true ||
                networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) == true ||
                networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true

        if (!isConnected) {
            Snackbar.make(mView, getString(R.string.no_connectivity), Snackbar.LENGTH_LONG).show()
            usernameField.isErrorEnabled = true
            usernameField.error = getString(R.string.no_connection_error)
            passwordField.isErrorEnabled = true
            passwordField.error = getString(R.string.no_connection_error)
        }

        return isConnected
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                findNavController().navigate(R.id.login_to_home)
                return true
            }
        }

        return false
    }
}