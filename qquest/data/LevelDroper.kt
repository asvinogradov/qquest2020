package com.htccs.android.neneyolka.data

import io.reactivex.Completable

interface LevelDroper {

    fun drop(levelId: String): Completable
}