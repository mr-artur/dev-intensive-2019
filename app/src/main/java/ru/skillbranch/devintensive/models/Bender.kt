package ru.skillbranch.devintensive.models

import androidx.core.text.isDigitsOnly

class Bender(var status: Status = Status.NORMAL, var question: Question = Question.NAME) {

    fun askQuestion(): String = question.question

    fun listenAnswer(answer: String): Pair<String, Triple<Int, Int, Int>> {

        val (isAnswerValid, validnessErrorMessage) = question.checkIfAnswerValid(answer)

        return if (!isAnswerValid) {
            "$validnessErrorMessage\n${question.question}" to status.color

        } else if (question.answers.contains(answer.toLowerCase())) {
            question = question.nextQuestion()
            "Отлично - ты справился\n${question.question}" to status.color

        } else if (question == Question.IDLE) {
            question.question to status.color

        } else {
            status = status.nextStatus()
            if (status == Status.NORMAL) {
                question = Question.NAME
                "Это неправильный ответ. Давай все по новой\n${question.question}" to status.color
            } else {
                "Это неправильный ответ\n${question.question}" to status.color
            }
        }
    }

    enum class Status(val color: Triple<Int, Int, Int>) {
        NORMAL(Triple(255, 255, 255)),
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 0, 0));

        fun nextStatus(): Status = if (ordinal < values().lastIndex) {
            values()[ordinal + 1]
        } else {
            values()[0]
        }
    }

    enum class Question(val question: String, val answers: List<String>) {
        NAME("Как меня зовут?", listOf("бендер", "bender")) {

            override fun checkIfAnswerValid(answer: String): Pair<Boolean, String> {
                return (answer.firstOrNull()?.isUpperCase() ?: false) to "Имя должно начинаться с заглавной буквы"
            }

            override fun nextQuestion(): Question = PROFESSION
        },
        PROFESSION("Назови мою профессию?", listOf("сгибальщик", "bender")) {

            override fun checkIfAnswerValid(answer: String): Pair<Boolean, String> {
                return (answer.firstOrNull()?.isLowerCase() ?: false) to "Профессия должна начинаться со строчной буквы"
            }

            override fun nextQuestion(): Question = MATERIAL
        },
        MATERIAL("Из чего я сделан?", listOf("металл", "дерево", "metal", "iron", "wood")) {

            override fun checkIfAnswerValid(answer: String): Pair<Boolean, String> {
                var isValid = true
                for (symbol in answer) {
                    if (symbol.isDigit()) {
                        isValid = false
                        break
                    }
                }

                return isValid to "Материал не должен содержать цифр"
            }

            override fun nextQuestion(): Question = BDAY
        },
        BDAY("Когда меня создали?", listOf("2993")) {

            override fun checkIfAnswerValid(answer: String): Pair<Boolean, String> {
                return (answer.isDigitsOnly()) to "Год моего рождения должен содержать только цифры"
            }

            override fun nextQuestion(): Question = SERIAL
        },
        SERIAL("Мой серийный номер?", listOf("2716057")) {

            override fun checkIfAnswerValid(answer: String): Pair<Boolean, String> {
                return (answer.isDigitsOnly() && answer.length == 7) to "Серийный номер содержит только цифры, и их 7"
            }

            override fun nextQuestion(): Question = IDLE
        },
        IDLE("На этом все, вопросов больше нет", listOf()) {

            override fun checkIfAnswerValid(answer: String): Pair<Boolean, String> = true to ""

            override fun nextQuestion(): Question = IDLE
        };

        abstract fun nextQuestion(): Question
        abstract fun checkIfAnswerValid(answer: String): Pair<Boolean, String>
    }
}