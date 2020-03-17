package com.htccs.android.neneyolka.main.leaderboard

import android.view.View
import androidx.annotation.ColorRes
import com.htccs.android.neneyolka.R
import com.htccs.android.neneyolka.base.BaseAdapterWithDiff
import com.htccs.android.neneyolka.main.leaderboard.user.LeaderBoardUser
import com.htccs.android.neneyolka.uiextensions.getColorCompat
import kotlinx.android.synthetic.main.leader_board_list_item.view.*

open class LeaderAdapter(private val listener: (LeaderBoardUser) -> Unit = {}) :
    BaseAdapterWithDiff<LeaderBoardUser>() {

    companion object {
        // количество используемых иконок (used in level-list)
        private const val USER_IMAGE_COUNTS = 6
    }

    override val offsetHeader = 1

    override fun compareSameItems(oldItem: LeaderBoardUser, newItem: LeaderBoardUser) =
        oldItem.email == newItem.email

    override fun compareSameContents(oldItem: LeaderBoardUser, newItem: LeaderBoardUser) =
        oldItem == newItem

    override fun getLayoutResourceByType(viewType: Int) =
        if (viewType == TYPE_HEADER)
            R.layout.leader_board_list_header
        else
            R.layout.leader_board_list_item

    override fun getViewHolderByType(viewType: Int, view: View) =
        if (viewType == TYPE_HEADER)
            ViewHolderHeader(view)
        else
            ViewHolderNormal(view)

    private fun LeaderBoardUser.generateImgLevel(): Int {
        return ((email?.count() ?: 0) + (login?.count() ?: 0)) % USER_IMAGE_COUNTS
    }

    inner class ViewHolderNormal(itemView: View) : BaseItemViewHolder(itemView) {

        private val defaultValue
            get() = itemView.context.getString(R.string.default_value_dash)

        init {
            itemView.setOnClickListener {
                listener(getItemWithOffset(adapterPosition))
            }
        }

        override fun bind(item: LeaderBoardUser) {
            with(itemView) {
                name.text = item.login
                score.text = item.score?.toString() ?: defaultValue
                position.text = item.rating?.toString() ?: defaultValue
                position.setTextColor(context.getColorCompat(getPositionTextColor(item)))
                userAnimalImg.setImageLevel(item.generateImgLevel())
            }
        }

        @ColorRes
        private fun getPositionTextColor(user: LeaderBoardUser) =
            if (user.isCurrent) {
                R.color.secondary
            } else {
                R.color.secondary_text
            }
    }

    inner class ViewHolderHeader(itemView: View) : BaseViewHolder(itemView)
}