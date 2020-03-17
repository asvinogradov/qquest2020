package com.htccs.android.neneyolka.data

import com.htccs.android.neneyolka.data.model.user.inventory.InventoryItem
import io.reactivex.Single

interface GiftOpener {

    fun openGift(openedItem: InventoryItem): Single<String>
}