package com.htccs.android.neneyolka.data

import com.htccs.android.neneyolka.data.model.user.inventory.InventoryItem
import io.reactivex.Completable

interface ItemSender {

    fun sendItem(targetEmail: String, sendedItem: InventoryItem): Completable
}