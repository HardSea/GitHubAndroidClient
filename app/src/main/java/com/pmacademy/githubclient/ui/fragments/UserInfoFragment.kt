package com.pmacademy.githubclient.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.pmacademy.githubclient.R
import com.pmacademy.githubclient.databinding.UserInfoFragmentBinding
import com.pmacademy.githubclient.ui.adapter.ReposListAdapter
import com.pmacademy.githubclient.ui.base.BaseFragment
import com.pmacademy.githubclient.ui.viewmodel.UserInfoViewModel
import com.pmacademy.myapplicationtemp.data.ReposResponse
import com.pmacademy.githubclient.data.model.UserResponse
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class UserInfoFragment : BaseFragment(R.layout.user_info_fragment) {

    private val recyclerViewPostsAdapter = ReposListAdapter()
    private lateinit var binding: UserInfoFragmentBinding
    private val viewModel: UserInfoViewModel by viewModels()
    private lateinit var user: UserResponse

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        user = requireArguments().getSerializable(KEY_USER) as UserResponse
        binding = UserInfoFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUserReposList(user)
        initRecyclerView()
        observeRepos()
    }

    private fun initRecyclerView() {
        binding.rvListRepositories.adapter = recyclerViewPostsAdapter
        binding.rvListRepositories.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun updateReposList(items: List<ReposResponse>) {
        recyclerViewPostsAdapter.updateReposList(items)
    }

    private fun observeRepos() {
        viewModel.reposLiveData.observe(viewLifecycleOwner, {
            if (it != null) {
                updateReposList(it)
            }
        })
    }

    companion object {
        private const val KEY_USER = "KEY_USER"

        fun newInstance(user: UserResponse): UserInfoFragment = UserInfoFragment().apply {
            val bundle = Bundle()
            bundle.putSerializable(KEY_USER, user)
            this.arguments = bundle
        }
    }
}
