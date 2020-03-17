package com.htccs.android.neneyolka.base.authorized

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.htccs.android.neneyolka.base.BaseViewModel
import org.koin.core.inject

open class BaseAuthorizedActivityViewModel : BaseViewModel() {

    private val isAuthorizationNeededMutableLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val isAuthorizationNeededLiveData: LiveData<Boolean> = isAuthorizationNeededMutableLiveData
    private val firebaseAuth: FirebaseAuth by inject()

    fun onCheckAuthorization() {
        isAuthorizationNeededMutableLiveData.postValue(firebaseAuth.currentUser == null)
    }

    fun signOut() {
        firebaseAuth.signOut()
    }
}