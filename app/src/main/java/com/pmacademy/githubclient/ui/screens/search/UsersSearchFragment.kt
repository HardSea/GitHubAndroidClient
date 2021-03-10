package com.pmacademy.githubclient.ui.screens.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.pmacademy.githubclient.Application
import com.pmacademy.githubclient.R
import com.pmacademy.githubclient.data.model.UserResponse
import com.pmacademy.githubclient.databinding.FragmentUsersSearchBinding
import com.pmacademy.githubclient.ui.base.BaseFragment
import com.pmacademy.githubclient.ui.screens.search.adapter.UsersSearchAdapter
import kotlinx.serialization.ExperimentalSerializationApi
import javax.inject.Inject


@ExperimentalSerializationApi
class UsersSearchFragment : BaseFragment(R.layout.fragment_users_search) {

    private lateinit var binding: FragmentUsersSearchBinding

    @Inject
    lateinit var viewModel: UsersSearchViewModel
    private var usersSearchAdapter: UsersSearchAdapter = UsersSearchAdapter { user ->
        navigator.showUserInfoFragment(user)
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
        viewModel.getUsersSearch(binding.etSearchInput.text.toString())
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

    override fun showErrorMessage(errorRes: Int) {
        binding.rvResultSearch.visibility = View.INVISIBLE
        binding.tvErrorMessage.visibility = View.VISIBLE
        binding.tvErrorMessage.text = getString(errorRes)
    }

    private fun updateSearchUsers(items: List<UserResponse>) {
        if (items.isNotEmpty()) {
            usersSearchAdapter.updateUsersList(items)
        }
    }

    companion object {
        fun newInstance(): UsersSearchFragment = UsersSearchFragment()
    }

    override fun setupDi() {
        val app = requireActivity().application as Application
        app.getComponent().inject(this)
    }
}