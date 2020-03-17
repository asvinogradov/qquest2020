package com.htccs.android.neneyolka.base.scannerinteraction

import android.os.Bundle
import androidx.annotation.StringRes
import com.google.gson.Gson
import com.htccs.android.neneyolka.R
import com.htccs.android.neneyolka.base.BaseNetworkViewModel
import com.htccs.android.neneyolka.base.State
import com.htccs.android.neneyolka.data.QrChecker
import com.htccs.android.neneyolka.data.model.answer.DialogAnswer
import com.htccs.android.neneyolka.scanner.ScannerActivity.Companion.LEVEL_ID_KEY
import com.htccs.android.neneyolka.scanner.ScannerActivity.Companion.RESULT_SCANNED
import com.htccs.android.neneyolka.scanner.ScannerActivity.Companion.SCAN_TYPE_KEY
import com.htccs.android.neneyolka.scanner.ScannerType
import org.koin.core.inject

class ScannerInteractionViewModel : BaseNetworkViewModel<DialogAnswer.Item>() {

    private val qrSender: QrChecker by inject()
    private val gson: Gson by inject()

    fun processBarcode(bundle: Bundle) {
        val qr = bundle.getString(RESULT_SCANNED)
        val levelId = bundle.getString(LEVEL_ID_KEY)
        val scanType = bundle.get(SCAN_TYPE_KEY) as? ScannerType

        if (qr == null || scanType == null) {
            postError(R.string.scanned_type_error)
            return
        } else {
            checkResult(qr, levelId, scanType)
        }
    }

    private fun checkResult(qr: String, levelId: String?, scanType: ScannerType) {
        when (scanType) {
            ScannerType.SCANNED -> qrSender.checkTrainingLevelQr(qr, levelId.toString())
            ScannerType.GAME -> qrSender.checkLevelQr(qr, levelId.toString())
            ScannerType.INVENTORY -> qrSender.checkInventoryQr(qr)
        }.addSubscription(
            { result ->
                gson.fromJson(result, DialogAnswer.Item::class.java).also {
                    stateMutableLiveData.postValue(State.setData(it))
                }
            },
            { processError(it, R.string.get_qr_error) }
        )
    }

    fun postError(@StringRes stringId: Int) {
        stateMutableLiveData.postValue(State.error(stringId))
    }
}