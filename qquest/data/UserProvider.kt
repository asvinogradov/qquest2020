package com.htccs.android.neneyolka.data

import com.htccs.android.neneyolka.data.model.user.ReceivedItem
import com.htccs.android.neneyolka.data.model.user.User
import com.htccs.android.neneyolka.main.leaderboard.user.LeaderBoardUser
import io.reactivex.Observable

interface UserProvider {

    fun getUser(email: String): Observable<User>

    fun getAllUsersForLeaderBoard(): Observable<List<LeaderBoardUser>>

    fun getSortedReceivedItems(email: String): Observable<List<ReceivedItem>>
}