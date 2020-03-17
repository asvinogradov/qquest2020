package com.htccs.android.neneyolka.data.firestore.rx

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.core.KoinComponent
import org.koin.core.inject

class FirestoreReferenceProvider : KoinComponent {

    private val firestore: FirebaseFirestore by inject()

    fun getTopLevelCollectionReference(collectionName: String): CollectionReference {
        return firestore.collection(collectionName)
    }

    fun getTopLevelDocumentReference(
        collectionName: String,
        documentName: String
    ): DocumentReference {
        return getTopLevelCollectionReference(collectionName).document(documentName)
    }

    fun getInnerCollectionReference(
        topLevelCollectionName: String,
        topLevelDocumentName: String,
        collectionName: String
    ): CollectionReference {
        return getTopLevelDocumentReference(
            topLevelCollectionName,
            topLevelDocumentName
        ).collection(collectionName)
    }

    fun getInnerDocumentReference(
        topLevelCollectionName: String,
        topLevelDocumentName: String,
        collectionName: String,
        documentName: String
    ): DocumentReference {
        return getInnerCollectionReference(
            topLevelCollectionName,
            topLevelDocumentName,
            collectionName
        ).document(documentName)
    }
}