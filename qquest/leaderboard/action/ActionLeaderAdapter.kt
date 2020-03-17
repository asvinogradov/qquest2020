package com.htccs.android.neneyolka.main.leaderboard.action

import com.htccs.android.neneyolka.main.leaderboard.LeaderAdapter
import com.htccs.android.neneyolka.main.leaderboard.user.LeaderBoardUser

class ActionLeaderAdapter(
    listener: (LeaderBoardUser) -> Unit = {}
) : LeaderAdapter(listener) {

    override val offsetHeader = 0
}