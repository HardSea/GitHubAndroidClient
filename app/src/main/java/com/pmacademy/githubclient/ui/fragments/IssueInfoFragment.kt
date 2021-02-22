package com.pmacademy.githubclient.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.pmacademy.githubclient.R
import com.pmacademy.githubclient.data.model.IssueCommentResponse
import com.pmacademy.githubclient.data.model.IssueResponse
import com.pmacademy.githubclient.databinding.IssueInfoFragmentBinding
import com.pmacademy.githubclient.ui.adapter.IssueCommentsAdapter
import com.pmacademy.githubclient.ui.base.BaseFragment
import com.pmacademy.githubclient.ui.viewmodel.IssueCommentsViewModel
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
class IssueInfoFragment : BaseFragment(R.layout.issue_info_fragment) {

    private lateinit var binding: IssueInfoFragmentBinding
    private val viewModel: IssueCommentsViewModel by viewModels()
    private lateinit var issue: IssueResponse
    private lateinit var userName: String
    private lateinit var repoName: String
    private val rvIssueCommentsAdapter = IssueCommentsAdapter { Log.d("TAG11", "Click on comment") }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        issue = requireArguments().getSerializable(KEY_ISSUE) as IssueResponse
        userName = requireArguments().getString(KEY_USER_NAME).toString()
        repoName = requireArguments().getString(KEY_REPO_NAME).toString()
        binding = IssueInfoFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        observeIssueCommentsLiveData()
        viewModel.getIssueComments(
            userName = userName,
            repoName = repoName,
            issueNumber = issue.number
        )
        setupView()
    }

    private fun setupView() {
        binding.tvIssueAuthor.text = userName
        binding.tvIssueTitle.text = issue.title
        binding.tvIssueBody.text = issue.body
    }


    private fun observeIssueCommentsLiveData() {
        viewModel.issueCommentsLiveData.observe(viewLifecycleOwner, {
            if (it.isError) {
                //showErrorMessage(it.errorResult)
            } else {
                updateIssueCommentsList(it.successResult)
            }
        })

    }

    private fun updateIssueCommentsList(commentsList: List<IssueCommentResponse>) {
        rvIssueCommentsAdapter.updateIssuesList(commentsList)
    }

    private fun initRecyclerView() {
        binding.rvIssueComments.adapter = rvIssueCommentsAdapter
        binding.rvIssueComments.layoutManager = LinearLayoutManager(requireContext())
    }


    companion object {
        private const val KEY_ISSUE = "KEY_ISSUE"
        private const val KEY_USER_NAME = "KEY_USER_NAME"
        private const val KEY_REPO_NAME = "KEY_REPO_NAME"

        fun newInstance(
            issue: IssueResponse,
            userName: String,
            repoName: String
        ): IssueInfoFragment =
            IssueInfoFragment().apply {
                val bundle = Bundle()
                bundle.putSerializable(KEY_ISSUE, issue)
                bundle.putString(KEY_USER_NAME, userName)
                bundle.putString(KEY_REPO_NAME, repoName)
                this.arguments = bundle
            }
    }
}