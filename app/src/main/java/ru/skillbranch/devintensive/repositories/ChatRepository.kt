package ru.skillbranch.devintensive.repositories

import androidx.lifecycle.MutableLiveData
import ru.skillbranch.devintensive.data.managers.CacheManager
import ru.skillbranch.devintensive.models.data.Chat

object ChatRepository{

    val chats = CacheManager.loadData()

    fun loadChats(): MutableLiveData<List<Chat>> {
        return chats
    }

    fun update(chat: Chat){
        val copy = chats.value!!.toMutableList()
        val ind = chats.value!!.indexOfFirst { it.id == chat.id }
        if(ind == -1) return
        copy[ind] = chat
        chats.value = copy
    }

    fun find(chatId: String): Chat?{
        val ind = chats.value!!.indexOfFirst { it.id == chatId }
        return chats.value!!.getOrNull(ind)
    }

    fun remove(chatId: String): Int{
        val copy = chats.value!!.toMutableList()
        val ind = chats.value!!.indexOfFirst { it.id == chatId }
        if(ind == -1) return -1
        copy.removeAt(ind)
        chats.value = copy
        return ind
    }

    fun insert(ind: Int, value: Chat) {
        val copy = chats.value!!.toMutableList()
        copy.add(ind, value)
        chats.value = copy
    }
}