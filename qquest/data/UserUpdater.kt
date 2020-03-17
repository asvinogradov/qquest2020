package com.htccs.android.neneyolka.data

import io.reactivex.Completable

interface UserUpdater {

    fun updateUser(login: String): Completable
}