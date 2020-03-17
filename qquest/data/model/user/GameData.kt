package com.htccs.android.neneyolka.data.model.user

import com.htccs.android.neneyolka.data.model.user.inventory.Inventory

data class GameData(
    var score: Int? = null,
    val inventory: Inventory? = null,
    val scannedQrs: Map<String, Int>? = null,
    val levelsInfo: Map<String, UserLevelInfo>? = null
)