package com.htccs.android.neneyolka.data.firestore

import com.google.gson.Gson
import com.htccs.android.neneyolka.data.*
import com.htccs.android.neneyolka.data.firestore.FirestoreConstants.DROP_LEVEL_PROGRESS
import com.htccs.android.neneyolka.data.firestore.FirestoreConstants.LEVEL_ID
import com.htccs.android.neneyolka.data.firestore.FirestoreConstants.LOGIN
import com.htccs.android.neneyolka.data.firestore.FirestoreConstants.OPEN_GIFT
import com.htccs.android.neneyolka.data.firestore.FirestoreConstants.PROCESS_INVENTORY_QR
import com.htccs.android.neneyolka.data.firestore.FirestoreConstants.PROCESS_LEVEL_QR
import com.htccs.android.neneyolka.data.firestore.FirestoreConstants.PROCESS_TRAINING_LEVEL_QR
import com.htccs.android.neneyolka.data.firestore.FirestoreConstants.QR
import com.htccs.android.neneyolka.data.firestore.FirestoreConstants.TARGET_EMAIL
import com.htccs.android.neneyolka.data.firestore.FirestoreConstants.THROWN_ITEM
import com.htccs.android.neneyolka.data.firestore.FirestoreConstants.THROW_ITEM
import com.htccs.android.neneyolka.data.firestore.FirestoreConstants.UPDATE_LOGIN
import com.htccs.android.neneyolka.data.firestore.rx.CloudFunctionsRxWrapper
import com.htccs.android.neneyolka.data.model.user.inventory.InventoryItem
import io.reactivex.Completable
import io.reactivex.Single

class CloudFunctionsItemSender(
    private val cloudFunctionsRxWrapper: CloudFunctionsRxWrapper,
    private val gson: Gson
) : ItemSender {

    override fun sendItem(
        targetEmail: String,
        sendedItem: InventoryItem
    ): Completable {
        return cloudFunctionsRxWrapper.call(
            THROW_ITEM,
            hashMapOf(
                TARGET_EMAIL to targetEmail,
                THROWN_ITEM to gson.toJson(sendedItem)
            )
        )
    }
}

class CloudFunctionsUserUpdater(
    private val cloudFunctionsRxWrapper: CloudFunctionsRxWrapper
) : UserUpdater {

    override fun updateUser(
        login: String
    ): Completable {
        return cloudFunctionsRxWrapper.call(
            UPDATE_LOGIN,
            hashMapOf(LOGIN to login)
        )
    }
}

class CloudFunctionsQrChecker(
    private val cloudFunctionsRxWrapper: CloudFunctionsRxWrapper
) : QrChecker {

    override fun checkTrainingLevelQr(qr: String, levelId: String): Single<String> {
        return cloudFunctionsRxWrapper.callForResult(
            PROCESS_TRAINING_LEVEL_QR,
            hashMapOf(
                QR to qr,
                LEVEL_ID to levelId
            )
        )
    }

    override fun checkInventoryQr(qr: String): Single<String> {
        return cloudFunctionsRxWrapper.callForResult(
            PROCESS_INVENTORY_QR,
            hashMapOf(QR to qr)
        )
    }

    override fun checkLevelQr(
        qr: String,
        levelId: String
    ): Single<String> {
        return cloudFunctionsRxWrapper.callForResult(
            PROCESS_LEVEL_QR,
            hashMapOf(
                QR to qr,
                LEVEL_ID to levelId
            )
        )
    }
}

class CloudFunctionsLevelDroper(
    private val cloudFunctionsRxWrapper: CloudFunctionsRxWrapper
) : LevelDroper {

    override fun drop(levelId: String): Completable {
        return cloudFunctionsRxWrapper.call(
            DROP_LEVEL_PROGRESS,
            hashMapOf(LEVEL_ID to levelId)
        )
    }
}

class CloudFunctionsGiftOpener(
    private val cloudFunctionsRxWrapper: CloudFunctionsRxWrapper,
    private val gson: Gson
) : GiftOpener {

    override fun openGift(openedItem: InventoryItem): Single<String> {
        return cloudFunctionsRxWrapper.callForResult(
            OPEN_GIFT,
            hashMapOf(THROWN_ITEM to gson.toJson(openedItem))
        )
    }
}