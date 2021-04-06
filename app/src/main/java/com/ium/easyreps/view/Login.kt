package com.ium.easyreps.view

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.ium.easyreps.R
import com.ium.easyreps.util.Config
import com.ium.easyreps.util.NetworkUtil
import com.ium.easyreps.util.ServerRequest
import com.ium.easyreps.viewmodel.UserVM

class Login : Fragment() {
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var mView: View
    var user = UserVM.user.value

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

            canLog(username.trim(), password.trim())
        }

        mView.findViewById<Button>(R.id.courseButton).setOnClickListener {
            findNavController().navigate(R.id.login_to_courses)
        }

        return mView
    }

    private fun canLog(username: String, password: String) {
        val usernameField = mView.findViewById<TextInputLayout>(R.id.usernameTextInput)
        val passwordField = mView.findViewById<TextInputLayout>(R.id.passwordTextInput)
        usernameField.isErrorEnabled = false
        passwordField.isErrorEnabled = false

        if (areInputValid(username, password) && isPhoneConnected()) {
            context?.let {
                ServerRequest.login(it, username, password) {
                    if (user?.isLogged == true)
                        findNavController().navigate(R.id.login_to_account)
                    else {
                        usernameField.isErrorEnabled = true
                        usernameField.error = getString(R.string.wrong_credential)
                        passwordField.isErrorEnabled = true
                        passwordField.error = getString(R.string.wrong_credential)
                    }
                }
            }
        }
    }

    private fun areInputValid(username: String, password: String): Boolean {
        val usernameField = mView.findViewById<TextInputLayout>(R.id.usernameTextInput)
        val passwordField = mView.findViewById<TextInputLayout>(R.id.passwordTextInput)

        if (username.isEmpty()) {
            usernameField.isErrorEnabled = true
            usernameField.error = getString(R.string.username_null_error)
            return false
        }
        if (!username.all { it.isLetterOrDigit() }) {
            usernameField.isErrorEnabled = true
            usernameField.error = getString(R.string.username_invalid_error)
            return false
        }
        if (password.isEmpty()) {
            passwordField.isErrorEnabled = true
            passwordField.error = getString(R.string.password_null_error)
            return false
        }
        if (!username.all { it.isLetterOrDigit() } /*!password.matches(passwordPattern)*/) {
            passwordField.isErrorEnabled = true
            passwordField.error = getString(R.string.password_invalid_error)
            return false
        }

        return true
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    private fun isPhoneConnected(): Boolean {
        val usernameField = mView.findViewById<TextInputLayout>(R.id.usernameTextInput)
        val passwordField = mView.findViewById<TextInputLayout>(R.id.passwordTextInput)
        val isConnected = NetworkUtil.checkConnection(context)

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
                activity?.finish()
                return true
            }
            R.id.aboutItem -> {
                findNavController().navigate(R.id.login_to_about)
                return true
            }
        }

        return false
    }
}