package com.pmacademy.githubclient.di

import android.content.Context
import com.pmacademy.githubclient.Application
import com.pmacademy.githubclient.ui.MainActivity
import com.pmacademy.githubclient.ui.screens.issueinfo.IssueInfoFragment
import com.pmacademy.githubclient.ui.screens.login.LoginFragment
import com.pmacademy.githubclient.ui.screens.repoinfo.RepoInfoFragment
import com.pmacademy.githubclient.ui.screens.search.UsersSearchFragment
import com.pmacademy.githubclient.ui.screens.userinfo.UserInfoFragment
import dagger.Component
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Singleton

@ExperimentalSerializationApi
@Component(modules = [AppModule::class])
@Singleton
interface AppComponent {

    fun context(): Context

    fun inject(activity: MainActivity)
    fun inject(fragment: IssueInfoFragment)
    fun inject(fragment: UserInfoFragment)
    fun inject(fragment: RepoInfoFragment)
    fun inject(fragment: UsersSearchFragment)
    fun inject(fragment: LoginFragment)

}