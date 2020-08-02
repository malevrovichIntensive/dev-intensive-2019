package ru.skillbranch.devintensive.utils

import android.graphics.Color
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ru.skillbranch.devintensive.ui.adapters.ItemTouchHelperHolder

abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
    ItemTouchHelperHolder {
    abstract fun bind(item: IBaseItem)

    override fun onItemCleared() {
        itemView.setBackgroundColor(Color.WHITE)
    }

    override fun onItemSelected() {
        itemView.setBackgroundColor(Color.LTGRAY)
    }
}