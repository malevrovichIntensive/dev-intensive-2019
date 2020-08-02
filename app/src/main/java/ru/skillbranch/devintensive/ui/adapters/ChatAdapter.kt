package ru.skillbranch.devintensive.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.group_chat_item.view.*
import kotlinx.android.synthetic.main.single_chat_item.view.*
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.models.data.ChatItem
import java.lang.Exception

class ChatAdapter : RecyclerView.Adapter<BaseViewHolder>() {

    var items: List<IBaseItem> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(viewType, parent, false)
        return when (viewType) {
            R.layout.single_chat_item -> SingleViewHolder(view)
            R.layout.group_chat_item -> GroupViewHolder(view)
            else -> throw Exception("Invalid argument at ChatAdapter.onCreateViewHolder")
        }
    }

    override fun getItemViewType(position: Int) = items[position].getLayoutId()

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun updateData(newItems: List<ChatItem>) {

        val diffCallback = object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return (items[oldItemPosition] as ChatItem).id == newItems[newItemPosition].id
            }

            override fun getOldListSize() = items.size

            override fun getNewListSize() = newItems.size

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return (items[oldItemPosition] as ChatItem).hashCode() == newItems[newItemPosition].hashCode()
            }
        }

        val diffResult = DiffUtil.calculateDiff(diffCallback)

        items = newItems
        diffResult.dispatchUpdatesTo(this)
    }

    class SingleViewHolder(itemView: View) : BaseViewHolder(itemView) {

        override fun bind(item: IBaseItem) {
            with(itemView) {
                item as ChatItem
                tv_chat_name.text = item.title
                tv_last_msg_date.text = item.lastMessageDate
                tv_message_single.text = item.shortDescription
                tv_counter.text = item.messageCount.toString()
                iv_icon_avatar.initals = item.initials
                online_indicator.visibility = if (item.isOnline) View.VISIBLE else View.GONE
            }
        }

    }

    class GroupViewHolder(itemView: View) : BaseViewHolder(itemView) {
        override fun bind(item: IBaseItem) {
            with(itemView) {
                item as ChatItem
                tv_group_name.text = item.title
                tv_last_msg_date_group.text = item.lastMessageDate
                tv_message_group.text = item.shortDescription
                tv_group_counter.text = item.messageCount.toString()
                iv_group_icon_avatar.initals = item.initials
            }
        }
    }
}