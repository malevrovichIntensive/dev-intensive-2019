package ru.skillbranch.devintensive.managers

import androidx.lifecycle.MutableLiveData
import ru.skillbranch.devintensive.extensions.mutableLiveData
import ru.skillbranch.devintensive.models.data.Chat
import ru.skillbranch.devintensive.models.data.User
import ru.skillbranch.devintensive.models.data.UserItem
import ru.skillbranch.devintensive.utils.DataGenerator

object CacheManager {
    var chats = mutableLiveData(DataGenerator.stabChats)
    var users = mutableLiveData(DataGenerator.stabUsers)

    fun loadData(): MutableLiveData<List<Chat>>{
        return chats
    }

    fun findUsersByIds(ids: List<String>): List<User> {
        return users.value!!.filter { ids.contains(it.id) }
    }

    fun nextChatId() = chats.value!!.size.toString()

    fun addChat(chat: Chat){
        val copy = chats.value!!.toMutableList()
        copy.add(chat)
        chats.value = copy
    }
}