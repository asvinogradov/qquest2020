package com.htccs.android.neneyolka.base.scannerinteraction

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.htccs.android.neneyolka.R
import com.htccs.android.neneyolka.base.BaseNetworkFragment
import com.htccs.android.neneyolka.dialogs.DialogCaller
import com.htccs.android.neneyolka.scanner.ScannerActivity
import com.htccs.android.neneyolka.scanner.ScannerType
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

abstract class BaseScannerInteractionFragment : BaseNetworkFragment() {

    override val networkViewModel: ScannerInteractionViewModel by viewModel()

    private val dialogCaller: DialogCaller by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        networkViewModel.stateLiveData.observeState({ state ->
            activity?.supportFragmentManager?.let {
                dialogCaller.showDialog(state, it) { networkViewModel.clearState() }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == ScannerActivity.REQUEST_SCANNER) {
            when (resultCode) {
                ScannerActivity.ERROR_SCANNED -> networkViewModel.postError(R.string.error_scan)
                AppCompatActivity.RESULT_OK -> data?.extras?.let {
                    networkViewModel.processBarcode(it)
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    protected fun openScannerActivity(type: ScannerType, levelId: String? = null) {
        activity?.let { ScannerActivity.startScannerActivity(it, this, type, levelId) }
    }
}