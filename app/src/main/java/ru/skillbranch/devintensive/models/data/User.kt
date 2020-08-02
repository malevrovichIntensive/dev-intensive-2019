package ru.skillbranch.devintensive.models.data

import ru.skillbranch.devintensive.extensions.humanizeDiff
import ru.skillbranch.devintensive.utils.Utils
import java.util.*

data class User(
    var id: String,
    var firstName: String?,
    var lastName: String?,
    var avatar: String?,
    var rating: Int = 0,
    var respect: Int = 0,
    var lastVisit: Date? = Date(),
    var isOnline: Boolean = false
){
    fun toUserItem(): UserItem {
        return UserItem(
            id = id,
            fullName = "$firstName $lastName",
            initials = Utils.toInitials(firstName, lastName) ?: "??",
            avatar = avatar,
            lastActivity = lastVisit?.humanizeDiff(Date()) ?: "online",
            isSelected = false,
            isOnline = isOnline
        )
    }

    constructor(id: Int, firstName: String?, lastName: String?): this(id.toString(), firstName, lastName, null){}

    companion object Factory{
        var idCounter = 0

        fun makeUser(fullname: String? = null): User {
            val tmp = Utils.parseFullName(fullname)
            return User(
                idCounter++,
                tmp.first,
                tmp.second
            )
        }
    }
}