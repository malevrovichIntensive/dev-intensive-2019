package ru.skillbranch.devintensive.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.single_chat_item.view.iv_icon_avatar
import kotlinx.android.synthetic.main.single_chat_item.view.online_indicator
import kotlinx.android.synthetic.main.single_chat_item.view.tv_chat_name
import kotlinx.android.synthetic.main.user_item.view.*
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.models.data.UserItem
import ru.skillbranch.devintensive.utils.BaseViewHolder
import ru.skillbranch.devintensive.utils.IBaseItem
import java.lang.Exception

class UserAdapter(val listener: (UserItem) -> Unit) : RecyclerView.Adapter<BaseViewHolder>() {

    var items: List<IBaseItem> = listOf()

    override fun getItemViewType(position: Int): Int {
        return items[position].getLayoutId()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(viewType, parent, false)
        return when (viewType) {
            R.layout.user_item -> UserViewHolder(view, listener)
            else -> throw Exception("Invalid argument at ChatAdapter.onCreateViewHolder")
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun update(newItems: List<UserItem>) {
        val diffCallback = object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return (items[oldItemPosition] as UserItem).id == newItems[newItemPosition].id
            }

            override fun getOldListSize() = items.size

            override fun getNewListSize() = newItems.size

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                Log.d("areContentsTheSame:", "old ${(items[oldItemPosition] as UserItem)}\nnew ${newItems[newItemPosition]}")
                return (items[oldItemPosition] as UserItem).hashCode() == newItems[newItemPosition].hashCode()
            }
        }

        val diffResult = DiffUtil.calculateDiff(diffCallback)

        items = newItems
        diffResult.dispatchUpdatesTo(this)
    }

    class UserViewHolder(itemView: View, val listener: (UserItem) -> Unit) :
        BaseViewHolder(itemView) {
        override fun bind(item: IBaseItem) {
            item as UserItem
            with(itemView) {
                tv_chat_name.text = item.fullName
                tv_last_activity.text = item.lastActivity
                iv_icon_avatar.initals = item.initials ?: "??"
                online_indicator.visibility = if (item.isOnline) View.VISIBLE else View.GONE
                iv_selected.visibility = if (item.isSelected) View.VISIBLE else View.GONE
            }

            itemView.setOnClickListener {
                listener.invoke(item)
            }
        }
    }
}