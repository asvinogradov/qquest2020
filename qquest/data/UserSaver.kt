package com.htccs.android.neneyolka.data

import com.htccs.android.neneyolka.data.model.user.User
import com.htccs.android.neneyolka.data.model.user.UserLevelInfo
import com.htccs.android.neneyolka.data.model.user.inventory.InventoryItem
import io.reactivex.Completable
import io.reactivex.Single

interface UserSaver {

    fun updateUser(user: User, email: String): Single<User>

    fun addInventoryItem(inventoryItem: InventoryItem, email: String): Completable

    fun updateScore(score: Int, email: String): Completable

    fun updatePositionInSequence(positionInSequence: Int, levelId: Int, email: String): Completable

    fun updateGameStarted(gameStarted: Boolean, levelId: Int, email: String): Completable

    fun addScannedQr(qr: String, email: String): Completable

    fun addLevelInfo(levelInfo: UserLevelInfo, levelId: Int, email: String): Completable

    fun removeInventoryItem(inventoryItem: InventoryItem, email: String): Completable
}