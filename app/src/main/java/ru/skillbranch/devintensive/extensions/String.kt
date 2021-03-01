package ru.skillbranch.devintensive.extensions

import java.lang.StringBuilder

fun String.truncate(limit: Int = 16): String {
    var truncated = substring(0, if (limit <= length) limit else length)
    while (truncated.last().isWhitespace()) {
        truncated = truncated.dropLast(1)
    }
    val meaningfulEnd = substring(truncated.length, length).isNotBlank()
    return if (meaningfulEnd) truncated.plus("...") else truncated
}

fun String.stripHtml(): String {
    val result = trim().replace("</.*>".toRegex(), "").replace("<.*>".toRegex(), "")
    return result.replace("\\s+".toRegex(), " ")
}