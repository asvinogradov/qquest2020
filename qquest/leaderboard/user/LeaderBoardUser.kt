package com.htccs.android.neneyolka.main.leaderboard.user

data class LeaderBoardUser(
    val login: String? = null,
    val email: String? = null,
    var score: Int? = null,
    val rating: Int? = null,
    var isCurrent: Boolean = false
)