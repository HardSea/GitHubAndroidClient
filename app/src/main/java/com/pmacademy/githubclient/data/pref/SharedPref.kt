package com.pmacademy.githubclient.data.pref

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import javax.inject.Inject

class SharedPref @Inject constructor(context: Context) {

    private companion object {
        const val KEY_TOKEN = "KEY_TOKEN"
        const val KEY_LOCAL_USER_NAME = "KEY_LOCAL_USER_NAME"
        const val KEY_LOCAL_USER_AVATAR_URL = "KEY_LOCAL_USER_AVATAR_URL"
    }

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences("main_shared_preferences", MODE_PRIVATE)
    }

    var token: String by SharedPrefDelegate(sharedPreferences, KEY_TOKEN, "")

    var localUserName: String by SharedPrefDelegate(sharedPreferences, KEY_LOCAL_USER_NAME, "")
    var localUserAvatarUrl: String by SharedPrefDelegate(
        sharedPreferences,
        KEY_LOCAL_USER_AVATAR_URL,
        ""
    )

}