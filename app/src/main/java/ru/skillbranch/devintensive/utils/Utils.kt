package ru.skillbranch.myapplication.utils

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {
        val tmp = fullName?.split(" ")
        var firstName = tmp?.getOrNull(0)
        var lastName = tmp?.getOrNull(1)
        if (firstName?.isBlank() == true) {
            firstName = null
        }
        if (lastName?.isBlank() == true) {
            lastName = null
        }
        return Pair(firstName, lastName)
    }

    fun toInitials(firstName: String?, lastName: String?): String? =
        if ((firstName == null || firstName.isBlank()) && (lastName == null || lastName.isBlank())) {
            null
        } else if ((firstName == null || firstName.isBlank())) {
            lastName!![0].toUpperCase().toString()
        } else if ((lastName == null || lastName.isBlank())) {
            firstName[0].toUpperCase().toString()
        } else {
            firstName[0].toUpperCase().toString() + lastName[0].toUpperCase().toString()
        }

    fun transliteration(payload: String, divider: String = " "): String {
        var tmp = payload
        val dict = mutableMapOf(
            "а" to "a",
            "б" to "b",
            "в" to "v",
            "г" to "g",
            "д" to "d",
            "е" to "e",
            "ё" to "e",
            "ж" to "zh",
            "з" to "z",
            "и" to "i",
            "й" to "i",
            "к" to "k",
            "л" to "l",
            "м" to "m",
            "н" to "n",
            "о" to "o",
            "п" to "p",
            "р" to "r",
            "с" to "s",
            "т" to "t",
            "у" to "u",
            "ф" to "f",
            "х" to "h",
            "ц" to "c",
            "ч" to "ch",
            "ш" to "sh",
            "щ" to "sh'",
            "ъ" to "",
            "ы" to "i",
            "ь" to "",
            "э" to "e",
            "ю" to "yu",
            "я" to "ya")
        dict[" "] = divider
        for((k, v) in dict){
            tmp = tmp.replace(k, v)
            tmp = tmp.replace(k.toUpperCase(), v.capitalize())
        }
        return tmp
    }

}