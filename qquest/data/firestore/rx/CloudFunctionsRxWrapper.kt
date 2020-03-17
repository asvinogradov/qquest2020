package com.htccs.android.neneyolka.data.firestore.rx

import com.google.android.gms.tasks.Task
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.HttpsCallableResult
import io.reactivex.Completable
import io.reactivex.CompletableEmitter
import io.reactivex.Single
import io.reactivex.SingleEmitter
import org.koin.core.KoinComponent
import org.koin.core.inject

class CloudFunctionsRxWrapper : KoinComponent {

    private val firebaseFunctions: FirebaseFunctions by inject()

    fun call(functionName: String, data: Any): Completable {
        return Completable.create { emitter ->
            callFirebase(functionName, data)
                .addListeners(emitter)
        }
    }

    fun callForResult(functionName: String, data: Any): Single<String> {
        return Single.create { emitter ->
            callFirebase(functionName, data)
                .addListeners(emitter)
        }
    }

    private fun callFirebase(
        functionName: String,
        data: Any
    ): Task<HttpsCallableResult> {
        return firebaseFunctions
            .getHttpsCallable(functionName)
            .call(data)
    }

    private fun Task<HttpsCallableResult>.addListeners(emitter: CompletableEmitter) {
        addOnSuccessListener { emitter.onComplete() }
        addOnFailureListener { exception -> emitter.onError(exception) }
    }

    private fun Task<HttpsCallableResult>.addListeners(emitter: SingleEmitter<String>) {
        addOnSuccessListener { emitter.onSuccess(it.data.toString()) }
        addOnFailureListener { exception -> emitter.onError(exception) }
    }
}