package ru.skillbranch.devintensive.models


enum class Status(val color: Triple<Int, Int, Int>) {
    NORMAL(Triple(255, 255, 255)) ,
    WARNING(Triple(255, 120, 0)),
    DANGER(Triple(255, 60, 60)),
    CRITICAL(Triple(255, 0, 0)) ;

    fun nextStatus(): Status {
        return if(this.ordinal + 1 >= Status.values().size){
            Status.values()[0]
        } else{
            Status.values()[this.ordinal + 1]
        }
    }
}

enum class Question(val question: String, val answers: List<String>) {
    NAME("Как меня зовут?", listOf("Бендер", "bender")),
    PROFESSION("Назови мою профессию?", listOf("сгибальщик", "bender")),
    MATERIAL("Из чего я сделан?", listOf("металл", "дерево", "metal", "iron", "wood")),
    BDAY("Когда меня создали?", listOf("2993")),
    SERIAL("Мой серийный номер?", listOf("2716057")),
    IDLE("На этом все, вопросов больше нет", listOf());

    fun nextQuestion(): Question {
        return if(this.ordinal + 1 >= values().size){
            values()[0]
        } else{
            values()[this.ordinal + 1]
        }
    }
}


class Bender {
    var question = Question.NAME
    var status = Status.NORMAL

    fun listenAnswer(answer: String): Pair<String, Triple<Int, Int, Int>> {
        if(answer in question.answers){
            question = question.nextQuestion()
            return "Отлично - ты справился\n${question.question}" to status.color
        } else{
            if(status == Status.CRITICAL){
                status = Status.NORMAL
                question = Question.NAME
                return "Это неправильный ответ. Давай все по новой\n${question.question}" to status.color
            }
            status = status.nextStatus()
            return "Это неправильный ответ\n${question.question}" to status.color
        }
    }
}