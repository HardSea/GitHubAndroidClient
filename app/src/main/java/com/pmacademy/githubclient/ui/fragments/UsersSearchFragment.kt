package com.pmacademy.githubclient.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.pmacademy.githubclient.R
import com.pmacademy.githubclient.data.model.UserResponse
import com.pmacademy.githubclient.databinding.FragmentUsersSearchBinding
import com.pmacademy.githubclient.tools.GithubError
import com.pmacademy.githubclient.ui.adapter.UsersSearchAdapter
import com.pmacademy.githubclient.ui.base.BaseFragment
import com.pmacademy.githubclient.ui.viewmodel.UsersSearchViewModel
import kotlinx.serialization.ExperimentalSerializationApi


@ExperimentalSerializationApi
class UsersSearchFragment : BaseFragment(R.layout.fragment_users_search) {

    private lateinit var binding: FragmentUsersSearchBinding
    private val viewModel: UsersSearchViewModel by viewModels()
    var usersSearchAdapter: UsersSearchAdapter = UsersSearchAdapter { user ->
        navigator.showUserInfoFragment(user)
    }

    companion object {
        fun newInstance(): UsersSearchFragment = UsersSearchFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUsersSearchBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupButtonListener()
        observeGitHubRepos()
        initRecyclerViews()
    }

    private fun setupButtonListener() {
        binding.btnSearch.setOnClickListener {
            getUsersSearch()
        }
    }

    private fun getUsersSearch() {
        viewModel.getUsersSearch(binding.etSearchInput.text.toString(), sharedPreferences.token)
    }

    private fun initRecyclerViews() {
        binding.rvResultSearch.adapter = usersSearchAdapter
        binding.rvResultSearch.layoutManager = LinearLayoutManager(context)
    }

    private fun observeGitHubRepos() {
        viewModel.userSearchLiveData.observe(viewLifecycleOwner, {
            if (it.isError) {
                handleError(it.errorResult)
            } else {
                updateSearchUsers(it.successResult)
            }
        })
    }

    private fun handleError(errorResult: GithubError) {
        when (errorResult) {
            GithubError.UNAUTHORIZED -> {
                Toast.makeText(requireContext(), "Need authorization", Toast.LENGTH_SHORT).show()
                navigator.showLoginFragment()
            }
            else -> Log.d(
                "SearchLog",
                "UsersSearchFragment ->  handleError() -> when (errorResult)-> else ($errorResult)"
            )
        }
    }

    private fun updateSearchUsers(items: List<UserResponse>) {
        if (items.isNotEmpty()) {
            usersSearchAdapter.updateUsersList(items)
        } else {
            Log.d("SearchLog", "updateReposList -> showEmptyReposTitle()")
        }
    }

}