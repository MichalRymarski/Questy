package questy

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
    fromApplication<QuestyApplication>().with(TestcontainersConfiguration::class).run(*args)
}
