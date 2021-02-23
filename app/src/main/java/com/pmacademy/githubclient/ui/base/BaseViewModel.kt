package com.pmacademy.githubclient.ui.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel

abstract class BaseViewModel : ViewModel() {

    private val job = Job()

    private val backgroundScope get() = Dispatchers.IO.plus(job)

    override fun onCleared() {
        super.onCleared()
        backgroundScope.cancel()
    }
}