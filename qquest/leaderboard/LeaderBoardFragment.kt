package com.htccs.android.neneyolka.main.leaderboard

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.htccs.android.neneyolka.R
import com.htccs.android.neneyolka.base.BaseNetworkFragment
import kotlinx.android.synthetic.main.list_layout.*
import org.koin.android.viewmodel.ext.android.viewModel

open class LeaderBoardFragment : BaseNetworkFragment() {

    companion object {
        val TAG: String = LeaderBoardFragment::class.java.simpleName

        fun newInstance() = LeaderBoardFragment()
    }

    override val layoutId = R.layout.list_layout
    override val networkViewModel: LeaderBoardViewModel by viewModel()
    protected open val leaderAdapter = LeaderAdapter()

    override fun initView(view: View) {
        setupRecyclerView()
        setupLoadingResultObserver()
        loadState()
    }

    private fun setupRecyclerView() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = leaderAdapter
        }
    }

    private fun setupLoadingResultObserver() {
        networkViewModel.stateLiveData.observeState({ leaderBoardData ->
            leaderAdapter.setData(leaderBoardData.users)
        })
    }

    override fun loadState() {
        networkViewModel.loadState()
    }
}