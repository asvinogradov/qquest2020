package com.htccs.android.neneyolka.base

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.htccs.android.neneyolka.data.model.answer.DialogAnswer
import com.htccs.android.neneyolka.dialogs.DialogCaller
import com.htccs.android.neneyolka.uiextensions.remove
import com.htccs.android.neneyolka.uiextensions.show
import kotlinx.android.synthetic.main.part_loader.*
import kotlinx.android.synthetic.main.part_try_again.*
import org.koin.android.ext.android.inject

abstract class BaseNetworkFragment : BaseFragment() {

    private val dialogCaller: DialogCaller by inject()
    abstract val networkViewModel: BaseNetworkViewModel<out Any>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTryAgainDialog()
    }

    protected fun <T> LiveData<State<T>>.observeState(
        processData: (T) -> Unit,
        processErrorId: (Int) -> Unit = { exceptionStringId -> showTryAgainButton(exceptionStringId) },
        processErrorString: (String) -> Unit = { exceptionString -> showErrorDialog(exceptionString) }
    ) {
        observe(viewLifecycleOwner, Observer { state ->
            observeState(state, processErrorId, processErrorString, processData)
        })
    }

    private fun <T> observeState(
        state: State<T>,
        processErrorId: (Int) -> Unit,
        processErrorString: (String) -> Unit,
        processData: (T) -> Unit
    ) {
        if (state.isLoading) {
            showLoadingBar()
        } else {
            hideLoadingBar()
            state.exceptionStringId?.let(processErrorId)
            state.exceptionString?.let(processErrorString)
            state.data?.let(processData)
        }
    }

    protected abstract fun loadState()

    private fun showErrorDialog(errorString: String) {
        activity?.supportFragmentManager?.let {
            dialogCaller.showDialog(DialogAnswer.Error(errorString), it) {
                networkViewModel.clearState()
            }
        }
    }

    private fun showTryAgainButton(@StringRes errorStringId: Int) {
        dialogTryAgainMessage.text = getString(errorStringId)
        dialogTryAgainMessage.show()
        dialogTryAgain.show()
    }

    private fun hideTryAgainDialog() {
        dialogTryAgain.remove()
    }

    private fun setupTryAgainDialog() {
        dialogTryAgainButton?.setOnClickListener {
            hideTryAgainDialog()
            loadState()
        }
    }

    private fun showLoadingBar() {
        loadingProgressBar?.show()
    }

    private fun hideLoadingBar() {
        loadingProgressBar?.remove()
    }
}