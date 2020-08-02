package ru.skillbranch.devintensive.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import ru.skillbranch.devintensive.extensions.mutableLiveData
import ru.skillbranch.devintensive.models.data.UserItem
import ru.skillbranch.devintensive.repositories.GroupRepository

class GroupViewModel : ViewModel() {

    private val query = mutableLiveData("")
    private val userItems = mutableLiveData(loadData())
    private val selectedUsers =
        Transformations.map(userItems) { users -> users.filter { it.isSelected } }

    private fun loadData() = GroupRepository.loadUsers().map { it.toUserItem() }

    fun changeUserSelect(userId: String) {
        val ind = userItems.value!!.indexOfFirst { it.id == userId }
        if (ind == -1) return
        val copy = userItems.value!!.toMutableList()
        copy[ind] = copy[ind].copy(isSelected = !copy[ind].isSelected)
        userItems.value = copy
    }

    fun deselectUser(userId: String) {
        val ind = userItems.value!!.indexOfFirst { it.id == userId }
        if (ind == -1) return
        val copy = userItems.value!!.toMutableList()
        copy[ind] = copy[ind].copy(isSelected = false)
        userItems.value = copy
    }

    fun addGroup() {
        GroupRepository.createChat(selectedUsers.value!!)
    }

    fun getUserData(): LiveData<List<UserItem>> {
        val result = MediatorLiveData<List<UserItem>>()

        val filter = {
            val queryText = query.value!!
            val users = userItems.value!!

            result.value = if (queryText.isEmpty()) users
            else users.filter { it.fullName.contains(queryText, true) }
        }

        result.addSource(userItems){ filter.invoke()}
        result.addSource(query) {filter.invoke()}

        return result
    }


    fun getSelectedUsers() = selectedUsers

    fun handleSearchQuery(queryText: String?) {
        query.value = queryText
    }
}