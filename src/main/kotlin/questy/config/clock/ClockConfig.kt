package questy.config.clock

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Clock
import java.time.ZoneId


@Configuration
/**
 * Apparently its necessary so JWT tokens have proper issue/use time
 */
class ClockConfig {
    @Bean
    fun clock(): Clock {
        return Clock.system(ZoneId.of("UTC"))
    }
}