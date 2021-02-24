package com.pmacademy.githubclient.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
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
    var usersSearchAdapter: UsersSearchAdapter = UsersSearchAdapter { reposName ->
        navigator.showProjectInfoFragment(reposName, user.login)
    }
    private lateinit var user: UserResponse

    companion object {
        private const val KEY_USER1 = "KEY_USER1"

        fun newInstance(): UsersSearchFragment = UsersSearchFragment().apply {
            val bundle = Bundle()
            // bundle.putSerializable(KEY_USER1, user)
            this.arguments = bundle
            Log.d("StarWars", "UsersSearchFragment ->  companion object -> newInstance()")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUsersSearchBinding.inflate(inflater, container, false)
        Log.d("StarWars", "UsersSearchFragment ->  onCreateView()")
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupButtonListener(sharedPreferences.token)
        observeGitHubRepos()
        initRecyclerViews()
        Log.d("StarWars", "UsersSearchFragment ->  onViewCreated()")
    }

    private fun setupButtonListener(authToken: String) {
        binding.btnSerach.setOnClickListener {
            getUsersSearch(authToken)
        }
    }

    private fun getUsersSearch(authToken: String) {
        viewModel.getUsersSearch(getUserName(), authToken)
        //setupFragmentView(getUserName())
    }

    private fun getUserName(): String {
        val username1 = binding.etSearch.text.toString()
        if (username1.isNotEmpty()) {
            return username1
        } else {
            Toast.makeText(context, "UserName field is empty", Toast.LENGTH_LONG).show()
            return "Not name"
        }
    }

    private fun initRecyclerViews() {
        binding.rvTest.adapter = usersSearchAdapter
        binding.rvTest.layoutManager = LinearLayoutManager(context)
        Log.d("SearchLog", "UsersSearchFragment ->  initRecyclerViews()")
    }

    private fun observeGitHubRepos() {
        viewModel.userSearchLiveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (!it.isLoading) {
                if (it.isError) {
                    handleError(it.errorResult)
                    Log.d(
                        "SearchLog",
                        "UsersSearchFragment ->  observeGitHubRepos() -> observe()-> if (errorResult)"
                    )
                } else {
                    Log.d(
                        "SearchLog",
                        "UsersSearchFragment ->  observeGitHubRepos() -> observe()-> else (successResult)"
                    )
                    updateSearchUsers(it.successResult)
                }
            }
        })
    }

    private fun handleError(errorResult: GithubError) {
        when (errorResult) {
            GithubError.UNAUTHORIZED -> {
                Toast.makeText(requireContext(), "Need authorization", Toast.LENGTH_SHORT).show()
                navigator.showLoginFragment()
            }
            else ->  Log.d(
                    "SearchLog",
                    "UsersSearchFragment ->  handleError() -> when (errorResult)-> else ($errorResult)")
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