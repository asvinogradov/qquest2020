package com.htccs.android.neneyolka.data.firestore

import com.htccs.android.neneyolka.data.LevelProvider
import com.htccs.android.neneyolka.data.firestore.rx.FirestoreProviderRxSingleWrapper
import com.htccs.android.neneyolka.data.model.level.Level
import io.reactivex.Single

class FirestoreLevelProvider(private val firestoreProviderRxSingleWrapper: FirestoreProviderRxSingleWrapper) :
    LevelProvider {

    override fun getLevel(levelId: String): Single<Level> {
        return firestoreProviderRxSingleWrapper.getDocument(
            FirestoreConstants.LEVELS_COLLECTION,
            levelId,
            Level::class.java
        )
    }

    override fun getLevels(): Single<List<Level>> {
        return firestoreProviderRxSingleWrapper.getCollection(
            FirestoreConstants.LEVELS_COLLECTION,
            Level::class.java
        )
    }
}