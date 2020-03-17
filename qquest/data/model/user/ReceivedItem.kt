package com.htccs.android.neneyolka.data.model.user

import com.htccs.android.neneyolka.data.model.user.inventory.InventoryItemType

data class ReceivedItem(
    val type: InventoryItemType? = null,
    val score: Int? = null,
    val from: String? = null,
    val imageReference: String? = null,
    val created: Long? = null
)