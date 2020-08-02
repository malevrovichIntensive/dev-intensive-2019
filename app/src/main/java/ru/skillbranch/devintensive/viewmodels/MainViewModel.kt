package ru.skillbranch.devintensive.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ru.skillbranch.devintensive.models.data.Chat
import ru.skillbranch.devintensive.models.data.ChatItem
import ru.skillbranch.devintensive.repositories.ChatRepository

class MainViewModel : ViewModel() {

    var lastDeleted: Pair<Int, Chat>? = null

    private val chatRepository = ChatRepository
    private val chats = Transformations.map(chatRepository.loadChats()) { chats ->
        return@map chats.filter { !it.isArchived }
            .map { it.toChatItem() }
    }

    fun getChatData(): LiveData<List<ChatItem>> {
        return chats
    }

    fun addToArchive(chatId: String){
        val chat = chatRepository.find(chatId) ?: return
        chatRepository.update(chat.copy(isArchived = true))
    }

    fun restoreFromArchive(chatId: String){
        val chat = chatRepository.find(chatId) ?: return
        chatRepository.update(chat.copy(isArchived = false))
    }

    fun remove(id: String): Int {
        val value = chatRepository.find(id) ?: return -1
        val ind =  chatRepository.remove(id)
        lastDeleted = ind to value
        return ind
    }

    fun restoreLastDeleted(){
        lastDeleted ?: return
        chatRepository.insert(lastDeleted!!.first, lastDeleted!!.second)
    }
}