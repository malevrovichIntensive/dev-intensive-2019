package ru.skillbranch.myapplication.models

import java.util.*

class Chat

abstract class BaseMessage(
    val id: String,
    val from: User?,
    val chat: Chat,
    val isIncoming: Boolean = false,
    val date: Date = Date()
) {
    abstract fun formatMessage(): String

    companion object AbstractFactory {
        var idCounter = 0

        fun makeMessage(
            from: User,
            chat: Chat,
            date: Date,
            type: String,
            payload: String,
            isIncoming: Boolean = false
        ): BaseMessage = when(type){
            "image" -> ImageMessage(idCounter++, from, chat, payload, isIncoming, date)
            else -> TextMessage(idCounter++, from, chat, payload, isIncoming, date)
        }
    }
}

