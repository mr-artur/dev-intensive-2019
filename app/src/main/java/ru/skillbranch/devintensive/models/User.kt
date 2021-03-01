package ru.skillbranch.devintensive.models

import ru.skillbranch.devintensive.utils.Utils.parseFullName
import java.util.Date

data class User(
        val id: String,
        var firstName: String?,
        var lastName: String?,
        var avatar: String?,
        var rating: Int = 0,
        var respect: Int = 0,
        var lastVisit: Date? = Date(),
        var isOnline: Boolean = false
) {
    constructor(id: String, firstName: String?, lastName: String?) : this(id, firstName, lastName, null)

    companion object Factory {
        private var currentId = -1

        fun makeUser(fullName: String?): User {
            currentId++
            val (firstName, lastName) = parseFullName(fullName)

            return User(id = "$currentId", firstName = firstName, lastName = lastName)
        }
    }

    data class Builder(var id: String,
                       var firstName: String?,
                       var lastName: String?,
                       var avatar: String?,
                       var rating: Int,
                       var respect: Int,
                       var lastVisit: Date?,
                       var isOnline: Boolean) {

        fun id(id: String) = apply { this.id = id }
        fun firstName(firstName: String?) = apply { this.firstName = firstName }
        fun lastName(lastName: String?) = apply { this.lastName = lastName }
        fun avatar(avatar: String?) = apply { this.avatar = avatar }
        fun rating(rating: Int) = apply { this.rating = rating }
        fun respect(respect: Int) = apply { this.respect = respect }
        fun lastVisit(lastVisit: Date?) = apply { this.lastVisit = lastVisit }
        fun isOnline(isOnline: Boolean) = apply { this.isOnline = isOnline }
        fun build(): User = User(id, firstName, lastName, avatar, rating, respect, lastVisit, isOnline)
    }
}