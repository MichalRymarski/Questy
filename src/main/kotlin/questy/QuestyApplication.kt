package questy

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.util.Optional

@SpringBootApplication
class QuestyApplication

fun main(args: Array<String>) {
    runApplication<QuestyApplication>(*args)
}


inline fun <reified T> T.logger() = KotlinLogging.logger { T::class.java }

inline fun <reified T> Optional<T>.toKotlinNullable() : T? {
    return if (this.isPresent) this.get() else null
}