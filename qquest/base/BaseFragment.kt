package com.htccs.android.neneyolka.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.htccs.android.neneyolka.R

abstract class BaseFragment : Fragment() {

    private val baseLayoutId = R.layout.base_fragment_layout
    abstract val layoutId: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        layoutInflater.inflate(baseLayoutId, container, false).also {
            layoutInflater.inflate(layoutId, it.findViewById(R.id.fragment_container), true)
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }

    protected abstract fun initView(view: View)
}