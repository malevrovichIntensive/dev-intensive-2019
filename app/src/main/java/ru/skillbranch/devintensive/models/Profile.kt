package ru.skillbranch.devintensive.models

import android.widget.TextView

data class Profile(
    var nickName: String = "John Doe",
    var rank: String = "Junior Android Developer",
    var rating: String = "0",
    var respect: String = "0",
    var firstName: String = "",
    var lastName: String = "",
    var about: String = "",
    var repository: String = ""
){
    fun toMap() = mapOf(
        "nickName" to nickName,
        "rank" to rank,
        "rating" to rating,
        "respect" to respect,
        "firstName" to firstName,
        "lastName" to lastName,
        "about" to about,
        "repository" to repository
    )
}