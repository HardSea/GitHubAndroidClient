package com.pmacademy.githubclient.data.pref

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class SharedPref(context: Context) {

  private companion object {
    const val KEY_TOKEN = "KEY_TOKEN"
    const val KEY_LAST = "KEY_LAST"
  }

  private val sharedPreferences: SharedPreferences by lazy {
    context.getSharedPreferences("main_shared_preferences", MODE_PRIVATE)
  }

  var token: String by SharedPrefDelegate(sharedPreferences, KEY_TOKEN, "")

  val bool: Boolean by SharedPrefDelegate(sharedPreferences, KEY_LAST, true)

}