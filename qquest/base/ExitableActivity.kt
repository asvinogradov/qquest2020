package com.htccs.android.neneyolka.base

import androidx.fragment.app.FragmentManager
import com.htccs.android.neneyolka.R
import org.koin.android.viewmodel.ext.android.viewModel

abstract class ExitableActivity : BaseActivity(), FragmentManager.OnBackStackChangedListener {

    private val exitViewModel: ExitViewModel by viewModel()

    override fun onBackPressed() {
        if (shouldCheckExit) {
            if (exitViewModel.checkBackDoubleTap()) {
                finish()
            } else {
                toastCaller.showShortMessage(this, R.string.exit_toast_text)
            }
        } else {
            doOnBackPressed()
        }
    }

    abstract fun doOnBackPressed()

    abstract val shouldCheckExit: Boolean

    override fun onBackStackChanged() {
        supportActionBar?.setDisplayHomeAsUpEnabled(!shouldCheckExit)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}