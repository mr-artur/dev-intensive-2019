package ru.skillbranch.devintensive.models

import java.util.*

abstract class BaseMessage(
        val id: String,
        val from: User?,
        val chat: Chat,
        val isIncoming: Boolean = false,
        val date: Date = Date()
) {
    abstract fun formatMessage(): String

    companion object AbstractFactory {
        var currentId = -1

        fun makeMessage(from: User?, chat: Chat, date: Date, type: String, payload: Any?, isIncoming: Boolean = false): BaseMessage {
            currentId++

            return when (type) {
                "text" -> TextMessage("$currentId", from, chat, isIncoming, date, text = payload as String)
                else -> ImageMessage("$currentId", from, chat, isIncoming, date, imageUrl = payload as String)
            }
        }
    }
}