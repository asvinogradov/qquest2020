package com.htccs.android.neneyolka.main.levelsFragment

import com.htccs.android.neneyolka.data.model.level.Level

data class LevelsItem(
    val content: Level,
    val foundItemCount: Int? = null,
    val isSequenceComplete: Boolean = false
)