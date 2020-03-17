package com.htccs.android.neneyolka.data.firestore

import com.htccs.android.neneyolka.data.ItemProvider
import com.htccs.android.neneyolka.data.firestore.rx.FirestoreProviderRxSingleWrapper
import com.htccs.android.neneyolka.data.model.item.FullItemId
import com.htccs.android.neneyolka.data.model.item.Item
import com.htccs.android.neneyolka.data.model.specialitem.SpecialItem
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class FirestoreItemProvider(private val firestoreProviderRxSingleWrapper: FirestoreProviderRxSingleWrapper) :
    ItemProvider {

    override fun getItem(fullItemId: FullItemId): Single<Item> {
        return firestoreProviderRxSingleWrapper
            .getInnerDocument(
                FirestoreConstants.LEVELS_COLLECTION,
                fullItemId.levelId.toString(),
                FirestoreConstants.ITEMS_COLLECTION,
                fullItemId.itemId.toString(),
                Item::class.java
            )
    }

    override fun getItemsOnLevel(levelId: String): Single<List<Item>> {
        return firestoreProviderRxSingleWrapper
            .getInnerCollection(
                FirestoreConstants.LEVELS_COLLECTION,
                levelId,
                FirestoreConstants.ITEMS_COLLECTION,
                Item::class.java
            )
            .subscribeOn(Schedulers.computation())
            .map { items ->
                items.sortedBy { it.id?.itemId }
            }
    }

    override fun getSpecialItem(answer: String): Single<SpecialItem> {
        return firestoreProviderRxSingleWrapper
            .getDocument(
                FirestoreConstants.SPECIAL_ITEMS_COLLECTION,
                answer,
                SpecialItem::class.java
            )
    }
}