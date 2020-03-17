package com.htccs.android.neneyolka.main.router

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.htccs.android.neneyolka.R

class BackStackFragment : Fragment() {

    companion object {

        const val BACK_STACK_CONTAINER = R.id.back_stack_fragment_container

        fun newInstance(): BackStackFragment {
            return BackStackFragment()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context as? FragmentManager.OnBackStackChangedListener)
            ?.let { childFragmentManager.addOnBackStackChangedListener(it) }
            ?: throw ClassCastException("$context must implement FragmentManager.OnBackStackChangedListener")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.back_stack_fragment_layout, container, false)
    }
}