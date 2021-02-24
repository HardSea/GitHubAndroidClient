package com.pmacademy.githubclient.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.pmacademy.githubclient.R
import com.pmacademy.githubclient.data.model.UsersSearchResponce
import com.pmacademy.githubclient.databinding.FragmentUsersSearchBinding
import com.pmacademy.githubclient.tools.GithubError
import com.pmacademy.githubclient.ui.adapter.UsersSearchAdapter
import com.pmacademy.githubclient.ui.base.BaseFragment
import com.pmacademy.githubclient.ui.viewmodel.UsersSearchViewModel
import com.pmacademy.myapplicationtemp.data.ReposResponse
import kotlinx.serialization.ExperimentalSerializationApi


@ExperimentalSerializationApi
class UsersSearchFragment : BaseFragment(R.layout.fragment_users_search) {

    private lateinit var binding: FragmentUsersSearchBinding
    private val viewModel: UsersSearchViewModel by viewModels()
    lateinit var usersSearchAdapter: UsersSearchAdapter
  //  private lateinit var user: UsersSearchResponce

    companion object {
        private const val KEY_USER1 = "KEY_USER1"

        fun newInstance(): UsersSearchFragment = UsersSearchFragment().apply {
            val bundle = Bundle()
          // bundle.putSerializable(KEY_USER1, user)
            this.arguments = bundle
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUsersSearchBinding.inflate(inflater, container, false)
        return binding.root
        initRecyclerViews()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupButtonListener(sharedPreferences.token)
        observeGitHubRepos()
        //initRecyclerViews()
    }

    private fun setupButtonListener(authToken: String) {
        binding.btnSerach.setOnClickListener {
            getUsersSearch(authToken)
        }
    }

    private fun getUsersSearch(authToken: String) {
        viewModel.getUsersSearch(getUserName(), authToken)
        setupFragmentView(getUserName())
    }

    private fun getUserName(): String {
        val username1 = binding.etSearch.text.toString()
        if (username1.isNotEmpty()){
            return username1
        }else {
            Toast.makeText(context, "UserName field is empty" ,Toast.LENGTH_LONG).show()
            return "Not name"
        }
    }

    private fun setupFragmentView(userName:String) {
        binding.tvUserName.text = "5"
        binding.tvURL.text = viewModel.userSearchLiveData.value.toString()
        loadAvatar("https://www.thoughtco.com/thmb/pu-0U6Z--3DBkmbSFEU2UwC34EM=/2795x2096/smart/filters:no_upscale()/acat-874e4928f96e4e96bec0b1723ca5a909.jpg")
    }

    private fun loadAvatar(imageUrl: String) {
        Glide.with(requireContext())
            .load(imageUrl)
            .circleCrop()
            .into(binding.ivLogo)
    }

    private fun initRecyclerViews() {
        binding.rvTest.adapter = usersSearchAdapter
        binding.rvTest.layoutManager = LinearLayoutManager(context)
    }

    private fun updateGitHubRepos(items: List<UsersSearchResponce>) {
        usersSearchAdapter?.updateAdapter(items)
    }

    private fun observeGitHubRepos() {
        viewModel.userSearchLiveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            updateGitHubRepos(it.successResult)
        })
    }

}