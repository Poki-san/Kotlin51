import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

val channel = Channel<String>()

suspend fun main():Unit = coroutineScope {
    val storage = Storage()
    launch {
        getList(storage.text)
    }
    var stringResult = ""
    val time = measureTimeMillis {
        launch {
            storage.text.split("\n").forEach { _ ->
                 stringResult += "\n${channel.receive()}"
            }
        }.join()
    }
    println(stringResult)
    println("\nЗатраченное время на получение - $time")

}

class Storage {
    val text = """
        Мартышка к старости слаба глазами стала.
        А у людей она слыхала,
        Что это зло еще не так большой руки.
        Лишь стоит завести Очки.
        Очков с полдюжины себе она достала.
        Вертит Очками так и сяк.
        То к темю их прижмет, то их на хвост нанижет,
        То их понюхает, то их полижет.
        Очки не действуют никак.
        Тьфу пропасть! - говорит она,- и тот дурак,
        Кто слушает людских всех врак.
        Всё про Очки лишь мне налгали.
        А проку на-волос нет в них.
        Мартышка тут с досады и с печали
        О камень так хватила их,
        Что только брызги засверкали.
        К несчастью, то ж бывает у людей.
        Как ни полезна вещь,- цены не зная ей,
        Невежда про нее свой толк все к худу клонит.
        А ежели невежда познатней,
        Так он ее еще и гонит. 
        """.trimIndent()
}

suspend fun getList(text: String) {
    text.split("\n").forEach {
        delay(10L)
        channel.send(it)
    }
}