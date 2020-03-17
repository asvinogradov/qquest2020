package com.htccs.android.neneyolka.data.firestore.rx

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import io.reactivex.Single
import io.reactivex.SingleEmitter

class FirestoreProviderRxSingleWrapper(private val firestoreReferenceProvider: FirestoreReferenceProvider) {

    fun <T> getCollection(
        collectionName: String,
        documentClass: Class<T>
    ): Single<List<T>> {
        return Single.create<List<T>> { emitter ->
            firestoreReferenceProvider
                .getTopLevelCollectionReference(collectionName)
                .addListeners(documentClass, emitter)
        }
    }

    fun <T> getDocument(
        collectionName: String,
        documentName: String,
        documentClass: Class<T>
    ): Single<T> {
        return Single.create<T> { emitter ->
            firestoreReferenceProvider
                .getTopLevelDocumentReference(collectionName, documentName)
                .addListeners(documentClass, emitter)
        }
    }

    fun <T> getInnerCollection(
        topLevelCollectionName: String,
        topLevelDocumentName: String,
        collectionName: String,
        documentClass: Class<T>
    ): Single<List<T>> {
        return Single.create<List<T>> { emitter ->
            firestoreReferenceProvider
                .getInnerCollectionReference(
                    topLevelCollectionName,
                    topLevelDocumentName,
                    collectionName
                ).addListeners(documentClass, emitter)
        }
    }

    fun <T> getInnerDocument(
        topLevelCollectionName: String,
        topLevelDocumentName: String,
        collectionName: String,
        documentName: String,
        documentClass: Class<T>
    ): Single<T> {
        return Single.create<T> { emitter ->
            firestoreReferenceProvider
                .getInnerDocumentReference(
                    topLevelCollectionName,
                    topLevelDocumentName,
                    collectionName,
                    documentName
                ).addListeners(documentClass, emitter)
        }
    }

    private fun <T> DocumentReference.addListeners(
        documentClass: Class<T>,
        emitter: SingleEmitter<T>
    ) {
        get()
            .addOnSuccessListener { documentSnapshot ->
                deserializeDocumentSnapshot(documentClass, emitter, documentSnapshot)
            }
            .addOnFailureListener { exception ->
                emitter.onError(exception)
            }
    }

    private fun <T> deserializeDocumentSnapshot(
        documentClass: Class<T>,
        emitter: SingleEmitter<T>,
        documentSnapshot: DocumentSnapshot
    ) {
        try {
            documentSnapshot.toObject(documentClass)
                ?.let { emitter.onSuccess(it) }
                ?: emitter.onError(NoSuchElementException())
        } catch (e: Exception) {
            emitter.onError(e)
        }
    }

    private fun <T> CollectionReference.addListeners(
        documentClass: Class<T>,
        emitter: SingleEmitter<List<T>>
    ) {
        get()
            .addOnSuccessListener { querySnapshot ->
                deserializeQuerySnapshot(documentClass, emitter, querySnapshot)
            }
            .addOnFailureListener { exception ->
                emitter.onError(exception)
            }
    }

    private fun <T> deserializeQuerySnapshot(
        documentClass: Class<T>,
        emitter: SingleEmitter<List<T>>,
        querySnapshot: QuerySnapshot
    ) {
        try {
            val objects = querySnapshot.toObjects(documentClass)
            emitter.onSuccess(objects)
        } catch (e: Exception) {
            emitter.onError(e)
        }
    }
}