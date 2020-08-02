package ru.skillbranch.devintensive.models.data

import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.ui.adapters.ChatAdapter
import ru.skillbranch.devintensive.ui.adapters.IBaseItem
import java.lang.Exception


data class ChatItem(
    val id: String,
    val avatar: String?,
    val initials: String,
    val title: String,
    val shortDescription: String?,
    val messageCount: Int = 0,
    val lastMessageDate: String?,
    val isOnline: Boolean = false,
    var chatType : ChatType = ChatType.SINGLE,
    var author :String? = null
): IBaseItem {

    override fun getLayoutId(): Int = when(chatType){
        ChatType.SINGLE -> R.layout.single_chat_item
        ChatType.GROUP -> R.layout.group_chat_item
        ChatType.ARCHIVE -> throw Exception("Not yet implemented")
    }
}