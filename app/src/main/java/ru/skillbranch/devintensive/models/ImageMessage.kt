package ru.skillbranch.devintensive.models

import java.util.*

class ImageMessage(
        id: String,
        from: User?,
        chat: Chat,
        isIncoming: Boolean,
        date: Date,
        val imageUrl: String
) : BaseMessage(id, from, chat, isIncoming, date) {

    override fun formatMessage(): String = """
        Пользователь ${from?.firstName ?: "(неизвестен)"} ${if (isIncoming) "получил" else "отправил"} изображение с id=$id!
    """.trimIndent()
}