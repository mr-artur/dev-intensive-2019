package ru.skillbranch.devintensive.utils

object Utils {

    fun parseFullName(fullName: String?): Pair<String?, String?> {
        if (fullName.isNullOrBlank()) {
            return null to null
        }
        val parts = fullName.split(" ")
        val firstName = parts.getOrNull(0)
        val secondName = parts.getOrNull(1)

        return firstName to secondName
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        var firstNameInitial = ""
        var lastNameInitial = ""

        if (firstName.isNullOrBlank() && lastName.isNullOrBlank()) {
            return null
        }
        if (!firstName.isNullOrBlank()) {
            firstNameInitial = firstNameInitial.plus(firstName[0].toUpperCase())
        }
        if (!lastName.isNullOrBlank()) {
            lastNameInitial = lastNameInitial.plus(lastName[0].toUpperCase())
        }

        return firstNameInitial.plus(lastNameInitial)
    }

    fun transliteration(name: String, divider: String = " "): String {
        val parts = name.split(" ")
        val resultList = arrayListOf<String>()

        for (word in parts) {
            val result = StringBuilder()
            for (symbol in word) {
                result.append(toEng(symbol.toLowerCase()))
            }
            resultList.add(result.toString().capitalize())
        }
        return resultList.joinToString(separator = divider)
    }

    private fun toEng(symbol: Char): String = when (symbol) {
        'а' -> "a"
        'б' -> "b"
        'в' -> "v"
        'г' -> "g"
        'д' -> "d"
        'е' -> "e"
        'ж' -> "zh"
        'з' -> "z"
        'ё' -> "e"
        'и' -> "i"
        'й' -> "i"
        'к' -> "k"
        'л' -> "l"
        'м' -> "m"
        'н' -> "n"
        'о' -> "o"
        'п' -> "p"
        'р' -> "r"
        'с' -> "s"
        'т' -> "t"
        'у' -> "u"
        'ф' -> "f"
        'х' -> "h"
        'ц' -> "c"
        'ч' -> "ch"
        'ш' -> "sh"
        'щ' -> "sh'"
        'ъ' -> ""
        'ы' -> "i"
        'ь' -> ""
        'э' -> "e"
        'ю' -> "yu"
        'я' -> "ya"
        else -> symbol.toString()
    }
}