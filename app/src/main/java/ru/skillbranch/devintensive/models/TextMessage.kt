package ru.skillbranch.myapplication.models

import java.util.*

class TextMessage(
    id: String,
    from: User?,
    chat: Chat,
    val text: String,
    isIncoming: Boolean = false,
    date: Date = Date()
    ) : BaseMessage(id, from, chat, isIncoming, date) {

        constructor(id: Int, from: User?, chat: Chat, image: String, isIncoming: Boolean, date: Date):
        this(id.toString(), from, chat, image, isIncoming, date)

        override fun formatMessage(): String = if(isIncoming){
            "id:$id ${from?.firstName} отправил изображение \"${text}\""
        } else {
            "id:$id ${from?.firstName} получил изображение \"${text}\""
        }
    }
