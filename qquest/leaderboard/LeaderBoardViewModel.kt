package com.htccs.android.neneyolka.main.leaderboard

import com.htccs.android.neneyolka.R
import com.htccs.android.neneyolka.base.BaseNetworkViewModel
import com.htccs.android.neneyolka.base.State
import com.htccs.android.neneyolka.data.UserProvider
import com.htccs.android.neneyolka.module.EMAIL_KEY
import org.koin.core.inject
import org.koin.core.qualifier.named

class LeaderBoardViewModel : BaseNetworkViewModel<LeaderBoardData>() {

    private val userProvider: UserProvider by inject()
    private val currentEmail: String by inject(named(EMAIL_KEY))

    fun loadState() {
        userProvider.getAllUsersForLeaderBoard()
            .map { users ->
                users.first { it.email == currentEmail }.isCurrent = true
                LeaderBoardData(users)
            }
            .addSubscription(
                argOnNext = { leaderBoardState ->
                    stateMutableLiveData.postValue(State.setData(leaderBoardState))
                },
                argOnError = { exception ->
                    exception.printStackTrace()
                    stateMutableLiveData.postValue(State.error(R.string.leader_board_users_load_error))
                }
            )
    }

}