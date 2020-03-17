package com.htccs.android.neneyolka.data

import com.htccs.android.neneyolka.data.model.item.FullItemId
import com.htccs.android.neneyolka.data.model.item.Item
import com.htccs.android.neneyolka.data.model.specialitem.SpecialItem
import io.reactivex.Single

interface ItemProvider {

    fun getItem(fullItemId: FullItemId): Single<Item>

    fun getItemsOnLevel(levelId: String): Single<List<Item>>

    fun getSpecialItem(answer: String): Single<SpecialItem>
}