package com.htccs.android.neneyolka.data.model.answer

import android.os.Parcelable
import com.htccs.android.neneyolka.data.model.user.inventory.InventoryItem
import com.htccs.android.neneyolka.data.model.user.inventory.InventoryItemType
import kotlinx.android.parcel.Parcelize

sealed class DialogAnswer {

    @Parcelize
    data class Error(val errorText: String) : Parcelable, DialogAnswer()

    @Parcelize
    data class Image(val urlImage: String) : Parcelable, DialogAnswer()

    @Parcelize
    data class Present(val gift: InventoryItem) : Parcelable, DialogAnswer()

    @Parcelize
    data class ResultToss(val type: InventoryItemType?, val login: String) : Parcelable,
        DialogAnswer()

    @Parcelize
    data class Item(
        val title: String,
        val subtitle: String,
        val imageReference: String? = null,
        val number: Int? = null,
        val buttonText: String
    ) : Parcelable, DialogAnswer()
}