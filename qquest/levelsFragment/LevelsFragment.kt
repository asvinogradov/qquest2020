package com.htccs.android.neneyolka.main.levelsFragment

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.htccs.android.neneyolka.R
import com.htccs.android.neneyolka.base.BaseNetworkFragment
import kotlinx.android.synthetic.main.list_layout.*
import org.koin.android.viewmodel.ext.android.viewModel

class LevelsFragment : BaseNetworkFragment() {

    companion object {

        val TAG: String = LevelsFragment::class.java.simpleName

        fun newInstance() = LevelsFragment()
    }

    override val layoutId = R.layout.list_layout
    override val networkViewModel: LevelsViewModel by viewModel()
    private val levelsAdapter = LevelsAdapter { networkViewModel.openLevelFragment(it) }

    override fun initView(view: View) {
        setupLevelsRecyclerView()
        setupStateObserver()
        loadState()
    }

    private fun setupLevelsRecyclerView() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = levelsAdapter
        }
    }

    private fun setupStateObserver() {
        networkViewModel.stateLiveData.observeState({ levelsState ->
            levelsAdapter.setData(levelsState.levels)
        })
    }

    override fun loadState() {
        networkViewModel.loadState()
    }
}