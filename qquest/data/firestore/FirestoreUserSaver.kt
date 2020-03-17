package com.htccs.android.neneyolka.data.firestore

import com.htccs.android.neneyolka.data.UserSaver
import com.htccs.android.neneyolka.data.firestore.rx.FirestoreSaverRxWrapper
import com.htccs.android.neneyolka.data.model.user.User
import com.htccs.android.neneyolka.data.model.user.UserLevelInfo
import com.htccs.android.neneyolka.data.model.user.inventory.InventoryItem
import io.reactivex.Completable
import io.reactivex.Single

class FirestoreUserSaver(
    private val firestoreSaverRxWrapper: FirestoreSaverRxWrapper
) : UserSaver {

    override fun updateUser(user: User, email: String): Single<User> {
        return firestoreSaverRxWrapper.setDocument(
            FirestoreConstants.USERS_COLLECTION,
            email,
            user
        )
    }

    override fun addInventoryItem(
        inventoryItem: InventoryItem,
        email: String
    ): Completable {
        return firestoreSaverRxWrapper.addElementToArrayField(
            FirestoreConstants.USERS_COLLECTION,
            email,
            FirestoreConstants.INVENTORY_ITEMS_FIELD,
            inventoryItem
        )
    }

    override fun updateScore(
        score: Int,
        email: String
    ): Completable {
        return firestoreSaverRxWrapper.updateField(
            FirestoreConstants.USERS_COLLECTION,
            email,
            FirestoreConstants.SCORE_FIELD,
            score
        )
    }

    override fun updatePositionInSequence(
        positionInSequence: Int,
        levelId: Int,
        email: String
    ): Completable {
        return firestoreSaverRxWrapper.updateField(
            FirestoreConstants.USERS_COLLECTION,
            email,
            FirestoreConstants.LEVELS_INFO_FIELD
                    + levelId
                    + FirestoreConstants.POSITION_IN_SEQUENCE_FIELD,
            positionInSequence
        )
    }

    override fun updateGameStarted(
        gameStarted: Boolean,
        levelId: Int,
        email: String
    ): Completable {
        return firestoreSaverRxWrapper.updateField(
            FirestoreConstants.USERS_COLLECTION,
            email,
            FirestoreConstants.LEVELS_INFO_FIELD
                    + levelId
                    + FirestoreConstants.GAME_STARTED_FIELD,
            gameStarted
        )
    }

    override fun addScannedQr(
        qr: String,
        email: String
    ): Completable {
        return firestoreSaverRxWrapper.addElementToArrayField(
            FirestoreConstants.USERS_COLLECTION,
            email,
            FirestoreConstants.SCANNED_QRS_FIELD,
            qr
        )
    }

    override fun addLevelInfo(
        levelInfo: UserLevelInfo,
        levelId: Int,
        email: String
    ): Completable {
        return firestoreSaverRxWrapper.updateField(
            FirestoreConstants.USERS_COLLECTION,
            email,
            FirestoreConstants.LEVELS_INFO_FIELD + levelId,
            levelInfo
        )
    }

    override fun removeInventoryItem(
        inventoryItem: InventoryItem,
        email: String
    ): Completable {
        return firestoreSaverRxWrapper.removeElementFromArrayField(
            FirestoreConstants.USERS_COLLECTION,
            email,
            FirestoreConstants.INVENTORY_ITEMS_FIELD,
            inventoryItem
        )
    }
}