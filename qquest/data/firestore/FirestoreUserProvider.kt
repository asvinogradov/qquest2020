package com.htccs.android.neneyolka.data.firestore

import com.htccs.android.neneyolka.data.UserProvider
import com.htccs.android.neneyolka.data.firestore.rx.FirestoreProviderRxObservableWrapper
import com.htccs.android.neneyolka.data.model.user.ReceivedItem
import com.htccs.android.neneyolka.data.model.user.User
import com.htccs.android.neneyolka.main.leaderboard.user.LeaderBoardUser
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class FirestoreUserProvider(
    private val firestoreProviderRxObservableWrapper: FirestoreProviderRxObservableWrapper
) : UserProvider {

    override fun getUser(email: String): Observable<User> {
        return firestoreProviderRxObservableWrapper.getDocument(
            FirestoreConstants.USERS_COLLECTION,
            email,
            User::class.java
        )
    }

    override fun getAllUsersForLeaderBoard(): Observable<List<LeaderBoardUser>> {
        return getAllUsers()
            .subscribeOn(Schedulers.computation())
            .map { users ->
                    users.filter { it.gameData?.score != null }
                    .sortedByDescending { it.gameData!!.score }
                    .mapIndexed { index, user ->
                        LeaderBoardUser(
                            user.login,
                            user.email,
                            user.gameData!!.score,
                            index + 1
                        )
                    }
            }
    }

    private fun getAllUsers(): Observable<List<User>> {
        return firestoreProviderRxObservableWrapper
            .getCollection(
                FirestoreConstants.USERS_COLLECTION,
                User::class.java
            )
    }

    override fun getSortedReceivedItems(email: String): Observable<List<ReceivedItem>> {
        return getReceivedItems(email)
            .subscribeOn(Schedulers.computation())
            .map { receivedItems ->
                receivedItems.sortedByDescending { it.created }
            }
    }

    private fun getReceivedItems(email: String): Observable<List<ReceivedItem>> {
        return firestoreProviderRxObservableWrapper
            .getInnerCollection(
                FirestoreConstants.USERS_COLLECTION,
                email,
                FirestoreConstants.RECIEVED_ITEMS_COLLECTION,
                ReceivedItem::class.java
            )
    }
}