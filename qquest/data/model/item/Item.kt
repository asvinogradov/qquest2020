package com.htccs.android.neneyolka.data.model.item

data class Item(
    val id: FullItemId? = null,
    val hint: String? = null,
    val imageReference: String? = null,
    val questionImageReference: String? = null,
    val rotationAngle: Int? = null,
    val size: Double? = null,
    val type: ItemType? = null,
    val answer: String? = null,
    val bounty: Int? = null
)