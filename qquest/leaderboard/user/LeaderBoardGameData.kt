package com.htccs.android.neneyolka.main.leaderboard.user

import com.htccs.android.neneyolka.data.model.user.inventory.Inventory

data class LeaderBoardGameData(
    var score: Int? = null,
    val inventory: Inventory? = null
)