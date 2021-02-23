package com.pmacademy.githubclient

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.pmacademy.githubclient.data.model.UserResponse
import com.pmacademy.githubclient.data.pref.SharedPref
import com.pmacademy.githubclient.ui.FragmentNavigator
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
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
        Log.d("TAGtoken", "onCreate: token - ${sharedPreferences.token}")
        if (sharedPreferences.token == "") {
            fragmentNavigator.showLoginFragment()
        } else {
            fragmentNavigator.showUserInfoFragment(
                UserResponse(
                    login = sharedPreferences.localUserName,
                    avatarUrl = sharedPreferences.localUserAvatarUrl
                ), addToBackStack = false
            )
        }
    }
}