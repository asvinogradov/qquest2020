package com.htccs.android.neneyolka.main.levelsFragment

import android.view.View
import com.htccs.android.neneyolka.R
import com.htccs.android.neneyolka.base.BaseAdapterWithDiff
import com.htccs.android.neneyolka.data.model.level.Level
import com.htccs.android.neneyolka.uiextensions.getColorCompat
import com.htccs.android.neneyolka.uiextensions.loadImage
import com.htccs.android.neneyolka.uiextensions.remove
import com.htccs.android.neneyolka.uiextensions.show
import kotlinx.android.synthetic.main.levels_list_item.view.*

class LevelsAdapter(private val listener: (Level) -> Unit = {}) :
    BaseAdapterWithDiff<LevelsItem>() {

    override val offsetHeader = 1

    override fun compareSameItems(oldItem: LevelsItem, newItem: LevelsItem) =
        oldItem.content.id == newItem.content.id

    override fun compareSameContents(oldItem: LevelsItem, newItem: LevelsItem) =
        oldItem == newItem

    override fun getLayoutResourceByType(viewType: Int) =
        if (viewType == TYPE_HEADER)
            R.layout.levels_list_header
        else
            R.layout.levels_list_item

    override fun getViewHolderByType(viewType: Int, view: View) =
        if (viewType == TYPE_HEADER)
            ViewHolderHeader(view)
        else
            ViewHolderNormal(view)

    inner class ViewHolderNormal(itemView: View) : BaseItemViewHolder(itemView) {

        override fun bind(item: LevelsItem) {
            with(itemView) {
                levelImage.loadImage(item.content.imageReference)
                levelName.text = item.content.name
                chooseItemViewAppearance(item)
            }
        }

        private fun chooseItemViewAppearance(level: LevelsItem) {
            with(itemView) {
                if (level.foundItemCount != null) {
                    lock.remove()
                    setOnClickListener { listener(getItemWithOffset(adapterPosition).content) }
                    levelStatus.setTextColor(context.getColorCompat(R.color.text_spec_gray_1))
                    setStatusText(
                        level.foundItemCount,
                        level.content.itemsCount,
                        level.isSequenceComplete
                    )
                } else {
                    lock.show()
                    setOnClickListener(null)
                    levelStatus.setTextColor(context.getColorCompat(R.color.text_spec_gray_1))
                    levelStatus.setText(R.string.level_closed)
                }
            }
        }

        private fun setStatusText(
            foundItemCount: Int,
            onLevelItemCount: Int?,
            isSequenceComplete: Boolean
        ) {
            with(itemView.context) {
                itemView.levelStatus.text = onLevelItemCount
                    ?.let { count ->
                        count.takeIf { foundItemCount < it || !isSequenceComplete }
                            ?.let { getString(R.string.level_status, foundItemCount, it) }
                            ?: getString(R.string.level_complete)
                    }
                    ?: getString(R.string.levels_load_error)
            }
        }
    }

    inner class ViewHolderHeader(itemView: View) : BaseViewHolder(itemView)
}