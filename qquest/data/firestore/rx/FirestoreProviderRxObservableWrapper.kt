package com.htccs.android.neneyolka.data.firestore.rx

import com.google.firebase.firestore.*
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class FirestoreProviderRxObservableWrapper(private val firestoreReferenceProvider: FirestoreReferenceProvider) {

    fun <T> getDocument(
        collectionName: String,
        documentName: String,
        documentClass: Class<T>
    ): Observable<T> {
        return BehaviorSubject.create<T>().also {
            firestoreReferenceProvider
                .getTopLevelDocumentReference(collectionName, documentName)
                .addListeners(documentClass, it)
                .also { listener -> it.doFinally(listener::remove) }
        }
    }

    fun <T> getCollection(
        collectionName: String,
        documentClass: Class<T>
    ): Observable<List<T>> {
        return BehaviorSubject.create<List<T>>().also {
            firestoreReferenceProvider
                .getTopLevelCollectionReference(collectionName)
                .addListeners(documentClass, it)
                .also { listener -> it.doFinally(listener::remove) }
        }
    }

    fun <T> getInnerCollection(
        topLevelCollectionName: String,
        topLevelDocumentName: String,
        collectionName: String,
        documentClass: Class<T>
    ): Observable<List<T>> {
        return BehaviorSubject.create<List<T>>().also {
            firestoreReferenceProvider
                .getInnerCollectionReference(
                    topLevelCollectionName,
                    topLevelDocumentName,
                    collectionName
                ).addListeners(documentClass, it)
                .also { listener -> it.doFinally(listener::remove) }
        }
    }

    private fun <T> DocumentReference.addListeners(
        documentClass: Class<T>,
        emitter: BehaviorSubject<T>
    ): ListenerRegistration {
        return addSnapshotListener { snapshot, exception ->
            exception
                ?.let { emitter.onError(it) }
                ?: snapshot
                    ?.takeIf { it.exists() }
                    ?.let {
                        deserializeDocumentSnapshot(documentClass, emitter, snapshot)
                    }
                ?: emitter.onError(NoSuchElementException("Current data: null instead of ${documentClass.simpleName}"))
        }
    }

    private fun <T> deserializeDocumentSnapshot(
        documentClass: Class<T>,
        emitter: BehaviorSubject<T>,
        documentSnapshot: DocumentSnapshot
    ) {
        try {
            documentSnapshot.toObject(documentClass)
                ?.let { emitter.onNext(it) }
                ?: emitter.onError(NoSuchElementException())
        } catch (e: RuntimeException) {
            emitter.onError(e)
        }
    }

    private fun <T> CollectionReference.addListeners(
        documentClass: Class<T>,
        emitter: BehaviorSubject<List<T>>
    ): ListenerRegistration {
        return addSnapshotListener { querySnapshot, exception ->
            exception
                ?.let { emitter.onError(it) }
                ?: querySnapshot
                    ?.let { deserializeQuerySnapshot(documentClass, emitter, querySnapshot) }
                ?: emitter.onError(NoSuchElementException("Current data: null instead of List<${documentClass.simpleName}>"))
        }
    }

    private fun <T> deserializeQuerySnapshot(
        documentClass: Class<T>,
        emitter: BehaviorSubject<List<T>>,
        querySnapshot: QuerySnapshot
    ) {
        try {
            val objects = querySnapshot.toObjects(documentClass)
            emitter.onNext(objects)
        } catch (e: Exception) {
            emitter.onError(e)
        }
    }

}