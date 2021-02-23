package com.pmacademy.githubclient

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pmacademy.githubclient.data.pref.SharedPref
import com.pmacademy.githubclient.ui.FragmentNavigator

class MainActivity : AppCompatActivity() {

    val fragmentNavigator by lazy {
        FragmentNavigator(
            supportFragmentManager,
            R.id.container
        )
    }

    private val sharedPreferences by lazy {
        SharedPref(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences.token = ""
        fragmentNavigator.showLoginFragment()
    }
}