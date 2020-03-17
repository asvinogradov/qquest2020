package com.htccs.android.neneyolka.data.model.user.inventory

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class InventoryItem(
    val id: String? = null,
    val type: InventoryItemType? = null,
    val imageReference: String? = null,
    val text: String? = null
) : Parcelable