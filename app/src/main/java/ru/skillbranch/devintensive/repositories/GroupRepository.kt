package ru.skillbranch.devintensive.repositories

import ru.skillbranch.devintensive.data.managers.CacheManager
import ru.skillbranch.devintensive.models.data.Chat
import ru.skillbranch.devintensive.models.data.User
import ru.skillbranch.devintensive.models.data.UserItem
import ru.skillbranch.devintensive.utils.DataGenerator
import ru.skillbranch.devintensive.utils.Utils

object GroupRepository {

    val users = DataGenerator.stabUsers

    fun loadUsers(): List<User> = users

    fun createChat(userItems: List<UserItem>){
        val ids = userItems.map{it.id}
        val users = CacheManager.findUsersByIds(ids)
        val chat = Chat(CacheManager.nextChatId(), Utils.getTitleFromMembers(users), users)
        CacheManager.addChat(chat)
    }
}