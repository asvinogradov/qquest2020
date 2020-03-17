package com.htccs.android.neneyolka.data

import com.htccs.android.neneyolka.data.model.level.Level
import io.reactivex.Single

interface LevelProvider {

    fun getLevel(levelId: String): Single<Level>

    fun getLevels(): Single<List<Level>>
}