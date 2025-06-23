package questy

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class QuestyApplication

fun main(args: Array<String>) {
    runApplication<QuestyApplication>(*args)
}
