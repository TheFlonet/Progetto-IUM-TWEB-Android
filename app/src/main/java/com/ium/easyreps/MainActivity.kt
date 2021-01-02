package com.ium.easyreps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.ium.easyreps.dto.User

class MainActivity : AppCompatActivity() {
    lateinit var userInfo: MutableLiveData<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        userInfo = MutableLiveData()
        userInfo.value = User()
    }
}