package com.htccs.android.neneyolka.data.receiveditem

import com.htccs.android.neneyolka.data.model.user.inventory.InventoryItemType

data class ReceivedItems(
    val from: String? = null,
    val imageReference: String? = null,
    val type: InventoryItemType,
    val score: Int? = null
)
