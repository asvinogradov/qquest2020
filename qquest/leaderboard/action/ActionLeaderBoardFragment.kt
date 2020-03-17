package com.htccs.android.neneyolka.main.leaderboard.action

import android.view.View
import androidx.core.os.bundleOf
import com.htccs.android.neneyolka.R
import com.htccs.android.neneyolka.data.model.answer.DialogAnswer
import com.htccs.android.neneyolka.data.model.user.inventory.InventoryItem
import com.htccs.android.neneyolka.data.model.user.inventory.InventoryItemType
import com.htccs.android.neneyolka.dialogs.DialogCaller
import com.htccs.android.neneyolka.main.leaderboard.LeaderBoardFragment
import com.htccs.android.neneyolka.main.leaderboard.user.LeaderBoardUser
import com.htccs.android.neneyolka.notification.NotificationCaller
import kotlinx.android.synthetic.main.leader_board_action_fragment.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class ActionLeaderBoardFragment : LeaderBoardFragment() {

    companion object {
        val TAG: String = ActionLeaderBoardFragment::class.java.simpleName
        const val ITEM_KEY = "ITEM_KEY"

        fun newInstance(inventoryItem: InventoryItem): ActionLeaderBoardFragment {
            val actionLeaderBoardFragment = ActionLeaderBoardFragment()
            actionLeaderBoardFragment.arguments = bundleOf(ITEM_KEY to inventoryItem)
            return actionLeaderBoardFragment
        }
    }

    override val layoutId = R.layout.leader_board_action_fragment
    override val leaderAdapter = ActionLeaderAdapter(::itemClick)
    private val actionLeaderBoardViewModel: ActionLeaderBoardViewModel by viewModel()
    private val notificationCaller: NotificationCaller by inject()
    private val dialogCaller: DialogCaller by inject()

    override fun initView(view: View) {
        super.initView(view)
        actionLeaderBoardViewModel.setSentItem(arguments)
        setupLoadingObserver()
    }

    private fun setupLoadingObserver() {
        actionLeaderBoardViewModel.stateLiveData.observeState(
            { actionData ->
                if (actionData.type != null) {
                    actionData.targetLogin
                        ?.let { showSuccess(actionData.type, actionData.targetLogin) }
                        ?: setHeaderText(actionData.type)
                }
            },
            { errorStringId ->
                context?.let { context ->
                    notificationCaller.showMessage(context, getString(errorStringId))
                }
            }
        )
    }

    private fun showSuccess(type: InventoryItemType, targetLogin: String) {
        fragmentManager?.let { manager ->
            dialogCaller.showDialog(
                DialogAnswer.ResultToss(
                    type,
                    targetLogin
                ),
                manager
            )
        }
        activity?.onBackPressed()
    }

    private fun setHeaderText(type: InventoryItemType) {
        header.text = getString(
            R.string.leader_board_action_header_text,
            when (type) {
                InventoryItemType.PIG -> getString(R.string.action_pig)
                InventoryItemType.GIFT -> getString(R.string.acton_gift)
            }
        )
    }

    private fun itemClick(targetUser: LeaderBoardUser) {
        context?.let {
            targetUser.takeIf { !targetUser.isCurrent }?.let {user ->
                actionLeaderBoardViewModel.itemClick(user)
            } ?: notificationCaller.showShortMessage(it, R.string.toast_self_sending_message)
        }
    }
}