package questy

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import questy.security.JwtConfigProperties
import java.util.*

@SpringBootApplication
@EnableConfigurationProperties(value = [JwtConfigProperties::class])
class QuestyApplication

fun main(args: Array<String>) {
    runApplication<QuestyApplication>(*args)
}


inline fun <reified T> T.logger() = KotlinLogging.logger { T::class.java }

inline fun <reified T> Optional<T>.toKotlinNullable() : T? {
    return if (this.isPresent) this.get() else null
}