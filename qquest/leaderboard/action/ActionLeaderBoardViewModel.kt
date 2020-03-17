package com.htccs.android.neneyolka.main.leaderboard.action

import android.os.Bundle
import com.htccs.android.neneyolka.R
import com.htccs.android.neneyolka.base.BaseNetworkViewModel
import com.htccs.android.neneyolka.base.State
import com.htccs.android.neneyolka.data.ItemSender
import com.htccs.android.neneyolka.data.model.user.inventory.InventoryItem
import com.htccs.android.neneyolka.data.model.user.inventory.InventoryItemType
import com.htccs.android.neneyolka.main.leaderboard.user.LeaderBoardUser
import io.reactivex.Completable
import org.koin.core.inject

class ActionLeaderBoardViewModel : BaseNetworkViewModel<ActionData>() {

    private var sentItem: InventoryItem? = null
    private val itemSender: ItemSender by inject()

    fun itemClick(targetUser: LeaderBoardUser) {
        targetUser.email
            ?.let { sendItem(it, targetUser.login) }
            ?: stateMutableLiveData.postValue(State.error(R.string.throw_item_error))
    }

    private fun sendItem(targetEmail: String, targetLogin: String?) {
        sentItem
            ?.let {
                itemSender.sendItem(targetEmail, it).addSubscriptionOnSendItem(it.type, targetLogin)
            }
            ?: throw NoSuchElementException("InventoryItem not found in fragment arguments")
    }

    private fun Completable.addSubscriptionOnSendItem(
        type: InventoryItemType?,
        targetLogin: String?
    ) {
        addSubscription(
            { stateMutableLiveData.postValue(State.setData(ActionData(type, targetLogin))) },
            { processError(it, R.string.throw_item_error) }
        )
    }

    fun setSentItem(arguments: Bundle?) {
        arguments
            ?.getParcelable<InventoryItem>(ActionLeaderBoardFragment.ITEM_KEY)
            ?.let {
                sentItem = it
                stateMutableLiveData.postValue(State.setData(ActionData(it.type)))
            }
    }
}