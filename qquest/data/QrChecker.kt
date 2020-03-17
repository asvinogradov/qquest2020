package com.htccs.android.neneyolka.data

import io.reactivex.Single

interface QrChecker {

    fun checkTrainingLevelQr(qr: String, levelId: String): Single<String>

    fun checkInventoryQr(qr: String): Single<String>

    fun checkLevelQr(qr: String, levelId: String): Single<String>
}