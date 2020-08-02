package ru.skillbranch.devintensive.ui.adapters

import android.graphics.*
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.utils.IBaseItem
import kotlin.math.abs

class ChatItemTouchHelperCallback(
    val adapter: ChatAdapter,
    val swipeStartListener: (IBaseItem) -> Unit,
    val swipeEndListener: (IBaseItem) -> Unit
) : ItemTouchHelper.Callback() {

    val bgRect = RectF()
    val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    val iconBound = Rect()

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        return if (viewHolder is ItemTouchHelperHolder) {
            makeFlag(
                ItemTouchHelper.ACTION_STATE_SWIPE,
                ItemTouchHelper.START or ItemTouchHelper.END
            )
        } else {
            makeFlag(ItemTouchHelper.ACTION_STATE_IDLE, ItemTouchHelper.START)
        }
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean = false

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        when (direction) {
            ItemTouchHelper.START -> swipeStartListener.invoke(adapter.items[viewHolder.adapterPosition])
            else -> swipeEndListener.invoke(adapter.items[viewHolder.adapterPosition])
        }
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if (viewHolder is ItemTouchHelperHolder) {
            viewHolder.onItemSelected()
        }
        super.onSelectedChanged(viewHolder, actionState)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        if (viewHolder is ItemTouchHelperHolder) {
            viewHolder.onItemCleared()
        }
        super.clearView(recyclerView, viewHolder)
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            drawBackground(c, dX, viewHolder.itemView)
            if (dX < 0) { // LEFT
                drawArchiveIcon(c, dX, viewHolder.itemView)
            }
            if (dX > 0){
                drawRemoveIcon(c, dX, viewHolder.itemView)
            }
        }
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    private fun drawRemoveIcon(c: Canvas, dX: Float, itemView: View) {
        val icon = itemView.resources.getDrawable(
            R.drawable.ic_baseline_delete_forever_24,
            itemView.context.theme
        )
        val iconSize = itemView.resources.getDimensionPixelSize(R.dimen.swipe_icon_size)
        val space = itemView.resources.getDimensionPixelSize(R.dimen.spacing_normal_16)

        val margin = (itemView.bottom - itemView.top - iconSize) / 2
        with(iconBound) {
            left = (itemView.left + dX - iconSize - space).toInt()
            top = itemView.top + margin
            bottom = itemView.bottom - margin
            right = (itemView.left + dX - space).toInt()
        }

        icon.bounds = iconBound
        icon.draw(c)
    }

    private fun drawArchiveIcon(c: Canvas, dX: Float, itemView: View) {
        val icon = itemView.resources.getDrawable(
            R.drawable.ic_baseline_archive_24,
            itemView.context.theme
        )
        val iconSize = itemView.resources.getDimensionPixelSize(R.dimen.swipe_icon_size)
        val space = itemView.resources.getDimensionPixelSize(R.dimen.spacing_normal_16)

        val margin = (itemView.bottom - itemView.top - iconSize) / 2
        with(iconBound) {
            left = (itemView.right + dX + space).toInt()
            top = itemView.top + margin
            bottom = itemView.bottom - margin
            right = (itemView.right + dX + iconSize + space).toInt()
        }

        icon.bounds = iconBound
        icon.draw(c)
    }

    private fun drawBackground(
        c: Canvas,
        dX: Float,
        itemHolder: View
    ) {
        if (dX < 0) { // LEFT
            with(bgRect) {
                left = itemHolder.left + dX
                right = itemHolder.right.toFloat()
                bottom = itemHolder.bottom.toFloat()
                top = itemHolder.top.toFloat()
            }
            bgPaint.color = Color.parseColor("#FCCF03")
        }
        if (dX > 0) {
            with(bgRect) {
                left = itemHolder.left.toFloat()
                right = itemHolder.right + dX
                bottom = itemHolder.bottom.toFloat()
                top = itemHolder.top.toFloat()
            }
            bgPaint.color = Color.RED
        }
        bgPaint.alpha = ((abs(dX) / (itemHolder.right - itemHolder.left)) * 255).toInt()
        c.drawRect(bgRect, bgPaint)
    }
}

interface ItemTouchHelperHolder {
    fun onItemSelected()
    fun onItemCleared()
}