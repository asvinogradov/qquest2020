package com.htccs.android.neneyolka.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.functions.FirebaseFunctionsException
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable

private val RANGE_OF_EXPECTED_ERRORS = 4..6

abstract class BaseNetworkViewModel<T> : BaseViewModel() {

    private val compositeDisposable = CompositeDisposable()
    protected val stateMutableLiveData: MutableLiveData<State<T>> =
        MutableLiveData()
    val stateLiveData: LiveData<State<T>> =
        stateMutableLiveData

    fun clearState() {
        stateMutableLiveData.postValue(State.empty())
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    protected fun <T> Observable<T>.addSubscription(
        argOnNext: ((T) -> Unit) = {},
        argOnComplete: (() -> Unit) = {},
        argOnError: ((Throwable) -> Unit) = {}
    ) {
        stateMutableLiveData.postValue(State.loading())
        subscribe({ argOnNext(it) }, { argOnError(it) }, { argOnComplete() })
            .let { compositeDisposable.add(it) }
    }

    protected fun <T> Single<T>.addSubscription(
        argOnSuccess: ((T) -> Unit) = {},
        argOnError: ((Throwable) -> Unit) = {}
    ) {
        stateMutableLiveData.postValue(State.loading())
        subscribe({ argOnSuccess(it) }, { argOnError(it) })
            .let { compositeDisposable.add(it) }
    }

    protected fun Completable.addSubscription(
        argOnSuccess: (() -> Unit) = {},
        argOnError: ((Throwable) -> Unit) = {}
    ) {
        stateMutableLiveData.postValue(State.loading())
        subscribe({ argOnSuccess() }, { argOnError(it) })
            .let { compositeDisposable.add(it) }
    }

    private fun isExpectedError(exception: Throwable): Boolean {
        return (exception is FirebaseFunctionsException &&
                exception.code.ordinal in RANGE_OF_EXPECTED_ERRORS &&
                exception.message != null)
    }

    protected fun processError(error: Throwable, errorStringId: Int) {
        stateMutableLiveData.postValue(
            if (isExpectedError(error)) {
                State.error(error.message!!)
            } else {
                State.error(errorStringId)
            }
        )
        error.printStackTrace()
    }
}