package ru.skillbranch.myapplication.models

import ru.skillbranch.myapplication.utils.Utils
import java.util.*

data class User (
    var id : String,
    var firstName : String?,
    var lastName : String?,
    var avatar : String?,
    var rating : Int = 0,
    var respect : Int = 0,
    var lastVisit : Date? = Date(),
    var isOnline : Boolean = false
){
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