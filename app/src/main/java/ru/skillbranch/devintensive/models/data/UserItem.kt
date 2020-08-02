package ru.skillbranch.devintensive.models.data

import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.utils.IBaseItem


data class UserItem (
    val id: String,
    val fullName: String,
    val initials : String?,
    val avatar: String?,
    var lastActivity:String,
    var isSelected : Boolean = false,
    var isOnline: Boolean = false
): IBaseItem {
    override fun getLayoutId(): Int {
        return R.layout.user_item
    }
}