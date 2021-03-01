package ru.skillbranch.devintensive.models

import java.util.*

class TextMessage(
        id: String,
        from: User?,
        chat: Chat,
        isIncoming: Boolean,
        date: Date,
        val text: String
) : BaseMessage(id, from, chat, isIncoming, date) {

    override fun formatMessage(): String = """
        Пользователь ${from?.firstName ?: "(неизвестен)"} ${if (isIncoming) "получил" else "отправил"} сообщение с id=$id!
    """.trimIndent()
}