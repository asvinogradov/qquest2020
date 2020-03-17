package com.htccs.android.neneyolka.data.firestore.rx

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import io.reactivex.Completable
import io.reactivex.CompletableEmitter
import io.reactivex.Single
import io.reactivex.SingleEmitter

class FirestoreSaverRxWrapper(private val firestoreReferenceProvider: FirestoreReferenceProvider) {

    fun <T : Any> setDocument(
        collectionName: String,
        documentName: String,
        data: T
    ): Single<T> {
        return Single.create<T> { emitter ->
            firestoreReferenceProvider
                .getTopLevelDocumentReference(collectionName, documentName)
                .addListenersOnSet(data, emitter)
        }
    }

    private fun <T : Any> DocumentReference.addListenersOnSet(data: T, emitter: SingleEmitter<T>) {
        set(data)
            .addOnFailureListener { emitter.onError(it) }
            .addOnSuccessListener { emitter.onSuccess(data) }
    }

    fun <T : Any> addElementToArrayField(
        collectionName: String,
        documentName: String,
        fieldName: String,
        data: T
    ): Completable {
        return Completable.create { emitter ->
            firestoreReferenceProvider
                .getTopLevelDocumentReference(collectionName, documentName)
                .update(fieldName, FieldValue.arrayUnion(data))
                .addListeners(emitter)
        }
    }

    fun <T : Any> removeElementFromArrayField(
        collectionName: String,
        documentName: String,
        fieldName: String,
        data: T
    ): Completable {
        return Completable.create { emitter ->
            firestoreReferenceProvider
                .getTopLevelDocumentReference(collectionName, documentName)
                .update(fieldName, FieldValue.arrayRemove(data))
                .addListeners(emitter)
        }
    }

    fun <T : Any> updateField(
        collectionName: String,
        documentName: String,
        fieldName: String,
        data: T
    ): Completable {
        return Completable.create { emitter ->
            firestoreReferenceProvider
                .getTopLevelDocumentReference(collectionName, documentName)
                .update(fieldName, data)
                .addListeners(emitter)
        }
    }

    private fun <T : Any> Task<T>.addListeners(emitter: CompletableEmitter) {
        addOnSuccessListener { emitter.onComplete() }
        addOnFailureListener { exception -> emitter.onError(exception) }
    }
}